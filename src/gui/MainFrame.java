/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import sys.JavaDB;
import event.ColorEvent;
import event.ColorListener;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import marvin.image.MarvinImage;
import obj.Gesture;
import obj.Joint;
import obj.SignLanguage;
import obj.Skeleton;

/**
 * The class that builds the frame for the GUI.
 * @author Liel Tan
 */
public final class MainFrame extends JFrame
{
    private static final JavaDB DB = new JavaDB("jdbc:mysql://localhost:3306/doths_lms", "root", "");
    
    private final MenuBar MENU_BAR;
    private final MediaPanel MEDIA_PANEL;
    private final ToolBar TOOL_BAR;
    private final CaptureBar CAPTURE_BAR;
    
    private final Map<Integer, ColorHandler> MAP_COLOR_HANDLER;
    private Integer selectedColorHandlerID;
    
    private final List<SignLanguage> LIST_SIGN_LANGUAGE;
    
    private Point point_mouse;
    private Color color_pixel;
    
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
            /*System.out.println("Color Detected: r=" + targetColor.getRed()
                                            + ",g=" + targetColor.getGreen()
                                            + ",b=" + targetColor.getBlue());*/
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
            
            red = red > 255 ? 255 : red;
            green = green > 255 ? 255 : green;
            blue = blue > 255 ? 255 : blue;
            
