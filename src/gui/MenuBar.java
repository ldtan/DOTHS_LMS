/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

/**
 * The class that builds the menu bar component for the frame.
 * @author Liel Tan
 */
public final class MenuBar extends JMenuBar
{
    protected final JMenu MENU_ABOUT;
    protected final JMenu MENU_CALIBRATION;
    protected final JMenu MENU_CAPTURE;
    protected final JMenu MENU_MEDIA;
    
    /**
     * Setup the components for the menu bar.
     */
    private void setupComponents()
    {
        add(MENU_MEDIA);
        add(MENU_CAPTURE);
        add(MENU_CALIBRATION);
        add(MENU_ABOUT);
    }
    
    /**
     * The constructor for this class.
     */
    public MenuBar()
    {
        MENU_ABOUT = new JMenu("About");
        MENU_CALIBRATION = new JMenu("Calibration");
        MENU_CAPTURE = new JMenu("Capture");
        MENU_MEDIA = new JMenu("Media");
        
        setupComponents();
    }
}