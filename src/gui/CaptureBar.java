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
    private final ButtonGroup BUTTON_GROUP_MODE;
    private final SpringLayout LAYOUT_CONTENT;
    private final GridLayout LAYOUT_MODE;
    private final GridLayout LAYOUT_VIDEO;
    private final JPanel PANEL_MODE;
    private final JPanel PANEL_VIDEO;
    protected final JToggleButton BUTTON_CALIBRATION_MODE;
    protected final JToggleButton BUTTON_DICTIONARY_MODE;
    protected final JButton BUTTON_FORWARD;
    protected final JToggleButton BUTTON_PLAY;
    protected final JToggleButton BUTTON_RECORD;
    protected final JButton BUTTON_REWIND;
    protected final JButton BUTTON_SAVE;
    protected final JToggleButton BUTTON_TRANSLATOR_MODE;
    
    /**
     * Configures the event listeners for each component.
     */
    private void configureComponents()
    {
        BUTTON_PLAY.addActionListener((ActionEvent ae) ->
        {
            BUTTON_PLAY.setText(BUTTON_PLAY.isSelected() ? "PAUSE" : "PLAY");
        });
        
        BUTTON_RECORD.addActionListener((ActionEvent ae) ->
        {
            BUTTON_RECORD.setText(BUTTON_RECORD.isSelected() ? "STOP" : "RECORD");
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
        
        PANEL_VIDEO.setLayout(LAYOUT_VIDEO);
        PANEL_VIDEO.add(BUTTON_PLAY);
        PANEL_VIDEO.add(BUTTON_REWIND);
        PANEL_VIDEO.add(BUTTON_RECORD);
        PANEL_VIDEO.add(BUTTON_FORWARD);
        PANEL_VIDEO.add(BUTTON_SAVE);
        
        BUTTON_TRANSLATOR_MODE.setSelected(true);
        
        BUTTON_GROUP_MODE.add(BUTTON_TRANSLATOR_MODE);
        BUTTON_GROUP_MODE.add(BUTTON_CALIBRATION_MODE);
        BUTTON_GROUP_MODE.add(BUTTON_DICTIONARY_MODE);
        
        PANEL_MODE.add(BUTTON_TRANSLATOR_MODE);
        PANEL_MODE.add(BUTTON_CALIBRATION_MODE);
        PANEL_MODE.add(BUTTON_DICTIONARY_MODE);
        PANEL_MODE.setLayout(LAYOUT_MODE);
        
        LAYOUT_CONTENT.putConstraint(W, PANEL_VIDEO, 20, W, this);
        LAYOUT_CONTENT.putConstraint(VC, PANEL_VIDEO, 0, VC, this);
        LAYOUT_CONTENT.putConstraint(E, PANEL_MODE, -20, E, this);
        LAYOUT_CONTENT.putConstraint(VC, PANEL_MODE, 0, VC, this);
        
        add(PANEL_VIDEO);
        add(PANEL_MODE);
        setLayout(LAYOUT_CONTENT);
    }
    
    /**
     * The constructor for this class.
     */
    public CaptureBar()
    {
        BUTTON_CALIBRATION_MODE = new JToggleButton("CALIBRATION");
        BUTTON_DICTIONARY_MODE = new JToggleButton("DICTIONARY");
        BUTTON_FORWARD = new JButton("FORWARD");
        BUTTON_PLAY = new JToggleButton("PLAY");
        BUTTON_RECORD = new JToggleButton("RECORD");
        BUTTON_REWIND = new JButton("REWIND");
        BUTTON_SAVE = new JButton("SAVE");
        BUTTON_TRANSLATOR_MODE = new JToggleButton("TRANSLATOR");
        BUTTON_GROUP_MODE = new ButtonGroup();
        LAYOUT_CONTENT = new SpringLayout();
        LAYOUT_MODE = new GridLayout(1, 2, 5, 5);
        LAYOUT_VIDEO = new GridLayout(1, 5, 5, 5);
        PANEL_MODE = new JPanel();
        PANEL_VIDEO = new JPanel();
        
        setupComponents();
        configureComponents();
    }
}