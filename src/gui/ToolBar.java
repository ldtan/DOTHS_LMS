/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SpringLayout;
import obj.SignLanguage;

/**
 * The class that build the tool bar component for the frame.
 * @author Liel Tan
 */
public final class ToolBar extends JPanel
{
    protected final JButton BUTTON_CLEAR_OUTPUT;
    protected final JButton BUTTON_CLEAR_PROCESS;
    protected final JButton BUTTON_EDIT;
    protected final JButton BUTTON_MEDIAN_COLOR;
    protected final JButton BUTTON_LEFT_COLOR;
    protected final JButton BUTTON_RIGHT_COLOR;
    protected final JButton BUTTON_LEARN;
    protected final JButton BUTTON_REMOVE;
    protected final JToggleButton BUTTON_SELECT_JOINT;
    protected final JButton BUTTON_TRANSLATE;
    protected final JComboBox DROP_DOWN_FINGER_PARTS;
    protected final JComboBox DROP_DOWN_HANDS;
    protected final JComboBox DROP_DOWN_HAND_ORIENTATION;
    protected final JComboBox DROP_DOWN_HAND_PARTS;
    protected final JLabel LABEL_ALPHA;
    protected final JLabel LABEL_BLUE;
    protected final JLabel LABEL_GREEN;
    protected final JLabel LABEL_RED;
    protected final JSlider SLIDER_ALPHA;
    protected final JSlider SLIDER_BLUE;
    protected final JSlider SLIDER_GREEN;
    protected final JSlider SLIDER_RED;
    protected final JTabbedPane TABBED_PANE_CONTENT;
    protected final JTextArea JTEXTAREA_INFORMATION;
    protected final JTextArea TEXT_AREA_OUTPUT;
    protected final JTextArea TEXT_AREA_PROCESS;
    protected final JTextField TEXT_FIELD_ALPHA;
    protected final JTextField TEXT_FIELD_BLUE;
    protected final JTextField TEXT_FIELD_GREEN;
    protected final JTextField TEXT_FIELD_RED;
    protected final JTextField TEXT_FIELD_SEARCH;
    protected final JTextField TEXT_FIELD_X;
    protected final JTextField TEXT_FIELD_Y;
    protected final JList JLIST_SIGN_LANGUAGES;
    
