/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import event.ColorEvent;
import event.ColorListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.SpringLayout;
import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;
import obj.Gesture;
import obj.Joint;
import obj.Skeleton;
import org.bytedeco.javacpp.opencv_core;
import static org.bytedeco.javacpp.opencv_core.CV_PI;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.VideoInputFrameGrabber;

/**
 * The GUI of this program.
 * @author jgbabao
 */
public class MainFrame extends JFrame
{
    private final JButton BUTTON_BROWSE_1;
    private final JButton BUTTON_BROWSE_2;
    private final JButton BUTTON_CLEAR;
    private final JButton BUTTON_COMPARE;
    private final JButton BUTTON_NEXT_1;
    private final JButton BUTTON_NEXT_2;
    private final JToggleButton BUTTON_PLAY_1;
    private final JToggleButton BUTTON_PLAY_2;
    private final JButton BUTTON_PREVIOUS_1;
    private final JButton BUTTON_PREVIOUS_2;
    private final ColorDetector COLOR_DETECTOR_BLUE;
    private final ColorDetector COLOR_DETECTOR_GREEN;
    private final ColorDetector COLOR_DETECTOR_ORANGE;
    private final ColorDetector COLOR_DETECTOR_PURPLE;
    private final ColorDetector COLOR_DETECTOR_RED;
    private final ColorDetector COLOR_DETECTOR_VIOLET;
    private final ColorDetector COLOR_DETECTOR_YELLOW;
    private final ActionHandler EVENT_HANDLER;
    private final JLabel LABEL_PROCESS;
    private final JLabel LABEL_OUTPUT;
    private final SpringLayout LAYOUT_CONTENT;
    private final JPanel PANEL_CONTENT;
    private final JPanel PANEL_OUTPUT_BUTTONS;
    private final JPanel PANEL_SLIDER_BUTTONS_1;
    private final JPanel PANEL_SLIDER_BUTTONS_2;
    private final List<Joint> SCANNED_JOINTS;
    private final JScrollPane SCROLL_CONTENT;
    private final JScrollPane SCROLL_PROCESS;
    private final JScrollPane SCROLL_OUTPUT;
    private final ImageBox SLIDER_1;
    private final ImageBox SLIDER_2;
    private final JTextArea TEXTAREA_OUTPUT;
    private final JTextArea TEXTAREA_PROCESS;
    private FrameGrabber frameGrabber; 
    private OpenCVFrameConverter.ToIplImage frameConverter;
    private boolean runVideo;
    private Thread thread_videoCapture;
    
