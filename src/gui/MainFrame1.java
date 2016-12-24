/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import db.JavaDB;
import event.ColorEvent;
import event.ColorListener;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import marvin.image.MarvinImage;

/**
 * The class that builds the frame for the GUI.
 * @author Liel Tan
 */
public final class MainFrame1 extends JFrame
{
    /**
     * Connects to database.
     */
    private static final JavaDB DB = new JavaDB("jdbc:mysql://localhost:3306/doths_lms", "root", "");
    
    /**
     * The component for the capture bar.
     */
    private final CaptureBar CAPTURE_BAR;
    
    /**
     * Collection of color handlers.
     */
    private final Map<Integer, ColorHandler> COLOR_HANDLERS;
    
    /**
     * The the layout for this frame.
     */
    private final SpringLayout LAYOUT_CONTENT;
    
    /**
     * The component for the menu bar.
     */
    private final MenuBar MENU_BAR;
    
    /**
     * The component for the tool bar.
     */
    private final ToolBar TOOL_BAR;
    
    /**
     * The panel for the video panel.
     */
    private final VideoPanel VIDEO_PANEL;
    
    /**
     * The mouse location within the video panel.
     */
    private Point mouseLocation;
    private Color pixelColor;
    private Integer selectedColorHandlerID;
    
    /**
     * The class that handles the color events.
     */
    public final class ColorHandler implements ColorListener
    {
        /**
         * The minimum and maximum range for the alpha value.
         */
        private double alphaLimit;
        
        /**
         * The minimum and maximum range for the blue value.
         */
        private double blueLimit;
        
        /**
         * Specifies the part of the finger: distal, middle, proximal.
         */
        private String fingerPart;
        
        /**
         * The minimum and maximum range for the green value.
         */
        private double greenLimit;
        
        /**
         * Specifies the part of the hand: pinky, ring, middle, index, thumb, palm.
         */
        private String handPart;
        
        /**
         * Specifies the hand used: left, right.
         */
        private String handUsed;
        
        /**
         * Specifies the orientation of the hand: front, back.
         */
        private String orientation;
        
        /**
         * The max size of the area to be detected.
         */
        private Dimension maxArea;
        
        /**
         * The min size of the area to be detected.
         */
        private Dimension minArea;
        
        /**
         * The minimum and maximum range for the red value.
         */
        private double redLimit;
        
        /**
         * The color to be detected.
         */
        private Color targetColor;
        
        /**
         * The database ID for this joint.
         */
        public final int ID;
        
        /**
         * The constructor for this class.
         * @param id The joint ID that is, the identity of the joint recognized by the database.
         */
        public ColorHandler(int id)
        {
            ID = id;
            
            DB.setIsConnected(true);
            DB.setIsUpdatable(false);

            try
            {
                ResultSet rs = DB.executeQuery("SELECT * FROM Joint WHERE id = " + ID);
                rs.next();
                
                targetColor = new Color(rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));
                redLimit = rs.getInt(6);
                greenLimit = rs.getInt(7);
                blueLimit = rs.getInt(8);
                alphaLimit = rs.getInt(9);
                minArea = new Dimension(rs.getInt(10), rs.getInt(11));
                maxArea = new Dimension(rs.getInt(12), rs.getInt(13));
                handUsed = rs.getString(14);
                orientation = rs.getString(15);
                handPart = rs.getString(16);
                fingerPart = rs.getString(17);
            }

            catch(SQLException | NullPointerException ex)
            {
                System.out.println("Error getting color handler.");
            }

            finally
            {
                DB.setIsConnected(false);
            }
        }
        
