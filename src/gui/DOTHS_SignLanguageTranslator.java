/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 * The class that runs the DOTHS sign language translator.
 * @author Liel Tan
 */
public final class DOTHS_SignLanguageTranslator implements Runnable
{
    /**
     * The frame for the GUI of the DOTHS Sign Language Translator.
     */
    private MainFrame mainFrame;
    
    @Override
    public void run()
    {
        mainFrame = new MainFrame();
        mainFrame.loadGUI();
    }
    
    /**
     * The main method for running the DOTHS Sign Language Translator.
     * @param args Not used.
     */
    public static void main(String[] args)
    {
        Thread doths_signLanguageTranslator = new Thread(new DOTHS_SignLanguageTranslator());
        doths_signLanguageTranslator.start();
    }
}