    /**
     * The constructor for this class. Initializes all components and other variables contained in this class.
     */
    private MainFrame()
    {
        BUTTON_BROWSE_1 = new JButton("BROWSE");
        BUTTON_BROWSE_2 = new JButton("BROWSE");
        BUTTON_CLEAR = new JButton("CLEAR");
        BUTTON_COMPARE = new JButton("COMPARE");
        BUTTON_NEXT_1 = new JButton(">>");
        BUTTON_NEXT_2 = new JButton(">>");
        BUTTON_PLAY_1 = new JToggleButton("PLAY");
        BUTTON_PLAY_2 = new JToggleButton("PLAY");
        BUTTON_PREVIOUS_1 = new JButton("<<");
        BUTTON_PREVIOUS_2 = new JButton("<<");
        COLOR_DETECTOR_BLUE = new ColorDetector(new Color(0, 0, 255), 30, 30, 30, 255);
        COLOR_DETECTOR_GREEN = new ColorDetector(new Color(0, 128, 0), 30, 30, 30, 255);
        COLOR_DETECTOR_ORANGE = new ColorDetector(new Color(255, 165, 0), 30, 30, 30, 255);
        COLOR_DETECTOR_PURPLE = new ColorDetector(new Color(138, 43, 226), 30, 30, 30, 255);
        COLOR_DETECTOR_RED = new ColorDetector(new Color(255, 0, 0), 30, 30, 30, 255);
        COLOR_DETECTOR_VIOLET = new ColorDetector(new Color(128, 0, 128), 30, 30, 30, 255);
        COLOR_DETECTOR_YELLOW = new ColorDetector(new Color(255, 255, 0), 30, 30, 30, 255);
        EVENT_HANDLER = new ActionHandler();
        LABEL_PROCESS = new JLabel("Process:");
        LABEL_OUTPUT = new JLabel("Output:");
        LAYOUT_CONTENT = new SpringLayout();
        PANEL_CONTENT = new JPanel();
        PANEL_OUTPUT_BUTTONS = new JPanel();
        PANEL_SLIDER_BUTTONS_1 = new JPanel();
        PANEL_SLIDER_BUTTONS_2 = new JPanel();
        SCANNED_JOINTS = new ArrayList();
        SCROLL_CONTENT = new JScrollPane();
        SCROLL_PROCESS = new JScrollPane();
        SCROLL_OUTPUT = new JScrollPane();
        SLIDER_1 = new ImageBox();
        SLIDER_2 = new ImageBox();
        TEXTAREA_OUTPUT = new JTextArea();
        TEXTAREA_PROCESS = new JTextArea();
        frameGrabber = new VideoInputFrameGrabber(0);
        frameConverter = new OpenCVFrameConverter.ToIplImage();
        runVideo = true;
        
        thread_videoCapture = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    frameGrabber.start();
                    frameGrabber.setAspectRatio(CV_PI);

                    while(runVideo)
                    {
                        opencv_core.IplImage frame = frameConverter.convert(frameGrabber.grab());

                        if(frame == null)
                        {
                            continue;
                        }

                        SLIDER_1.setImage(new MarvinImage(IplImageToBufferedImage(frame)));
                        SLIDER_2.setImage(new MarvinImage(IplImageToBufferedImage(frame)));
                        SLIDER_1.scanPanel();
                        SLIDER_1.viewSelectedAreas(true);
                    }

                    frameGrabber.stop();
                }
                