    /**
     * Setup the components for the tool bar.
     */
    private void setupComponents()
    {
        final String N = SpringLayout.NORTH;
        final String E = SpringLayout.EAST;
        final String S = SpringLayout.SOUTH;
        final String W = SpringLayout.WEST;
        final String VC = SpringLayout.VERTICAL_CENTER;
        final String HC = SpringLayout.HORIZONTAL_CENTER;
        
        JPanel jPanel_translator = new JPanel();
        JPanel jPanel_joint = new JPanel();
        JPanel jPanel_coordinates = new JPanel();
        JPanel jPanel_color = new JPanel();
        JPanel jPanel_colorRange = new JPanel();
        JPanel jPanel_colorView = new JPanel();
        JPanel jPanel_calibration = new JPanel();
        JPanel jPanel_learn = new JPanel();
        JPanel jPanel_dictionary = new JPanel();
        SpringLayout springLayout_translator = new SpringLayout();
        SpringLayout springLayout_calibration = new SpringLayout();
        SpringLayout springLayout_dictionary = new SpringLayout();
        JLabel jLabel_process = new JLabel("Process");
        JLabel jLabel_output = new JLabel("Output");
        JLabel jLabel_joint = new JLabel("JOINT");
        JLabel jLabel_coordinates = new JLabel("COORDINATES");
        JLabel jLabel_color = new JLabel("COLOR");
        JLabel jLabel_colorRange = new JLabel("COLOR RANGE");
        JLabel jLabel_search = new JLabel("Search:");
        JScrollPane jScrollPane_process = new JScrollPane(TEXT_AREA_PROCESS);
        JScrollPane jScrollPane_output = new JScrollPane(TEXT_AREA_OUTPUT);
        JScrollPane jScrollPane_signLanguage = new JScrollPane(JLIST_SIGN_LANGUAGES);
        JScrollPane jScrollPane_information = new JScrollPane(JTEXTAREA_INFORMATION);
        
        TEXT_AREA_PROCESS.setEditable(false);
        TEXT_AREA_OUTPUT.setEditable(false);
        
        springLayout_translator.putConstraint(N, jLabel_process, 5, N, jPanel_translator);
        springLayout_translator.putConstraint(W, jLabel_process, 5, W, jPanel_translator);
        
        springLayout_translator.putConstraint(N, jScrollPane_process, 5, S, jLabel_process);
        springLayout_translator.putConstraint(W, jScrollPane_process, 5, W, jPanel_translator);
        springLayout_translator.putConstraint(E, jScrollPane_process, -5, E, jPanel_translator);
        springLayout_translator.putConstraint(S, jScrollPane_process, 150, N, jScrollPane_process);
        
        springLayout_translator.putConstraint(N, BUTTON_CLEAR_PROCESS, 5, S, jScrollPane_process);
        springLayout_translator.putConstraint(E, BUTTON_CLEAR_PROCESS, -5, E, jScrollPane_process);
        
        springLayout_translator.putConstraint(N, jLabel_output, 20, S, BUTTON_CLEAR_PROCESS);
        springLayout_translator.putConstraint(W, jLabel_output, 5, W, jPanel_translator);
        
        springLayout_translator.putConstraint(N, jScrollPane_output, 5, S, jLabel_output);
        springLayout_translator.putConstraint(W, jScrollPane_output, 5, W, jPanel_translator);
        springLayout_translator.putConstraint(E, jScrollPane_output, -5, E, jPanel_translator);
        springLayout_translator.putConstraint(S, jScrollPane_output, 150, N, jScrollPane_output);
        
        springLayout_translator.putConstraint(N, BUTTON_CLEAR_OUTPUT, 5, S, jScrollPane_output);
        springLayout_translator.putConstraint(E, BUTTON_CLEAR_OUTPUT, -5, E, jScrollPane_output);
        
        springLayout_translator.putConstraint(N, BUTTON_TRANSLATE, 5, S, jScrollPane_output);
        springLayout_translator.putConstraint(E, BUTTON_TRANSLATE, -5, W, BUTTON_CLEAR_OUTPUT);
        
        jPanel_translator.setLayout(springLayout_translator);
        jPanel_translator.add(jLabel_process);
        jPanel_translator.add(jScrollPane_process);
        jPanel_translator.add(BUTTON_CLEAR_PROCESS);
        jPanel_translator.add(jLabel_output);
        jPanel_translator.add(jScrollPane_output);
        jPanel_translator.add(BUTTON_CLEAR_OUTPUT);
        jPanel_translator.add(BUTTON_TRANSLATE);
        jPanel_translator.setPreferredSize(new Dimension(230, 440));
        
        DROP_DOWN_FINGER_PARTS.setEnabled(false);
        DROP_DOWN_HANDS.setEnabled(false);
        DROP_DOWN_HAND_ORIENTATION.setEnabled(false);
        DROP_DOWN_HAND_PARTS.setEnabled(false);
        SLIDER_ALPHA.setEnabled(false);
        SLIDER_BLUE.setEnabled(false);
        SLIDER_GREEN.setEnabled(false);
        SLIDER_RED.setEnabled(false);
        
        jPanel_joint.setLayout(new GridLayout(4, 2, 5, 5));
        jPanel_joint.add(new JLabel("Hand:"));
        jPanel_joint.add(DROP_DOWN_HANDS);
        jPanel_joint.add(new JLabel("Orientation:"));
        jPanel_joint.add(DROP_DOWN_HAND_ORIENTATION);
        jPanel_joint.add(new JLabel("Part:"));
        jPanel_joint.add(DROP_DOWN_HAND_PARTS);
        jPanel_joint.add(new JLabel("Division:"));
        jPanel_joint.add(DROP_DOWN_FINGER_PARTS);
        
        TEXT_FIELD_X.setEditable(false);
        TEXT_FIELD_Y.setEditable(false);
        TEXT_FIELD_RED.setEditable(false);
        TEXT_FIELD_GREEN.setEditable(false);
        TEXT_FIELD_BLUE.setEditable(false);
        TEXT_FIELD_ALPHA.setEditable(false);
        
        jPanel_coordinates.setLayout(new GridLayout(2, 2, 5, 5));
        jPanel_coordinates.add(new JLabel("X:"));
        jPanel_coordinates.add(TEXT_FIELD_X);
        jPanel_coordinates.add(new JLabel("Y:"));
        jPanel_coordinates.add(TEXT_FIELD_Y);
        
        jPanel_color.setLayout(new GridLayout(4, 2, 5, 5));
        jPanel_color.add(new JLabel("Red:"));
        jPanel_color.add(TEXT_FIELD_RED);
        jPanel_color.add(new JLabel("Green:"));
        jPanel_color.add(TEXT_FIELD_GREEN);
        jPanel_color.add(new JLabel("Blue:"));
        jPanel_color.add(TEXT_FIELD_BLUE);
        jPanel_color.add(new JLabel("Alpha:"));
        jPanel_color.add(TEXT_FIELD_ALPHA);
        
        SLIDER_ALPHA.setMajorTickSpacing(5);
        SLIDER_ALPHA.setMinorTickSpacing(1);
        SLIDER_BLUE.setMajorTickSpacing(5);
        SLIDER_BLUE.setMinorTickSpacing(1);
        SLIDER_GREEN.setMajorTickSpacing(5);
        SLIDER_GREEN.setMinorTickSpacing(1);
        SLIDER_RED.setMajorTickSpacing(5);
        SLIDER_RED.setMinorTickSpacing(1);
        
        jPanel_colorRange.setLayout(new GridLayout(6, 2, 5, 5));
        jPanel_colorRange.add(LABEL_RED);
        jPanel_colorRange.add(SLIDER_RED);
        jPanel_colorRange.add(LABEL_GREEN);
        jPanel_colorRange.add(SLIDER_GREEN);
        jPanel_colorRange.add(LABEL_BLUE);
        jPanel_colorRange.add(SLIDER_BLUE);
        jPanel_colorRange.add(LABEL_ALPHA);
        jPanel_colorRange.add(SLIDER_ALPHA);
        
        jPanel_colorView.setLayout(new GridLayout(1, 3, 20, 0));
        jPanel_colorView.add(BUTTON_LEFT_COLOR);
        jPanel_colorView.add(BUTTON_MEDIAN_COLOR);
        jPanel_colorView.add(BUTTON_RIGHT_COLOR);
        
        springLayout_calibration.putConstraint(N, jLabel_joint, 5, N, jPanel_calibration);
        springLayout_calibration.putConstraint(W, jLabel_joint, 5, W, jPanel_calibration);
        
        springLayout_calibration.putConstraint(N, jPanel_joint, 10, S, jLabel_joint);
        springLayout_calibration.putConstraint(E, jPanel_joint, -5, E, jPanel_calibration);
        springLayout_calibration.putConstraint(W, jPanel_joint, 5, W, jPanel_calibration);
        
        springLayout_calibration.putConstraint(N, BUTTON_SELECT_JOINT, 10, S, jPanel_joint);
        springLayout_calibration.putConstraint(HC, BUTTON_SELECT_JOINT, 0, HC, jPanel_calibration);
        
        springLayout_calibration.putConstraint(N, jLabel_coordinates, 20, S, BUTTON_SELECT_JOINT);
        springLayout_calibration.putConstraint(W, jLabel_coordinates, 5, W, jPanel_calibration);
        
        springLayout_calibration.putConstraint(N, jPanel_coordinates, 10, S, jLabel_coordinates);
        springLayout_calibration.putConstraint(E, jPanel_coordinates, -5, E, jPanel_calibration);
        springLayout_calibration.putConstraint(W, jPanel_coordinates, 5, W, jPanel_calibration);
        
        springLayout_calibration.putConstraint(N, jLabel_color, 20, S, jPanel_coordinates);
        springLayout_calibration.putConstraint(W, jLabel_color, 5, W, jPanel_calibration);
        
        springLayout_calibration.putConstraint(N, jPanel_color, 10, S, jLabel_color);
        springLayout_calibration.putConstraint(E, jPanel_color, -5, E, jPanel_calibration);
        springLayout_calibration.putConstraint(W, jPanel_color, 5, W, jPanel_calibration);
        
        springLayout_calibration.putConstraint(N, jLabel_colorRange, 20, S, jPanel_color);
        springLayout_calibration.putConstraint(W, jLabel_colorRange, 5, W, jPanel_calibration);
        
        springLayout_calibration.putConstraint(N, jPanel_colorView, 10, S, jLabel_colorRange);
        springLayout_calibration.putConstraint(S, jPanel_colorView, 20, N, jPanel_colorView);
        springLayout_calibration.putConstraint(HC, jPanel_colorView, 0, HC, jPanel_calibration);
        
        springLayout_calibration.putConstraint(N, jPanel_colorRange, 10, S, jPanel_colorView);
        springLayout_calibration.putConstraint(E, jPanel_colorRange, -5, E, jPanel_calibration);
        springLayout_calibration.putConstraint(W, jPanel_colorRange, 5, W, jPanel_calibration);
        
        jPanel_calibration.setLayout(springLayout_calibration);
        jPanel_calibration.add(jLabel_joint);
        jPanel_calibration.add(jPanel_joint);
        jPanel_calibration.add(BUTTON_SELECT_JOINT);
        jPanel_calibration.add(jLabel_coordinates);
        jPanel_calibration.add(jPanel_coordinates);
        jPanel_calibration.add(jLabel_color);
        jPanel_calibration.add(jPanel_color);
        jPanel_calibration.add(jLabel_colorRange);
        jPanel_calibration.add(jPanel_colorView);
        jPanel_calibration.add(jPanel_colorRange);
        jPanel_calibration.setPreferredSize(new Dimension(230, 575));
        
        jPanel_learn.setLayout(new GridLayout(1, 3, 0, 0));
        jPanel_learn.add(BUTTON_LEARN);
        jPanel_learn.add(BUTTON_EDIT);
        jPanel_learn.add(BUTTON_REMOVE);
        
        JTEXTAREA_INFORMATION.setEditable(false);
        
        springLayout_dictionary.putConstraint(N, jLabel_search, 5, N, jPanel_dictionary);
        springLayout_dictionary.putConstraint(W, jLabel_search, 5, W, jPanel_dictionary);
        
        springLayout_dictionary.putConstraint(N, TEXT_FIELD_SEARCH, 5, N, jPanel_dictionary);
        springLayout_dictionary.putConstraint(E, TEXT_FIELD_SEARCH, -5, E, jPanel_dictionary);
        springLayout_dictionary.putConstraint(W, TEXT_FIELD_SEARCH, 5, E, jLabel_search);
        
        springLayout_dictionary.putConstraint(N, jScrollPane_signLanguage, 20, S, TEXT_FIELD_SEARCH);
        springLayout_dictionary.putConstraint(E, jScrollPane_signLanguage, -5, E, jPanel_dictionary);
        springLayout_dictionary.putConstraint(W, jScrollPane_signLanguage, 5, W, jPanel_dictionary);
        springLayout_dictionary.putConstraint(S, jScrollPane_signLanguage, 300, N, jScrollPane_signLanguage);
        
        springLayout_dictionary.putConstraint(N, jScrollPane_information, 20, S, jScrollPane_signLanguage);
        springLayout_dictionary.putConstraint(E, jScrollPane_information, -5, E, jPanel_dictionary);
        springLayout_dictionary.putConstraint(W, jScrollPane_information, 5, W, jPanel_dictionary);
        springLayout_dictionary.putConstraint(S, jScrollPane_information, 130, N, jScrollPane_information);
        
        springLayout_dictionary.putConstraint(N, jPanel_learn, 20, S, jScrollPane_information);
        springLayout_dictionary.putConstraint(E, jPanel_learn, -5, E, jPanel_dictionary);
        springLayout_dictionary.putConstraint(W, jPanel_learn, 5, W, jPanel_dictionary);
        
        jPanel_dictionary.setLayout(springLayout_dictionary);
        jPanel_dictionary.add(jLabel_search);
        jPanel_dictionary.add(TEXT_FIELD_SEARCH);
        jPanel_dictionary.add(jScrollPane_signLanguage);
        jPanel_dictionary.add(jScrollPane_information);
        jPanel_dictionary.add(jPanel_learn);
        jPanel_dictionary.setPreferredSize(new Dimension(230, 545));
        
        TABBED_PANE_CONTENT.addTab("Translator", new JScrollPane(jPanel_translator));
        TABBED_PANE_CONTENT.addTab("Calibration", new JScrollPane(jPanel_calibration));
        TABBED_PANE_CONTENT.addTab("Dictionary", new JScrollPane(jPanel_dictionary));
        
        setLayout(new GridLayout(1, 1));
        add(TABBED_PANE_CONTENT);
    }
    