        @Override
        public void colorDetected(ColorEvent event)
        {
            
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
        
        public String getFingerPart()
        {
            return(fingerPart);
        }
        
        @Override
        public double getGreenLimit()
        {
            return(greenLimit);
        }
        
        public String getHandPart()
        {
            return(handPart);
        }
        
        public String getHandUsed()
        {
            return(handUsed);
        }
        
        @Override
        public Dimension getMaxArea()
        {
            return(maxArea);
        }

        @Override
        public Dimension getMinArea()
        {
            return(minArea);
        }
        
        public Color getMaxTargetColor()
        {
            int red = targetColor.getRed() + (int)redLimit;
            int green = targetColor.getGreen() + (int)greenLimit;
            int blue = targetColor.getBlue() + (int)blueLimit;
            int alpha = targetColor.getAlpha() + (int)alphaLimit;
            
            red = red > 255 ? 255 : red;
            green = green > 255 ? 255 : green;
            blue = blue > 255 ? 255 : blue;
            alpha = alpha > 255 ? 255 : alpha;
            
            return(new Color(red, green, blue, alpha));
        }
        
        public Color getMinTargetColor()
        {
            int red = targetColor.getRed() - (int)redLimit;
            int green = targetColor.getGreen() - (int)greenLimit;
            int blue = targetColor.getBlue() - (int)blueLimit;
            int alpha = targetColor.getAlpha() - (int)alphaLimit;
            
            red = red < 0 ? 0 : red;
            green = green < 0 ? 0 : green;
            blue = blue < 0 ? 0 : blue;
            alpha = alpha < 0 ? 0 : alpha;
            
            return(new Color(red, green, blue, alpha));
        }
        
        public String getOrientation()
        {
            return(orientation);
        }
        
        @Override
        public double getRedLimit()
        {
            return(redLimit);
        }

        @Override
        public Color getTargetColor()
        {
            return(targetColor);
        }
        
        /**
         * Sets the left and right range for alpha value acceptance.
         * @param limit The left and right ranges from the alpha value.
         */
        public void setAlphaLimit(int limit)
        {
            DB.setIsConnected(true);
            DB.setIsUpdatable(true);
            
            alphaLimit = DB.executeUpdate("UPDATE Joint SET alpha_limit = " + limit + " WHERE id = " + ID)
                       ? limit : alphaLimit;
            
            DB.setIsConnected(false);
        }
        
        /**
         * Sets the left and right range for blue value acceptance.
         * @param limit The left and right ranges from blue alpha value.
         */
        public void setBlueLimit(int limit)
        {
            DB.setIsConnected(true);
            DB.setIsUpdatable(true);
            
            blueLimit = DB.executeUpdate("UPDATE Joint SET blue_limit = " + limit + " WHERE id = " + ID)
                      ? limit : blueLimit;
            
            DB.setIsConnected(false);
        }
        
        /**
         * Sets the left and right range for green value acceptance.
         * @param limit The left and right ranges from the green value.
         */
        public void setGreenLimit(int limit)
        {
            DB.setIsConnected(true);
            DB.setIsUpdatable(true);
            
            greenLimit = DB.executeUpdate("UPDATE Joint SET green_limit = " + limit + " WHERE id = " + ID)
                       ? limit : greenLimit;
            
            DB.setIsConnected(false);
        }
        
        /**
         * Sets the color to be detected.
         * @param color The desired color to be detected.
         */
        public void setTargetColor(Color color)
        {
            DB.setIsConnected(true);
            DB.setIsUpdatable(true);
            
            targetColor = DB.executeUpdate("UPDATE Joint SET"
                                         + " red_value = " + color.getRed()
                                         + ", green_value = " + color.getGreen()
                                         + ", blue_value = " + color.getBlue()
                                         + ", alpha_value = " + color.getAlpha()
                                         + " WHERE id = " + ID)
                        ? color : targetColor;
            
            DB.setIsConnected(false);
        }
        
        /**
         * Sets the left and right range for red value acceptance.
         * @param limit The left and right ranges from the red value.
         */
        public void setRedLimit(int limit)
        {
            DB.setIsConnected(true);
            DB.setIsUpdatable(true);
            
            redLimit = DB.executeUpdate("UPDATE Joint SET red_limit = " + limit + " WHERE id = " + ID)
                     ? limit : redLimit;
            
            DB.setIsConnected(false);
        }
    }
    