                catch(Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }
        });
        
        setupComponents();
    }
    
    /**
     * Converts an IplImage image to type BufferedImage.
     * @param img the IplImage image to be converted.
     * @return The converted BufferedImage.
     */
    private BufferedImage IplImageToBufferedImage(opencv_core.IplImage img)
    {
        OpenCVFrameConverter.ToIplImage grabberConverter = new OpenCVFrameConverter.ToIplImage();
        Java2DFrameConverter paintConverter = new Java2DFrameConverter();
        Frame frame = grabberConverter.convert(img);
        
        return(paintConverter.getBufferedImage(frame,1));
    }
    
    /**
     * Set ups the GUI components.
     */
    private void setupComponents()
    {
        final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
        final Dimension MIN_SIZE = new Dimension(10, 10);
        final String EAST = SpringLayout.EAST;
        final String H_CENTER = SpringLayout.HORIZONTAL_CENTER;
        final String NORTH = SpringLayout.NORTH;
        final String SOUTH = SpringLayout.SOUTH;
        final String V_CENTER = SpringLayout.VERTICAL_CENTER;
        final String WEST = SpringLayout.WEST;
        
        COLOR_DETECTOR_BLUE.minSize = MIN_SIZE;
        COLOR_DETECTOR_GREEN.minSize = MIN_SIZE;
        COLOR_DETECTOR_ORANGE.minSize = MIN_SIZE;
        COLOR_DETECTOR_PURPLE.minSize = MIN_SIZE;
        COLOR_DETECTOR_RED.minSize = MIN_SIZE;
        COLOR_DETECTOR_VIOLET.minSize = MIN_SIZE;
        COLOR_DETECTOR_YELLOW.minSize = MIN_SIZE;
        
        SLIDER_1.addColorListener(COLOR_DETECTOR_BLUE); // Blue
        SLIDER_1.addColorListener(COLOR_DETECTOR_GREEN); // Green
        SLIDER_1.addColorListener(COLOR_DETECTOR_ORANGE); // Orange
        SLIDER_1.addColorListener(COLOR_DETECTOR_PURPLE); // Purple
        SLIDER_1.addColorListener(COLOR_DETECTOR_RED); // Red
        SLIDER_1.addColorListener(COLOR_DETECTOR_VIOLET); // Violet
        SLIDER_1.addColorListener(COLOR_DETECTOR_YELLOW); // Yellow
        
        SLIDER_2.addColorListener(COLOR_DETECTOR_BLUE); // Blue
        SLIDER_2.addColorListener(COLOR_DETECTOR_GREEN); // Green
        SLIDER_2.addColorListener(COLOR_DETECTOR_ORANGE); // Orange
        SLIDER_2.addColorListener(COLOR_DETECTOR_PURPLE); // Purple
        SLIDER_2.addColorListener(COLOR_DETECTOR_RED); // Red
        SLIDER_2.addColorListener(COLOR_DETECTOR_VIOLET); // Violet
        SLIDER_2.addColorListener(COLOR_DETECTOR_YELLOW); // Yellow
        
        /*SLIDER_1.addImage(MarvinImageIO.loadImage("./img/edited_1.png"));
        SLIDER_1.addImage(MarvinImageIO.loadImage("./img/edited_2.png"));
        SLIDER_1.addImage(MarvinImageIO.loadImage("./img/edited_3.png"));
        SLIDER_1.addImage(MarvinImageIO.loadImage("./img/edited_1.png"));
        SLIDER_2.addImage(MarvinImageIO.loadImage("./img/edited_1.png"));
        SLIDER_2.addImage(MarvinImageIO.loadImage("./img/edited_2.png"));
        SLIDER_2.addImage(MarvinImageIO.loadImage("./img/edited_3.png"));*/
        
        BUTTON_PREVIOUS_1.addActionListener(EVENT_HANDLER);
        BUTTON_PLAY_1.addActionListener(EVENT_HANDLER);
        BUTTON_BROWSE_1.addActionListener(EVENT_HANDLER);
        BUTTON_NEXT_1.addActionListener(EVENT_HANDLER);
        BUTTON_PREVIOUS_2.addActionListener(EVENT_HANDLER);
        BUTTON_PLAY_2.addActionListener(EVENT_HANDLER);
        BUTTON_BROWSE_2.addActionListener(EVENT_HANDLER);
        BUTTON_NEXT_2.addActionListener(EVENT_HANDLER);
        BUTTON_COMPARE.addActionListener(EVENT_HANDLER);
        BUTTON_CLEAR.addActionListener(EVENT_HANDLER);
        
        PANEL_SLIDER_BUTTONS_1.setLayout(new GridLayout(1, 4, 10, 5));
        PANEL_SLIDER_BUTTONS_1.add(BUTTON_PREVIOUS_1);
        PANEL_SLIDER_BUTTONS_1.add(BUTTON_PLAY_1);
        PANEL_SLIDER_BUTTONS_1.add(BUTTON_BROWSE_1);
        PANEL_SLIDER_BUTTONS_1.add(BUTTON_NEXT_1);
        
        PANEL_SLIDER_BUTTONS_2.setLayout(new GridLayout(1, 4, 10, 5));
        PANEL_SLIDER_BUTTONS_2.add(BUTTON_PREVIOUS_2);
        PANEL_SLIDER_BUTTONS_2.add(BUTTON_PLAY_2);
        PANEL_SLIDER_BUTTONS_2.add(BUTTON_BROWSE_2);
        PANEL_SLIDER_BUTTONS_2.add(BUTTON_NEXT_2);
        
        PANEL_OUTPUT_BUTTONS.setLayout(new GridLayout(1, 2, 10, 5));
        PANEL_OUTPUT_BUTTONS.add(BUTTON_COMPARE);
        PANEL_OUTPUT_BUTTONS.add(BUTTON_CLEAR);
        
        TEXTAREA_PROCESS.setEditable(false);
        TEXTAREA_OUTPUT.setEditable(false);
        
        SCREEN_SIZE.setSize(SCREEN_SIZE.width - 20, SCREEN_SIZE.height - 70);
        PANEL_CONTENT.setLayout(LAYOUT_CONTENT);
        PANEL_CONTENT.setPreferredSize(SCREEN_SIZE);
        PANEL_CONTENT.add(SLIDER_1);
        PANEL_CONTENT.add(SLIDER_2);
        PANEL_CONTENT.add(PANEL_SLIDER_BUTTONS_1);
        PANEL_CONTENT.add(PANEL_SLIDER_BUTTONS_2);
        PANEL_CONTENT.add(LABEL_PROCESS);
        PANEL_CONTENT.add(LABEL_OUTPUT);
        PANEL_CONTENT.add(SCROLL_PROCESS);
        PANEL_CONTENT.add(SCROLL_OUTPUT);
        PANEL_CONTENT.add(PANEL_OUTPUT_BUTTONS);
        
        LAYOUT_CONTENT.putConstraint(NORTH, SLIDER_1, 20, NORTH, PANEL_CONTENT);
        LAYOUT_CONTENT.putConstraint(EAST, SLIDER_1, -20, H_CENTER, PANEL_CONTENT);
        LAYOUT_CONTENT.putConstraint(SOUTH, SLIDER_1, 20, V_CENTER, PANEL_CONTENT);
        LAYOUT_CONTENT.putConstraint(WEST, SLIDER_1, 20, WEST, PANEL_CONTENT);
        LAYOUT_CONTENT.putConstraint(NORTH, SLIDER_2, 20, NORTH, PANEL_CONTENT);
        LAYOUT_CONTENT.putConstraint(EAST, SLIDER_2, -20, EAST, PANEL_CONTENT);
        LAYOUT_CONTENT.putConstraint(SOUTH, SLIDER_2, 20, V_CENTER, PANEL_CONTENT);
        LAYOUT_CONTENT.putConstraint(WEST, SLIDER_2, 20, H_CENTER, PANEL_CONTENT);
        LAYOUT_CONTENT.putConstraint(NORTH, PANEL_SLIDER_BUTTONS_1, 5, SOUTH, SLIDER_1);
        LAYOUT_CONTENT.putConstraint(H_CENTER, PANEL_SLIDER_BUTTONS_1, 0, H_CENTER, SLIDER_1);
        LAYOUT_CONTENT.putConstraint(NORTH, PANEL_SLIDER_BUTTONS_2, 5, SOUTH, SLIDER_2);
        LAYOUT_CONTENT.putConstraint(H_CENTER, PANEL_SLIDER_BUTTONS_2, 0, H_CENTER, SLIDER_2);
        LAYOUT_CONTENT.putConstraint(NORTH, LABEL_PROCESS, 80, V_CENTER, PANEL_CONTENT);
        LAYOUT_CONTENT.putConstraint(WEST, LABEL_PROCESS, 20, WEST, PANEL_CONTENT);
        LAYOUT_CONTENT.putConstraint(NORTH, LABEL_OUTPUT, 80, V_CENTER, PANEL_CONTENT);
        LAYOUT_CONTENT.putConstraint(WEST, LABEL_OUTPUT, 20, H_CENTER, PANEL_CONTENT);
        LAYOUT_CONTENT.putConstraint(NORTH, SCROLL_PROCESS, 5, SOUTH, LABEL_PROCESS);
        LAYOUT_CONTENT.putConstraint(EAST, SCROLL_PROCESS, -20, H_CENTER, PANEL_CONTENT);
        LAYOUT_CONTENT.putConstraint(SOUTH, SCROLL_PROCESS, -20, NORTH, PANEL_OUTPUT_BUTTONS);
        LAYOUT_CONTENT.putConstraint(WEST, SCROLL_PROCESS, 20, WEST, PANEL_CONTENT);
        LAYOUT_CONTENT.putConstraint(NORTH, SCROLL_OUTPUT, 5, SOUTH, LABEL_OUTPUT);
        LAYOUT_CONTENT.putConstraint(EAST, SCROLL_OUTPUT, -20, EAST, PANEL_CONTENT);
        LAYOUT_CONTENT.putConstraint(SOUTH, SCROLL_OUTPUT, -20, NORTH, PANEL_OUTPUT_BUTTONS);
        LAYOUT_CONTENT.putConstraint(WEST, SCROLL_OUTPUT, 20, H_CENTER, PANEL_CONTENT);
        LAYOUT_CONTENT.putConstraint(SOUTH, PANEL_OUTPUT_BUTTONS, -20, SOUTH, PANEL_CONTENT);
        LAYOUT_CONTENT.putConstraint(H_CENTER, PANEL_OUTPUT_BUTTONS, 0, H_CENTER, PANEL_CONTENT);
        
        SCROLL_PROCESS.setViewportView(TEXTAREA_PROCESS);
        SCROLL_OUTPUT.setViewportView(TEXTAREA_OUTPUT);
        SCROLL_CONTENT.setViewportView(PANEL_CONTENT);
        
        add(SCROLL_CONTENT);
    }
    
    /**
     * Loads the GUI.
     */
    public static void loadGUI()
    {
        MainFrame mainFrame = new MainFrame();
        
        mainFrame.setTitle("DOTHS-LMS Translator Prototype 5.1");
        mainFrame.setIconImage(new ImageIcon("./img/v2.1.png").getImage());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(new Dimension(1000, 600));
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.thread_videoCapture.start();
        mainFrame.setVisible(true);
    }
    
    /**
     * Handles all color events.
     */
    private class ColorDetector implements ColorListener
    {
        public final Color COLOR;
        public double alphaLimit;
        public double blueLimit;
        public double greenLimit;
        public Dimension maxSize;
        public Dimension minSize;
        public double redLimit;
        
        public ColorDetector(Color color)
        {
            this(color, 0, 0, 0, 0);
        }
        
        public ColorDetector(Color color, double redLimit, double greenLimit, double blueLimit, double alphaLimit)
        {
            COLOR = color;
            this.alphaLimit = alphaLimit;
            this.blueLimit = blueLimit;
            this.greenLimit = greenLimit;
            maxSize = null;
            minSize = null;
            this.redLimit = redLimit;
        }
        
        @Override
        public void colorDetected(ColorEvent event)
        {
            Rectangle r = event.AREA;
            Point p = new Point();
            
            p.setLocation(r.getCenterX(), r.getCenterY());
            SCANNED_JOINTS.add(new Joint(getTargetColor(), p));
        }
        
        @Override
        public double getAlphaLimit()
        {
            return(alphaLimit);
        }
        
        @Override
        public double getBlueLimit()
        {
            return(blueLimit);
        }
        
        @Override
        public double getGreenLimit()
        {
            return(greenLimit);
        }
        
        @Override
        public Dimension getMaxArea()
        {
            return(maxSize);
        }
        
        @Override
        public Dimension getMinArea()
        {
            return(minSize);
        }
        
        @Override
        public double getRedLimit()
        {
            return(redLimit);
        }
        
        @Override
        public Color getTargetColor()
        {
            return(COLOR);
        }
    }
    
    /**
     * Handles all action events.
     */
    private class ActionHandler implements ActionListener
    {
        /**
         * Initializes the file explorer to search and select image(s).
         * @return The list of selected images.
         */
        private List<MarvinImage> browseImages()
        {
            final JFileChooser FILE_CHOOSER = new JFileChooser();
            final List<MarvinImage> IMAGES = new ArrayList();
            
            FILE_CHOOSER.setMultiSelectionEnabled(true);
            
            try
            {
                FILE_CHOOSER.setMultiSelectionEnabled(true);
                int option = FILE_CHOOSER.showOpenDialog(null);
                File[] fileImages = FILE_CHOOSER.getSelectedFiles();

                if(fileImages == null)
                {
                    throw new NullPointerException();
                }

                if(option == JFileChooser.APPROVE_OPTION)
                {
                    for(File fileImage : fileImages)
                    {
                        IMAGES.add(new MarvinImage(ImageIO.read(fileImage)));
                    }
                }
            }

            catch(HeadlessException | IOException | NullPointerException ex)
            {
                JOptionPane.showMessageDialog(PANEL_CONTENT, "Cannot read selected file.", "Image Error", JOptionPane.ERROR_MESSAGE);
            }
            
            return(IMAGES);
        }
        
        @Override
        public void actionPerformed(ActionEvent e)
        {
            final Object SOURCE = e.getSource();
            
            if(SOURCE == BUTTON_PREVIOUS_1)
            {
                SLIDER_1.previousImage();
            }
            
            else if(SOURCE == BUTTON_PLAY_1)
            {
                SLIDER_1.setImageMode(BUTTON_PLAY_1.isSelected() ? ImageBox.SLIDE_MODE : ImageBox.NORMAL_MODE);
                BUTTON_PLAY_1.setText(BUTTON_PLAY_1.isSelected() ? "PAUSE" : "PLAY");
            }
            
            else if(SOURCE == BUTTON_BROWSE_1)
            {
                SLIDER_1.clear();
                
                for(MarvinImage image : browseImages())
                {
                    SLIDER_1.addImage(image);
                }
            }
            
            else if(SOURCE == BUTTON_NEXT_1)
            {
                SLIDER_1.nextImage();
            }
            
            else if(SOURCE == BUTTON_PREVIOUS_2)
            {
                SLIDER_2.previousImage();
            }
            
            else if(SOURCE == BUTTON_PLAY_2)
            {
                SLIDER_2.setImageMode(BUTTON_PLAY_2.isSelected() ? ImageBox.SLIDE_MODE : ImageBox.NORMAL_MODE);
                BUTTON_PLAY_2.setText(BUTTON_PLAY_2.isSelected() ? "PAUSE" : "PLAY");
            }
            
            else if(SOURCE == BUTTON_BROWSE_2)
            {
                SLIDER_2.clear();
                
                for(MarvinImage image : browseImages())
                {
                    SLIDER_2.addImage(image);
                }
            }
            
            else if(SOURCE == BUTTON_NEXT_2)
            {
                SLIDER_2.nextImage();
            }
            
            else if(SOURCE == BUTTON_COMPARE)
            {
                Gesture g1 = new Gesture();
                Gesture g2 = new Gesture();
                g1.acceptanceRadius = 5;
                g2.acceptanceRadius = 5;
                
                for(int i = 0; i < SLIDER_1.imageCount(); i++)
                {
                    Skeleton s1 = new Skeleton();
                    s1.acceptanceRadius = 10;
                    
                    SCANNED_JOINTS.clear();
                    SLIDER_1.setImage(i);
                    SLIDER_1.scanPanel();
                    SLIDER_1.viewSelectedAreas(true);
                    
                    for(Joint j : SCANNED_JOINTS)
                    {
                        s1.addJoint(j);
                    }
                    
                    g1.addSkeleton(i, s1);
                }
                
                for(int i = 0; i < SLIDER_2.imageCount(); i++)
                {
                    Skeleton s2 = new Skeleton();
                    s2.acceptanceRadius = 10;
                    
                    SCANNED_JOINTS.clear();
                    SLIDER_2.setImage(i);
                    SLIDER_2.scanPanel();
                    SLIDER_2.viewSelectedAreas(true);

                    for(Joint j : SCANNED_JOINTS)
                    {
                        s2.addJoint(j);
                    }
                    
                    g2.addSkeleton(i, s2);
                }
                
                TEXTAREA_PROCESS.setText("Gesture Match: " + g1.percentageMatch(g2));
            }
            
            else if(SOURCE == BUTTON_CLEAR)
            {
                TEXTAREA_PROCESS.setText("");
                TEXTAREA_OUTPUT.setText("");
            }
        }
    }
}