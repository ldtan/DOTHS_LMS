/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SpringLayout;

/**
 * The class that builds the capture bar component for the frame.
 * @author Liel Tan
 */
public final class CaptureBar extends JPanel
{
    private final JPanel JPANEL_MEDIA;
    
    protected final JToggleButton JTOGGLEBUTTON_PLAY;
    protected final JButton JBUTTON_PREVIOUS;
    protected final JButton JBUTTON_FORWARD;
    protected final JButton JBUTTON_ADD;
    protected final JButton JBUTTON_REMOVE;
    protected final JButton JBUTTON_SCAN;
    protected final JToggleButton JTOGGLEBUTTON_RECORD;
    protected final JButton JBUTTON_SAVE;
    
    protected final JToggleButton JTOGGLEBUTTON_MOTION_MODE;
    protected final JToggleButton JTOGGLEBUTTON_STATIC_MODE;
    
    /**
     * Configures the event listeners for each component.
     */
    private void configureComponents()
    {
        JTOGGLEBUTTON_PLAY.addActionListener((ActionEvent ae) ->
        {
            JTOGGLEBUTTON_PLAY.setText(JTOGGLEBUTTON_PLAY.isSelected() ? "PAUSE" : "PLAY");
        });
        
        JTOGGLEBUTTON_RECORD.addActionListener((ActionEvent ae) ->
        {
            JTOGGLEBUTTON_RECORD.setText(JTOGGLEBUTTON_RECORD.isSelected() ? "STOP" : "RECORD");
        });
        
        JTOGGLEBUTTON_STATIC_MODE.addActionListener((ActionEvent ae) ->
        {
            JPANEL_MEDIA.removeAll();
            JPANEL_MEDIA.add(JTOGGLEBUTTON_PLAY);
            JPANEL_MEDIA.add(JBUTTON_PREVIOUS);
            JPANEL_MEDIA.add(JBUTTON_FORWARD);
            JPANEL_MEDIA.add(JBUTTON_ADD);
            JPANEL_MEDIA.add(JBUTTON_REMOVE);
            JPANEL_MEDIA.add(JBUTTON_SCAN);
            JPANEL_MEDIA.add(JBUTTON_SAVE);
            
            reload();
        });
        
        JTOGGLEBUTTON_MOTION_MODE.addActionListener((ActionEvent ae) ->
        {
            JPANEL_MEDIA.removeAll();
            JPANEL_MEDIA.add(JTOGGLEBUTTON_PLAY);
            JPANEL_MEDIA.add(JBUTTON_PREVIOUS);
            JPANEL_MEDIA.add(JBUTTON_FORWARD);
            JPANEL_MEDIA.add(JTOGGLEBUTTON_RECORD);
            JPANEL_MEDIA.add(JBUTTON_SAVE);
            
            reload();
        });
    }
    
    /**
     * Setup the components for the capture bar.
     */
    private void setupComponents()
    {
        final String W = SpringLayout.WEST;
        final String E = SpringLayout.EAST;
        final String VC = SpringLayout.VERTICAL_CENTER;
        
        JPanel jPanel_mode = new JPanel();
        SpringLayout springLayout_content = new SpringLayout();
        ButtonGroup buttonGroup_mode = new ButtonGroup();
        
        JPANEL_MEDIA.setLayout(new GridLayout(1, 5, 5, 5));
        JPANEL_MEDIA.add(JTOGGLEBUTTON_PLAY);
        JPANEL_MEDIA.add(JBUTTON_PREVIOUS);
        JPANEL_MEDIA.add(JBUTTON_FORWARD);
        JPANEL_MEDIA.add(JBUTTON_ADD);
        JPANEL_MEDIA.add(JBUTTON_REMOVE);
        JPANEL_MEDIA.add(JBUTTON_SCAN);
        JPANEL_MEDIA.add(JBUTTON_SAVE);
        
        buttonGroup_mode.add(JTOGGLEBUTTON_MOTION_MODE);
        buttonGroup_mode.add(JTOGGLEBUTTON_STATIC_MODE);
        
        JTOGGLEBUTTON_STATIC_MODE.setSelected(true);
        
        jPanel_mode.setLayout(new GridLayout(1, 2, 5, 5));
        jPanel_mode.add(JTOGGLEBUTTON_STATIC_MODE);
        jPanel_mode.add(JTOGGLEBUTTON_MOTION_MODE);
        
        springLayout_content.putConstraint(W, JPANEL_MEDIA, 20, W, this);
        springLayout_content.putConstraint(VC, JPANEL_MEDIA, 0, VC, this);
        springLayout_content.putConstraint(E, jPanel_mode, -20, E, this);
        springLayout_content.putConstraint(VC, jPanel_mode, 0, VC, this);
        
        add(JPANEL_MEDIA);
        add(jPanel_mode);
        setLayout(springLayout_content);
    }
    
    /**
     * The constructor for this class.
     */
    public CaptureBar()
    {
        JBUTTON_ADD = new JButton("ADD");
        JTOGGLEBUTTON_MOTION_MODE = new JToggleButton("MOTION");
        JTOGGLEBUTTON_STATIC_MODE = new JToggleButton("STATIC");
        JBUTTON_FORWARD = new JButton("FORWARD");
        JTOGGLEBUTTON_PLAY = new JToggleButton("PLAY");
        JTOGGLEBUTTON_RECORD = new JToggleButton("RECORD");
        JBUTTON_PREVIOUS = new JButton("PREVIOUS");
        JBUTTON_REMOVE = new JButton("REMOVE");
        JBUTTON_SAVE = new JButton("SAVE");
        JBUTTON_SCAN = new JButton("SCAN");
        JPANEL_MEDIA = new JPanel();
        
        setupComponents();
        configureComponents();
    }
    
    public void reload()
    {
        revalidate();
        repaint();
    }
}