    /**
     * Gets the specified color handler.
     * @param handUsed can either be "left" or "right".
     * @param orientation can either be "front" or "back".
     * @param handPart can be any part of the hand: "thumb", "index", "middle", "ring", "pinky", "palm"
     * @param fingerPart can be of any division: "distal", "middle", "proximal"
     * @return The specified color handler.
     */
    private ColorHandler getColorHandler(String handUsed, String orientation, String handPart, String fingerPart)
    {
        ColorHandler colorHandler = null;
        
        for(Integer i : COLOR_HANDLERS.keySet())
        {
            ColorHandler ch = COLOR_HANDLERS.get(i);
            
            if(handPart.equals("palm") && ch.getHandUsed().equals(handUsed) && ch.getOrientation().equals(orientation) && ch.getHandPart().equals(handPart))
            {
                colorHandler = ch;
                break;
            }
            
            else if(ch.getHandUsed().equals(handUsed) && ch.getOrientation().equals(orientation) && ch.getHandPart().equals(handPart) && ch.getFingerPart().equals(fingerPart))
            {
                colorHandler = ch;
                break;
            }
        }
        
        return(colorHandler);
    }
    
    /**
     * Collects the stored color handlers from the database.
     */
    private void getColorHandlers()
    {
        DB.setIsConnected(true);
        DB.setIsUpdatable(false);
        COLOR_HANDLERS.clear();
        
        try
        {
            ResultSet rs = DB.select("joint");
            
            while(rs.next())
            {
                int id = rs.getInt(1);
                COLOR_HANDLERS.put(id, new ColorHandler(id));
            }
        }
        
        catch(SQLException | NullPointerException ex)
        {
            System.out.println("Error getting color handlers.");
        }
        
        VIDEO_PANEL.removeAllColorListeners();
        
        for(Integer i : COLOR_HANDLERS.keySet())
        {
            VIDEO_PANEL.addColorListener(COLOR_HANDLERS.get(i));
        }
        
        DB.setIsConnected(false);
    }
    