    /**
     * The constructor for this class.
     */
    public ToolBar()
    {
        LABEL_ALPHA = new JLabel("Alpha:");
        LABEL_BLUE = new JLabel("Blue:");
        LABEL_GREEN = new JLabel("Green:");
        LABEL_RED = new JLabel("Red:");
        TABBED_PANE_CONTENT = new JTabbedPane();
        BUTTON_CLEAR_OUTPUT = new JButton("CLEAR");
        BUTTON_CLEAR_PROCESS = new JButton("CLEAR");
        BUTTON_EDIT = new JButton("EDIT");
        BUTTON_MEDIAN_COLOR = new JButton();
        BUTTON_LEARN = new JButton("LEARN");
        BUTTON_LEFT_COLOR = new JButton();
        BUTTON_REMOVE = new JButton("REMOVE");
        BUTTON_RIGHT_COLOR = new JButton();
        BUTTON_SELECT_JOINT = new JToggleButton("SELECT JOINT");
        BUTTON_TRANSLATE = new JButton("TRANSLATE");
        DROP_DOWN_FINGER_PARTS = new JComboBox(SignLanguage.ARRAY_FINGER_PARTS);
        DROP_DOWN_HANDS = new JComboBox(SignLanguage.ARRAY_HAND_USED);
        DROP_DOWN_HAND_ORIENTATION = new JComboBox(SignLanguage.ARRAY_HAND_ORIENTATION);
        DROP_DOWN_HAND_PARTS = new JComboBox(SignLanguage.ARRAY_HAND_PARTS);
        SLIDER_ALPHA = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        SLIDER_BLUE = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        SLIDER_GREEN = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        SLIDER_RED = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        JTEXTAREA_INFORMATION = new JTextArea();
        TEXT_AREA_OUTPUT = new JTextArea();
        TEXT_AREA_PROCESS = new JTextArea();
        TEXT_FIELD_ALPHA = new JTextField();
        TEXT_FIELD_BLUE = new JTextField();
        TEXT_FIELD_GREEN = new JTextField();
        TEXT_FIELD_RED = new JTextField();
        TEXT_FIELD_SEARCH = new JTextField();
        TEXT_FIELD_X = new JTextField();
        TEXT_FIELD_Y = new JTextField();
        JLIST_SIGN_LANGUAGES = new JList();
        
        setupComponents();
    }
    