            return(new Color(red, green, blue));
        }
        
        public Color getMinTargetColor()
        {
            int red = targetColor.getRed() - (int)redLimit;
            int green = targetColor.getGreen() - (int)greenLimit;
            int blue = targetColor.getBlue() - (int)blueLimit;
            
            red = red < 0 ? 0 : red;
            green = green < 0 ? 0 : green;
            blue = blue < 0 ? 0 : blue;
            
            return(new Color(red, green, blue));
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
    private ColorHandler getColorHandler(String handUsed, String orientation, String handPart)
    {
        ColorHandler colorHandler = null;
        
        for(Integer i : MAP_COLOR_HANDLER.keySet())
        {
            ColorHandler ch = MAP_COLOR_HANDLER.get(i);
            
            if(ch.getHandUsed().equals(handUsed) && ch.getOrientation().equals(orientation) && ch.getHandPart().equals(handPart))
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
        MAP_COLOR_HANDLER.clear();
        
        try
        {
            ResultSet rs = DB.executeQuery("SELECT * FROM joint");
            
            while(rs.next())
            {
                int id = rs.getInt(1);
                MAP_COLOR_HANDLER.put(id, new ColorHandler(id));
            }
        }
        
        catch(SQLException | NullPointerException ex)
        {
            System.out.println("Error getting color handlers.");
        }
        
        MEDIA_PANEL.removeAllColorListeners();
        
        for(Integer i : MAP_COLOR_HANDLER.keySet())
        {
            MEDIA_PANEL.addColorListener(MAP_COLOR_HANDLER.get(i));
        }
        
        DB.setIsConnected(false);
    }
    
    private void getSignLanguages()
    {
        try
        {
            DB.setIsConnected(true);
            DB.setIsUpdatable(false);
            LIST_SIGN_LANGUAGE.clear();
            
            ResultSet rs = DB.executeQuery("SELECT * FROM sign_language ORDER BY id");
            DefaultListModel signLanguages = new DefaultListModel();
            
            while(rs.next())
            {
                SignLanguage sl = SignLanguage.getSignLanguage(rs.getInt(1));
                
                LIST_SIGN_LANGUAGE.add(sl);
                signLanguages.addElement(sl.getText());
            }
            
            TOOL_BAR.JLIST_SIGN_LANGUAGES.setModel(signLanguages);
        }
        
        catch(SQLException ex)
        {
            
        }
        
        finally
        {
            DB.setIsUpdatable(false);
            DB.setIsConnected(false);
            TOOL_BAR.TEXT_FIELD_SEARCH.setText("");
        }
    }
    
    /**
     * Configures the event listeners for each component.
     */
    private void configureComponents()
    {
        /* ActionListeners: */
        CAPTURE_BAR.JTOGGLEBUTTON_PLAY.addActionListener((ActionEvent ae) ->
        {
            boolean isSelected = CAPTURE_BAR.JTOGGLEBUTTON_PLAY.isSelected();
            boolean isStatic = CAPTURE_BAR.JTOGGLEBUTTON_STATIC_MODE.isSelected();
            
            if(isStatic)
            {
                MEDIA_PANEL.setImageMode(isSelected ? ImageBox.SLIDE_MODE : ImageBox.NORMAL_MODE);
            }
            
            else
            {
                if(isSelected)
                {
                    if(MEDIA_PANEL.isRunning())
                    {
                        MEDIA_PANEL.resumeVideoCapture();
                    }
                    
                    else
                    {
                        MEDIA_PANEL.startVideoCapture();
                    }
                }
                
                else
                {
                    MEDIA_PANEL.pauseVideoCapture();
                }
            }
            
            CAPTURE_BAR.JTOGGLEBUTTON_PLAY.setText(isSelected ? "PAUSE" : "PLAY");
        });

        CAPTURE_BAR.JBUTTON_PREVIOUS.addActionListener((ActionEvent ae) ->
        {
            MEDIA_PANEL.previousImage();
        });
        
        CAPTURE_BAR.JBUTTON_SCAN.addActionListener((ActionEvent ae) ->
        {
            MEDIA_PANEL.scanImage();
            MEDIA_PANEL.viewSelectedAreas(true);
        });
        
        CAPTURE_BAR.JTOGGLEBUTTON_RECORD.addActionListener((ActionEvent ae) ->
        {
            boolean isSelected = CAPTURE_BAR.JTOGGLEBUTTON_RECORD.isSelected();

            if(isSelected)
            {
                MEDIA_PANEL.clearRecordedGesture();
            }

            else
            {
                MEDIA_PANEL.pauseVideoCapture();
                CAPTURE_BAR.JTOGGLEBUTTON_PLAY.setSelected(false);
                CAPTURE_BAR.JTOGGLEBUTTON_PLAY.setText("PLAY");
            }

            MEDIA_PANEL.setEnableRecordImages(isSelected);
            CAPTURE_BAR.JTOGGLEBUTTON_RECORD.setText(isSelected ? "STOP" : "RECORD");
        });

        CAPTURE_BAR.JBUTTON_FORWARD.addActionListener((ActionEvent ae) ->
        {
            MEDIA_PANEL.nextImage();
        });
        
        CAPTURE_BAR.JBUTTON_ADD.addActionListener((ActionEvent ae) ->
        {
            final JFileChooser FILE_CHOOSER = new JFileChooser();
            
            FILE_CHOOSER.setMultiSelectionEnabled(true);
            FILE_CHOOSER.setCurrentDirectory(new File("./img/"));
            
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
                        MEDIA_PANEL.addImage(new MarvinImage(ImageIO.read(fileImage)));
                    }
                }
            }

            catch(HeadlessException | IOException | NullPointerException ex)
            {
                JOptionPane.showMessageDialog(this, "Cannot read selected file.", "Image Error", JOptionPane.ERROR_MESSAGE);
            }
            
            MEDIA_PANEL.clearRecordedGesture();
        });
        
        CAPTURE_BAR.JBUTTON_REMOVE.addActionListener((ActionEvent ae) ->
        {
            if(MEDIA_PANEL.imageCount() > 0)
            {
                final String[] OPTIONS = {"Current Image", "All Images", "Cancel"};
                int option = JOptionPane.showOptionDialog(null
                                                        , "Would you like to remove:"
                                                        , "Remove Image"
                                                        , JOptionPane.YES_NO_CANCEL_OPTION
                                                        , JOptionPane.QUESTION_MESSAGE
                                                        , null
                                                        , OPTIONS
                                                        , OPTIONS[0]);

                switch(option)
                {
                    case JOptionPane.YES_OPTION:

                        MEDIA_PANEL.removeImage();
                        break;

                    case JOptionPane.NO_OPTION:

                        MEDIA_PANEL.clearImages();
                        break;
                }
            }
            
            MEDIA_PANEL.clearRecordedGesture();
        });
        
        CAPTURE_BAR.JBUTTON_SAVE.addActionListener((ActionEvent ae) ->
        {
            JOptionPane.showMessageDialog(null, "This feature is not yet available.");
        });
        
        CAPTURE_BAR.JTOGGLEBUTTON_STATIC_MODE.addActionListener((ActionEvent ae) ->
        {
            MEDIA_PANEL.pauseVideoCapture();
            CAPTURE_BAR.JTOGGLEBUTTON_PLAY.setSelected(false);
        });
        
        CAPTURE_BAR.JTOGGLEBUTTON_MOTION_MODE.addActionListener((ActionEvent ae) ->
        {
            MEDIA_PANEL.setImageMode(ImageBox.NORMAL_MODE);
            CAPTURE_BAR.JTOGGLEBUTTON_PLAY.setSelected(false);
        });
        
        TOOL_BAR.BUTTON_TRANSLATE.addActionListener((ActionEvent ae) ->
        {
            Gesture gesture = new Gesture();
            int imageCount = MEDIA_PANEL.imageCount();
            
            if(imageCount > 0)
            {
                final String[] OPTIONS = {"Current Image", "All Images", "Cancel"};
                
                int option = JOptionPane.showOptionDialog(this
                                                        , "Would you like to translate:"
                                                        , "Translation"
                                                        , JOptionPane.YES_NO_CANCEL_OPTION
                                                        , JOptionPane.QUESTION_MESSAGE
                                                        , null
                                                        , OPTIONS
                                                        , OPTIONS[0]);
                MarvinImage img = null;
                Skeleton skeleton = null;
                        
                switch(option)
                {
                    case JOptionPane.YES_OPTION:
                        
                        MEDIA_PANEL.scanImage();
                        
                        img = MEDIA_PANEL.getImage();
                        skeleton = new Skeleton();
                        skeleton.acceptanceRadius = 5;
                        
                        for(Rectangle r : MEDIA_PANEL.getSelectedAreas())
                        {
                            Point p = new Point((int)r.getCenterX(), (int)r.getCenterY());
                            skeleton.addJoint(new Joint(new Color(img.getIntColor(p.x, p.y)), p));
                        }
                        
                        gesture.addSkeleton(skeleton);
                        gesture.acceptanceRadius = 5;
                        
                        break;
                        
                    case JOptionPane.NO_OPTION:
                        
                        for(int i = 0; i < imageCount; i++)
                        {
                            MEDIA_PANEL.setImage(i);
                            MEDIA_PANEL.scanOriginalImage();
                            
                            img = MEDIA_PANEL.getOriginalImage();
                            skeleton = new Skeleton();
                            
                            skeleton.acceptanceRadius = 5;
                            
                            for(Rectangle r : MEDIA_PANEL.getSelectedAreas())
                            {
                                Point p = new Point((int)r.getCenterX(), (int)r.getCenterY());
                                skeleton.addJoint(new Joint(new Color(img.getIntColor(p.x, p.y)), p));
                            }
                            
                            gesture.addSkeleton(skeleton);
                        }
                        
                        gesture.acceptanceRadius = 5;
                        
                        break;
                }
                
                if(option == 0 || option == 1)
                {
                    TOOL_BAR.TEXT_AREA_PROCESS.setText("Start translation."
                                                     + "\nAnalyzing gesture."
                                                     + "\nDividing into skeleton(s).");

                    for(Skeleton s : gesture.getSkeletons())
                    {
                        TOOL_BAR.TEXT_AREA_PROCESS.setText(TOOL_BAR.TEXT_AREA_PROCESS.getText()
                                                         + "\nScanning for joint(s).");

                        for(Joint j : s.getJoints())
                        {
                            TOOL_BAR.TEXT_AREA_PROCESS.setText(TOOL_BAR.TEXT_AREA_PROCESS.getText()
                                                             + "\n" + j);
                        }
                    }

                    TOOL_BAR.TEXT_AREA_PROCESS.setText(TOOL_BAR.TEXT_AREA_PROCESS.getText()
                                                     + "\nFinding for possible match-ups."
                                                     + "\nAccept highest percentage match-up."
                                                     + "\nTranslating into text.");

                    TOOL_BAR.TEXT_AREA_OUTPUT.setText(SignLanguage.translateGesture(gesture));

                    TOOL_BAR.TEXT_AREA_PROCESS.setText(TOOL_BAR.TEXT_AREA_PROCESS.getText()
                                                     + "\nEnd of translation.");
                }
            }
        });
        
        TOOL_BAR.BUTTON_SELECT_JOINT.addActionListener((ActionEvent ae) ->
        {
            MEDIA_PANEL.clearSelectedAreas();
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
            TOOL_BAR.DROP_DOWN_FINGER_PARTS.setEnabled(!MAP_COLOR_HANDLER.get(selectedColorHandlerID).handPart.equals("palm"));
        });

        TOOL_BAR.DROP_DOWN_FINGER_PARTS.addActionListener((ActionEvent ae) ->
        {
            selectJoint();
        });
        
        TOOL_BAR.BUTTON_LEARN.addActionListener((ActionEvent ae) ->
        {
            Gesture gesture = new Gesture();
            int imageCount = MEDIA_PANEL.imageCount();
            
            if(imageCount > 0)
            {
                final String[] OPTIONS = {"Current Image", "All Images", "Cancel"};
                
                int option = JOptionPane.showOptionDialog(null
                                                        , "Would you like to learn:"
                                                        , "Learn Sign Language"
                                                        , JOptionPane.YES_NO_CANCEL_OPTION
                                                        , JOptionPane.QUESTION_MESSAGE
                                                        , null
                                                        , OPTIONS
                                                        , OPTIONS[0]);
                MarvinImage img = null;
                Skeleton skeleton = null;
                        
                switch(option)
                {
                    case JOptionPane.YES_OPTION:
                        
                        MEDIA_PANEL.scanOriginalImage();
                        
                        img = MEDIA_PANEL.getOriginalImage();
                        skeleton = new Skeleton();
                        
                        for(Rectangle r : MEDIA_PANEL.getSelectedAreas())
                        {
                            Point p = new Point((int)r.getCenterX(), (int)r.getCenterY());
                            skeleton.addJoint(new Joint(new Color(img.getIntColor(p.x, p.y)), p));
                        }
                        
                        gesture.addSkeleton(skeleton);
                        
                        break;
                        
                    case JOptionPane.NO_OPTION:
                        
                        for(int i = 0; i < imageCount; i++)
                        {
                            MEDIA_PANEL.setImage(i);
                            MEDIA_PANEL.scanOriginalImage();
                            
                            img = MEDIA_PANEL.getOriginalImage();
                            skeleton = new Skeleton();
                            
                            for(Rectangle r : MEDIA_PANEL.getSelectedAreas())
                            {
                                skeleton.addJoint(new Joint(new Color(img.getIntColor((int)r.getCenterX(), (int)r.getCenterY()))));
                            }
                            
                            gesture.addSkeleton(skeleton);
                        }
                        
                        break;
                }
            }
            
            if(!gesture.isEmpty())
            {
                String text = JOptionPane.showInputDialog("Enter text translation:");

                if(SignLanguage.addSignLanguage(gesture, text))
                {
                    JOptionPane.showMessageDialog(this, "New sign language learned.", "Learn", JOptionPane.INFORMATION_MESSAGE);
                    getSignLanguages();
                }

                else
                {
                    JOptionPane.showMessageDialog(this, "Failed to learn new sign language.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        TOOL_BAR.BUTTON_REMOVE.addActionListener((ActionEvent ae) ->
        {
            int[] selectedIndeces = TOOL_BAR.JLIST_SIGN_LANGUAGES.getSelectedIndices();
            
            for(int i : selectedIndeces)
            {
                SignLanguage.removeSignLanguage(LIST_SIGN_LANGUAGE.get(i).ID);
            }
            
            getSignLanguages();
        });
        
        /* ChangeListeners: */
        TOOL_BAR.SLIDER_RED.addChangeListener((ChangeEvent ce) ->
        {
            ColorHandler colorHandler = MAP_COLOR_HANDLER.get(selectedColorHandlerID);

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
            ColorHandler colorHandler = MAP_COLOR_HANDLER.get(selectedColorHandlerID);

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
            ColorHandler colorHandler = MAP_COLOR_HANDLER.get(selectedColorHandlerID);

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
            ColorHandler colorHandler = MAP_COLOR_HANDLER.get(selectedColorHandlerID);

            if(colorHandler != null)
            {
                int alphaValue = TOOL_BAR.SLIDER_ALPHA.getValue();
                
                colorHandler.setAlphaLimit(alphaValue);
                TOOL_BAR.LABEL_ALPHA.setText("Alpha: " + alphaValue);
                TOOL_BAR.BUTTON_LEFT_COLOR.setBackground(colorHandler.getMinTargetColor());
                TOOL_BAR.BUTTON_RIGHT_COLOR.setBackground(colorHandler.getMaxTargetColor());
            }
        });
        
        /* ListSelectionListener: */
        TOOL_BAR.JLIST_SIGN_LANGUAGES.addListSelectionListener((ListSelectionEvent lse) ->
        {
            int selectedItems = TOOL_BAR.JLIST_SIGN_LANGUAGES.getSelectedValuesList().size();
            
            if(selectedItems == 1)
            {
                SignLanguage selectedSignLanguage = null;
                String text = TOOL_BAR.JLIST_SIGN_LANGUAGES.getSelectedValue().toString();
                
                for(SignLanguage sl : LIST_SIGN_LANGUAGE)
                {
                    if(sl.getText().equals(text))
                    {
                        selectedSignLanguage = sl;
                        break;
                    }
                }
                
                Gesture g = selectedSignLanguage.getGesture();
                int i = 0;
                
                TOOL_BAR.JTEXTAREA_INFORMATION.setText("ID: " + selectedSignLanguage.ID
                                                     + "\nTranslation: " + selectedSignLanguage.getText()
                                                     + "\nSkeleton Count: " + g.skeletonCount() + "\n");
                
                for(Skeleton s : g.getSkeletons())
                {
                    TOOL_BAR.JTEXTAREA_INFORMATION.setText(TOOL_BAR.JTEXTAREA_INFORMATION.getText()
                                                         + "\nSkeleton Number: " + ++i
                                                         + "\nJoint Count: " + s.jointCount() + "\n");
                    
                    for(Joint j : s.getJoints())
                    {
                        TOOL_BAR.JTEXTAREA_INFORMATION.setText(TOOL_BAR.JTEXTAREA_INFORMATION.getText()
                                                             + "\n" + j);
                    }
                }
            }
            
            else
            {
                TOOL_BAR.JTEXTAREA_INFORMATION.setText("Selected Items: " + selectedItems);
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
                    ColorHandler colorHandler = MAP_COLOR_HANDLER.get(selectedColorHandlerID);
                    
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
        
        TOOL_BAR.TEXT_FIELD_SEARCH.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent ke)
            {
                String subString = TOOL_BAR.TEXT_FIELD_SEARCH.getText();
                DefaultListModel selection = new DefaultListModel();
                
                for(SignLanguage sl : LIST_SIGN_LANGUAGE)
                {
                    String text = sl.getText();
                    
                    if(text.contains(subString))
                    {
                        selection.addElement(text);
                    }
                }
                
                TOOL_BAR.JLIST_SIGN_LANGUAGES.setModel(selection);
                TOOL_BAR.TEXT_FIELD_SEARCH.setForeground(selection.isEmpty() ? Color.RED : Color.BLACK);
            }
        });
        
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
        
        MEDIA_PANEL.addMouseListener(MOUSE_HANDLER);
        MEDIA_PANEL.addMouseMotionListener(MOUSE_HANDLER);
    }
    
    /**
     * Selects a joint from the database.
     */
    private void selectJoint()
    {
        boolean isSelected = TOOL_BAR.BUTTON_SELECT_JOINT.isSelected();
        ColorHandler colorHandler = getColorHandler((String)TOOL_BAR.DROP_DOWN_HANDS.getSelectedItem(),
                                                    (String)TOOL_BAR.DROP_DOWN_HAND_ORIENTATION.getSelectedItem(),
                                                    (String)TOOL_BAR.DROP_DOWN_HAND_PARTS.getSelectedItem());

        boolean isFound = colorHandler != null;
        boolean isEditable = TOOL_BAR.BUTTON_SELECT_JOINT.isSelected();

        TOOL_BAR.setEditableColorPanel(isEditable);
        TOOL_BAR.setEnabledColorPanel(isFound);
        TOOL_BAR.setEnabledColorRangePanel(isFound & isSelected);
        TOOL_BAR.setEnabledJointPanel(isSelected);

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
        MEDIA_PANEL.setImageMode(ImageBox.NORMAL_MODE);
        MEDIA_PANEL.pauseVideoCapture();
        
        MarvinImage image = MEDIA_PANEL.getImage();

        if(TOOL_BAR.TABBED_PANE_CONTENT.getSelectedIndex() == 1 && !TOOL_BAR.BUTTON_SELECT_JOINT.isSelected() && image != null)
        {
            point_mouse = p;
            Rectangle region = new Rectangle(point_mouse.x - 5, point_mouse.y - 5, 9, 9);
            color_pixel = new Color(image.getIntColor(point_mouse.x, point_mouse.y), true);
        
            MEDIA_PANEL.pauseVideoCapture();
            MEDIA_PANEL.clearSelectedAreas();
            MEDIA_PANEL.selectArea(region);
            MEDIA_PANEL.viewSelectedAreas(true);
            
            CAPTURE_BAR.JTOGGLEBUTTON_PLAY.setSelected(false);
            CAPTURE_BAR.JTOGGLEBUTTON_PLAY.setText("PLAY");
            
            TOOL_BAR.TEXT_FIELD_X.setText(point_mouse.x + "");
            TOOL_BAR.TEXT_FIELD_Y.setText(point_mouse.y + "");
            TOOL_BAR.TEXT_FIELD_RED.setText(color_pixel.getRed() + "");
            TOOL_BAR.TEXT_FIELD_GREEN.setText(color_pixel.getGreen() + "");
            TOOL_BAR.TEXT_FIELD_BLUE.setText(color_pixel.getBlue() + "");
            TOOL_BAR.TEXT_FIELD_ALPHA.setText(color_pixel.getAlpha() + "");
            TOOL_BAR.BUTTON_LEFT_COLOR.setBackground(Color.LIGHT_GRAY);
            TOOL_BAR.BUTTON_MEDIAN_COLOR.setBackground(color_pixel);
            TOOL_BAR.BUTTON_RIGHT_COLOR.setBackground(Color.LIGHT_GRAY);
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
        
        SpringLayout springLayout_content = new SpringLayout();
        
        getColorHandlers();
        getSignLanguages();
        
        springLayout_content.putConstraint(NORTH, MENU_BAR, 1, NORTH, CONTENT_PANE);
        springLayout_content.putConstraint(EAST, MENU_BAR, 1, EAST, CONTENT_PANE);
        springLayout_content.putConstraint(WEST, MENU_BAR, 1, WEST, CONTENT_PANE);
        
        springLayout_content.putConstraint(NORTH, MEDIA_PANEL, 0, SOUTH, MENU_BAR);
        springLayout_content.putConstraint(EAST, MEDIA_PANEL, 0, WEST, TOOL_BAR);
        springLayout_content.putConstraint(WEST, MEDIA_PANEL, 0, WEST, CONTENT_PANE);
        springLayout_content.putConstraint(SOUTH, MEDIA_PANEL, 0, NORTH, CAPTURE_BAR);
        
        springLayout_content.putConstraint(NORTH, TOOL_BAR, 0, SOUTH, MENU_BAR);
        springLayout_content.putConstraint(EAST, TOOL_BAR, 0, EAST, CONTENT_PANE);
        springLayout_content.putConstraint(WEST, TOOL_BAR, -300, EAST, TOOL_BAR);
        springLayout_content.putConstraint(SOUTH, TOOL_BAR, 0, NORTH, CAPTURE_BAR);
        
        springLayout_content.putConstraint(NORTH, CAPTURE_BAR, -70, SOUTH, CAPTURE_BAR);
        springLayout_content.putConstraint(EAST, CAPTURE_BAR, 0, EAST, CONTENT_PANE);
        springLayout_content.putConstraint(SOUTH, CAPTURE_BAR, 0, SOUTH, CONTENT_PANE);
        springLayout_content.putConstraint(WEST, CAPTURE_BAR, 0, WEST, CONTENT_PANE);
        
        setLayout(springLayout_content);
        add(MENU_BAR);
        add(MEDIA_PANEL);
        add(TOOL_BAR);
        add(CAPTURE_BAR);
    }
    
    /**
     * The constructor for this class.
     */
    public MainFrame()
    {
        CAPTURE_BAR = new CaptureBar();
        MAP_COLOR_HANDLER = new HashMap();
        MENU_BAR = new MenuBar();
        TOOL_BAR = new ToolBar();
        MEDIA_PANEL = new MediaPanel();
        LIST_SIGN_LANGUAGE = new ArrayList();
        
        setupComponents();
        configureComponents();
    }
    
    /**
     * Loads the graphical user interface.
     */
    public void loadGUI()
    {
        setTitle("DOTHS Sign Language Translator");
        setIconImage(new ImageIcon("./img/logo_197x60.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 610));
        setSize(new Dimension(1000, 610));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }
}