    /**
     * Configures the event listeners for each component.
     */
    private void configureComponents()
    {
        /* ActionListeners: */
        CAPTURE_BAR.BUTTON_PLAY.addActionListener((ActionEvent ae) ->
        {
            boolean isSelected = CAPTURE_BAR.BUTTON_PLAY.isSelected();

            if(isSelected)
            {
                if(VIDEO_PANEL.isRunning())
                {
                    VIDEO_PANEL.resumeVideoCapture();
                }

                else
                {
                    VIDEO_PANEL.startVideoCapture();
                }
            }

            else
            {
                VIDEO_PANEL.pauseVideoCapture();
            }

            CAPTURE_BAR.BUTTON_PLAY.setText(isSelected ? "PAUSE" : "PLAY");
        });

        CAPTURE_BAR.BUTTON_REWIND.addActionListener((ActionEvent ae) ->
        {
            VIDEO_PANEL.previousImage();
        });

        CAPTURE_BAR.BUTTON_RECORD.addActionListener((ActionEvent ae) ->
        {
            boolean isSelected = CAPTURE_BAR.BUTTON_RECORD.isSelected();

            if(isSelected)
            {
                VIDEO_PANEL.clearGesture();
            }

            else
            {
                VIDEO_PANEL.pauseVideoCapture();
                CAPTURE_BAR.BUTTON_PLAY.setSelected(false);
                CAPTURE_BAR.BUTTON_PLAY.setText("PLAY");
            }

            VIDEO_PANEL.setEnableRecordImages(isSelected);
            CAPTURE_BAR.BUTTON_RECORD.setText(isSelected ? "STOP" : "RECORD");
        });

        CAPTURE_BAR.BUTTON_FORWARD.addActionListener((ActionEvent ae) ->
        {
            VIDEO_PANEL.nextImage();
        });

        CAPTURE_BAR.BUTTON_SAVE.addActionListener((ActionEvent ae) ->
        {
            JOptionPane.showMessageDialog(null, "This feature is not yet available.");
        });

        CAPTURE_BAR.BUTTON_TRANSLATOR_MODE.addActionListener((ActionEvent ae) ->
        {
            TOOL_BAR.TABBED_PANE_CONTENT.setSelectedIndex(0);
            VIDEO_PANEL.viewSelectedAreas(true);
        });

        CAPTURE_BAR.BUTTON_CALIBRATION_MODE.addActionListener((ActionEvent ae) ->
        {
            TOOL_BAR.TABBED_PANE_CONTENT.setSelectedIndex(1);
        });

        CAPTURE_BAR.BUTTON_DICTIONARY_MODE.addActionListener((ActionEvent ae) ->
        {
            TOOL_BAR.TABBED_PANE_CONTENT.setSelectedIndex(2);
        });

        TOOL_BAR.BUTTON_SELECT_JOINT.addActionListener((ActionEvent ae) ->
        {
            VIDEO_PANEL.clearSelectedAreas();
            selectJoint();
        });

        TOOL_BAR.DROP_DOWN_HANDS.addActionListener((ActionEvent ae) ->
        {
            selectJoint();
        });

        TOOL_BAR.DROP_DOWN_HAND_ORIENTATION.addActionListener((ActionEvent ae) ->
        {
            selectJoint();
        });

        TOOL_BAR.DROP_DOWN_HAND_PARTS.addActionListener((ActionEvent ae) ->
        {
            selectJoint();
            TOOL_BAR.DROP_DOWN_FINGER_PARTS.setEnabled(!COLOR_HANDLERS.get(selectedColorHandlerID).handPart.equals("palm"));
        });

        TOOL_BAR.DROP_DOWN_FINGER_PARTS.addActionListener((ActionEvent ae) ->
        {
            selectJoint();
        });
        
        /* ChangeListeners: */
        TOOL_BAR.SLIDER_RED.addChangeListener((ChangeEvent ce) ->
        {
            ColorHandler colorHandler = COLOR_HANDLERS.get(selectedColorHandlerID);

            if(colorHandler != null)
            {
                int redValue = TOOL_BAR.SLIDER_RED.getValue();
                
                colorHandler.setRedLimit(redValue);
                TOOL_BAR.LABEL_RED.setText("Red: " + redValue);
                TOOL_BAR.BUTTON_LEFT_COLOR.setBackground(colorHandler.getMinTargetColor());
                TOOL_BAR.BUTTON_RIGHT_COLOR.setBackground(colorHandler.getMaxTargetColor());
            }
        });

        TOOL_BAR.SLIDER_GREEN.addChangeListener((ChangeEvent ce) ->
        {
            ColorHandler colorHandler = COLOR_HANDLERS.get(selectedColorHandlerID);

            if(colorHandler != null)
            {
                int greenValue = TOOL_BAR.SLIDER_GREEN.getValue();
                
                colorHandler.setGreenLimit(greenValue);
                TOOL_BAR.LABEL_GREEN.setText("Green: " + greenValue);
                TOOL_BAR.BUTTON_LEFT_COLOR.setBackground(colorHandler.getMinTargetColor());
                TOOL_BAR.BUTTON_RIGHT_COLOR.setBackground(colorHandler.getMaxTargetColor());
            }
        });

        TOOL_BAR.SLIDER_BLUE.addChangeListener((ChangeEvent ce) ->
        {
            ColorHandler colorHandler = COLOR_HANDLERS.get(selectedColorHandlerID);

            if(colorHandler != null)
            {
                int blueValue = TOOL_BAR.SLIDER_BLUE.getValue();
                
                colorHandler.setBlueLimit(blueValue);
                TOOL_BAR.LABEL_BLUE.setText("Blue: " + blueValue);
                TOOL_BAR.BUTTON_LEFT_COLOR.setBackground(colorHandler.getMinTargetColor());
                TOOL_BAR.BUTTON_RIGHT_COLOR.setBackground(colorHandler.getMaxTargetColor());
            }
        });

        TOOL_BAR.SLIDER_ALPHA.addChangeListener((ChangeEvent ce) ->
        {
            ColorHandler colorHandler = COLOR_HANDLERS.get(selectedColorHandlerID);

            if(colorHandler != null)
            {
                int alphaValue = TOOL_BAR.SLIDER_ALPHA.getValue();
                
                colorHandler.setAlphaLimit(alphaValue);
                TOOL_BAR.LABEL_ALPHA.setText("Alpha: " + alphaValue);
                TOOL_BAR.BUTTON_LEFT_COLOR.setBackground(colorHandler.getMinTargetColor());
                TOOL_BAR.BUTTON_RIGHT_COLOR.setBackground(colorHandler.getMaxTargetColor());
            }
        });
        
        /* KeyListeners: */
        final KeyAdapter KEY_HANDLER = new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent ke)
            {
                boolean redCheck = false;
                boolean greenCheck = false;
                boolean blueCheck = false;
                boolean alphaCheck = false;
                
                try
                {
                    int red = Integer.parseInt(TOOL_BAR.TEXT_FIELD_RED.getText());
                    redCheck = red >= 0 && red <= 255;
                    int green = Integer.parseInt(TOOL_BAR.TEXT_FIELD_GREEN.getText());
                    greenCheck = green >= 0 && green <= 255;
                    int blue = Integer.parseInt(TOOL_BAR.TEXT_FIELD_BLUE.getText());
                    blueCheck = blue >= 0 && blue <= 255;
                    int alpha = Integer.parseInt(TOOL_BAR.TEXT_FIELD_ALPHA.getText());
                    alphaCheck = alpha >= 0 && alpha <= 255;
                    Color targetColor = new Color(red, green, blue, alpha);
                    ColorHandler colorHandler = COLOR_HANDLERS.get(selectedColorHandlerID);
                    
                    colorHandler.setTargetColor(targetColor);
                    TOOL_BAR.BUTTON_LEFT_COLOR.setBackground(colorHandler.getMinTargetColor());
                    TOOL_BAR.BUTTON_MEDIAN_COLOR.setBackground(colorHandler.getTargetColor());
                    TOOL_BAR.BUTTON_RIGHT_COLOR.setBackground(colorHandler.getMaxTargetColor());
                }
                
                catch(IllegalArgumentException ex){}
                
                finally
                {
                    TOOL_BAR.TEXT_FIELD_RED.setForeground(redCheck ? Color.BLACK : Color.RED);
                    TOOL_BAR.TEXT_FIELD_GREEN.setForeground(greenCheck ? Color.BLACK : Color.RED);
                    TOOL_BAR.TEXT_FIELD_BLUE.setForeground(blueCheck ? Color.BLACK : Color.RED);
                    TOOL_BAR.TEXT_FIELD_ALPHA.setForeground(alphaCheck ? Color.BLACK : Color.RED);
                }
            }
        };
        
        TOOL_BAR.TEXT_FIELD_RED.addKeyListener(KEY_HANDLER);
        TOOL_BAR.TEXT_FIELD_GREEN.addKeyListener(KEY_HANDLER);
        TOOL_BAR.TEXT_FIELD_BLUE.addKeyListener(KEY_HANDLER);
        TOOL_BAR.TEXT_FIELD_ALPHA.addKeyListener(KEY_HANDLER);
        
        /* MouseListeners: */
        final MouseAdapter MOUSE_HANDLER = new MouseAdapter()
        {
            @Override
            public void mouseDragged(MouseEvent me)
            {
                selectPixel(me.getPoint());
            }
            
            @Override
            public void mousePressed(MouseEvent me)
            {
                selectPixel(me.getPoint());
            }
            
            @Override
            public void mouseReleased(MouseEvent me)
            {
                selectPixel(me.getPoint());
            }
        };
        
        VIDEO_PANEL.addMouseListener(MOUSE_HANDLER);
        VIDEO_PANEL.addMouseMotionListener(MOUSE_HANDLER);
        
        /* Input Map: */
    }
    
    /**
     * Selects a joint from the database.
     */
    private void selectJoint()
    {
        boolean isSelected = TOOL_BAR.BUTTON_SELECT_JOINT.isSelected();
        ColorHandler colorHandler = getColorHandler((String)TOOL_BAR.DROP_DOWN_HANDS.getSelectedItem(),
                                                    (String)TOOL_BAR.DROP_DOWN_HAND_ORIENTATION.getSelectedItem(),
                                                    (String)TOOL_BAR.DROP_DOWN_HAND_PARTS.getSelectedItem(),
                                                    (String)TOOL_BAR.DROP_DOWN_FINGER_PARTS.getSelectedItem());

        boolean isFound = colorHandler != null;
        boolean isEditable = TOOL_BAR.BUTTON_SELECT_JOINT.isSelected();

        TOOL_BAR.setEditableColorPanel(isEditable);
        TOOL_BAR.setEnabledColorPanel(isFound);
        TOOL_BAR.setEnabledColorRangePanel(isFound & isSelected);
        TOOL_BAR.setEnabledJointPanel(isSelected);
        TOOL_BAR.BUTTON_MEDIAN_COLOR.setEnabled(false);

        if(isFound)
        {
            Color targetColor = colorHandler.getTargetColor();
            selectedColorHandlerID = colorHandler.ID;
            
            TOOL_BAR.BUTTON_LEFT_COLOR.setBackground(colorHandler.getMinTargetColor());
            TOOL_BAR.BUTTON_MEDIAN_COLOR.setBackground(targetColor);
            TOOL_BAR.BUTTON_RIGHT_COLOR.setBackground(colorHandler.getMaxTargetColor());
            
            TOOL_BAR.TEXT_FIELD_RED.setText(targetColor.getRed() + "");
            TOOL_BAR.TEXT_FIELD_GREEN.setText(targetColor.getGreen() + "");
            TOOL_BAR.TEXT_FIELD_BLUE.setText(targetColor.getBlue() + "");
            TOOL_BAR.TEXT_FIELD_ALPHA.setText(targetColor.getAlpha() + "");
            
            TOOL_BAR.LABEL_RED.setText("Red: " + (int)colorHandler.getRedLimit());
            TOOL_BAR.LABEL_GREEN.setText("Green: " + (int)colorHandler.getGreenLimit());
            TOOL_BAR.LABEL_BLUE.setText("Blue: " + (int)colorHandler.getBlueLimit());
            TOOL_BAR.LABEL_ALPHA.setText("Alpha: " + (int)colorHandler.getAlphaLimit());
            
            TOOL_BAR.SLIDER_RED.setValue((int)colorHandler.getRedLimit());
            TOOL_BAR.SLIDER_GREEN.setValue((int)colorHandler.getGreenLimit());
            TOOL_BAR.SLIDER_BLUE.setValue((int)colorHandler.getBlueLimit());
            TOOL_BAR.SLIDER_ALPHA.setValue((int)colorHandler.getAlphaLimit());
        }

        else
        {
            selectedColorHandlerID = null;
        }
    }
    
    /**
     * Selects a pixel from the video pane.
     * @param p The location of the pixel.
     */
    private void selectPixel(Point p)
    {
        mouseLocation = p;
        Rectangle region = new Rectangle(mouseLocation.x - 5, mouseLocation.y - 5, 9, 9);
        MarvinImage image = VIDEO_PANEL.getImage();
        pixelColor = new Color(image.getIntColor(mouseLocation.x, mouseLocation.y), true);

        if(!TOOL_BAR.BUTTON_SELECT_JOINT.isSelected() && image != null)
        {
            VIDEO_PANEL.pauseVideoCapture();
            VIDEO_PANEL.clearSelectedAreas();
            VIDEO_PANEL.selectRegion(region);
            
            CAPTURE_BAR.BUTTON_PLAY.setSelected(false);
            CAPTURE_BAR.BUTTON_PLAY.setText("PLAY");
            
            TOOL_BAR.TEXT_FIELD_X.setText(mouseLocation.x + "");
            TOOL_BAR.TEXT_FIELD_Y.setText(mouseLocation.y + "");
            TOOL_BAR.TEXT_FIELD_RED.setText(pixelColor.getRed() + "");
            TOOL_BAR.TEXT_FIELD_GREEN.setText(pixelColor.getGreen() + "");
            TOOL_BAR.TEXT_FIELD_BLUE.setText(pixelColor.getBlue() + "");
            TOOL_BAR.TEXT_FIELD_ALPHA.setText(pixelColor.getAlpha() + "");
        }
    }
    
    /**
     * Setup the components for the frame.
     */
    private void setupComponents()
    {
        final Container CONTENT_PANE = getContentPane();
        final String EAST = SpringLayout.EAST;
        final String H_CENTER = SpringLayout.HORIZONTAL_CENTER;
        final String NORTH = SpringLayout.NORTH;
        final String SOUTH = SpringLayout.SOUTH;
        final String V_CENTER = SpringLayout.VERTICAL_CENTER;
        final String WEST = SpringLayout.WEST;
        
        getColorHandlers();
        
        LAYOUT_CONTENT.putConstraint(NORTH, MENU_BAR, 1, NORTH, CONTENT_PANE);
        LAYOUT_CONTENT.putConstraint(EAST, MENU_BAR, 1, EAST, CONTENT_PANE);
        LAYOUT_CONTENT.putConstraint(WEST, MENU_BAR, 1, WEST, CONTENT_PANE);
        
        LAYOUT_CONTENT.putConstraint(NORTH, VIDEO_PANEL, 0, SOUTH, MENU_BAR);
        LAYOUT_CONTENT.putConstraint(EAST, VIDEO_PANEL, 0, WEST, TOOL_BAR);
        LAYOUT_CONTENT.putConstraint(WEST, VIDEO_PANEL, 0, WEST, CONTENT_PANE);
        LAYOUT_CONTENT.putConstraint(SOUTH, VIDEO_PANEL, 0, NORTH, CAPTURE_BAR);
        
        LAYOUT_CONTENT.putConstraint(NORTH, TOOL_BAR, 0, SOUTH, MENU_BAR);
        LAYOUT_CONTENT.putConstraint(EAST, TOOL_BAR, 0, EAST, CONTENT_PANE);
        LAYOUT_CONTENT.putConstraint(WEST, TOOL_BAR, -300, EAST, TOOL_BAR);
        LAYOUT_CONTENT.putConstraint(SOUTH, TOOL_BAR, 0, NORTH, CAPTURE_BAR);
        
        LAYOUT_CONTENT.putConstraint(NORTH, CAPTURE_BAR, -70, SOUTH, CAPTURE_BAR);
        LAYOUT_CONTENT.putConstraint(EAST, CAPTURE_BAR, 0, EAST, CONTENT_PANE);
        LAYOUT_CONTENT.putConstraint(SOUTH, CAPTURE_BAR, 0, SOUTH, CONTENT_PANE);
        LAYOUT_CONTENT.putConstraint(WEST, CAPTURE_BAR, 0, WEST, CONTENT_PANE);
        
        setLayout(LAYOUT_CONTENT);
        add(MENU_BAR);
        add(VIDEO_PANEL);
        add(TOOL_BAR);
        add(CAPTURE_BAR);
    }
    
    /**
     * The constructor for this class.
     */
    public MainFrame1()
    {
        CAPTURE_BAR = new CaptureBar();
        COLOR_HANDLERS = new HashMap();
        LAYOUT_CONTENT = new SpringLayout();
        MENU_BAR = new MenuBar();
        TOOL_BAR = new ToolBar();
        VIDEO_PANEL = new VideoPanel();
        
        setupComponents();
        configureComponents();
    }
    
    /**
     * Loads the graphical user interface.
     */
    public void loadGUI()
    {
        setTitle("DOTHS Sign Language Translator");
        setIconImage(new ImageIcon("./img/v2.1.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1000, 610));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }
}