    /**
     * Sets the color panel to accept or ignore user input.
     * @param state True for the color panel to accept user input, else false.
     */
    public void setEditableColorPanel(boolean state)
    {
        TEXT_FIELD_RED.setEditable(state);
        TEXT_FIELD_GREEN.setEditable(state);
        TEXT_FIELD_BLUE.setEditable(state);
        TEXT_FIELD_ALPHA.setEditable(state);
    }
    
    /**
     * Sets the color panel be responsive or not.
     * @param state True for color panel to be responsive, else false.
     */
    public void setEnabledColorPanel(boolean state)
    {
        TEXT_FIELD_RED.setEnabled(state);
        TEXT_FIELD_GREEN.setEnabled(state);
        TEXT_FIELD_BLUE.setEnabled(state);
        TEXT_FIELD_ALPHA.setEnabled(state);
        
        if(!state)
        { 
            TEXT_FIELD_RED.setText("N/A");
            TEXT_FIELD_GREEN.setText("N/A");
            TEXT_FIELD_BLUE.setText("N/A");
            TEXT_FIELD_ALPHA.setText("N/A");
        }
    }
    
    /**
     * Sets the coordinates panel to accept or ignore user input.
     * @param state True for the color panel to accept user input, else false.
     */
    public void setEditableCoordinatesPanel(boolean state)
    {
        TEXT_FIELD_X.setEditable(state);
        TEXT_FIELD_Y.setEditable(state);
    }
    
    /**
     * Set the coordinates panel to be responsive or not.
     * @param state True for the coordinates panel to be responsive, else false.
     */
    public void setEnabledCoordinatesPanel(boolean state)
    {
        TEXT_FIELD_X.setEnabled(state);
        TEXT_FIELD_Y.setEnabled(state);
        
        if(!state)
        {
            TEXT_FIELD_X.setText("N/A");
            TEXT_FIELD_Y.setText("N/A");
        }
    }
    
    /**
     * Set the joint panel to be responsive or not.
     * @param state True for the joint panel to be responsive, else false.
     */
    public void setEnabledJointPanel(boolean state)
    {
        DROP_DOWN_FINGER_PARTS.setEnabled(state);
        DROP_DOWN_HANDS.setEnabled(state);
        DROP_DOWN_HAND_ORIENTATION.setEnabled(state);
        DROP_DOWN_HAND_PARTS.setEnabled(state);
    }
    
    /**
     * Set the color range panel to be responsive or not.
     * @param state True for the color range panel to be responsive, else false.
     */
    public void setEnabledColorRangePanel(boolean state)
    {
        SLIDER_ALPHA.setEnabled(state);
        SLIDER_BLUE.setEnabled(state);
        SLIDER_GREEN.setEnabled(state);
        SLIDER_RED.setEnabled(state);
    }
}