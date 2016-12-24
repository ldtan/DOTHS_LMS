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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SpringLayout;
import obj.SignLanguage;

/**
 * The class that build the tool bar component for the frame.
 * @author Liel Tan
 */
public final class ToolBar extends JPanel
{
    /**
     * Label for the color panel.
     */
    private final JLabel LABEL_COLOR;
    
    /**
     * Label for the coordinates panel.
     */
    private final JLabel LABEL_COORDINATES;
    
    /**
     * Label for the finger part drop-down component.
     */
    private final JLabel LABEL_FINGER_PARTS;
    
    /**
     * Label for the hand used drop-down component.
     */
    private final JLabel LABEL_HANDS;
    
    /**
     * Label for the orientation drop-down component.
     */
    private final JLabel LABEL_HAND_ORIENTATION;
    
    /**
     * Label for the hand part drop-down component.
     */
    private final JLabel LABEL_HAND_PARTS;
    
    /**
     * Label for the joint panel.
     */
    private final JLabel LABEL_JOINT;
    
    /**
     * Label for the output text area.
     */
    private final JLabel LABEL_OUTPUT;
    
    /**
     * Label for the process text area.
     */
    private final JLabel LABEL_PROCESS;
    
    /**
     * Label for the color range panel.
     */
    private final JLabel LABEL_COLOR_RANGE;
    
    /**
     * Label for the x-coordinate text field.
     */
    private final JLabel LABEL_X;
    
    /**
     * Label for the y-coordinate text field.
     */
    private final JLabel LABEL_Y;
    
    /**
     * The layout for the calibration panel.
     */
    private final SpringLayout LAYOUT_CALIBRATION;
    
    /**
     * The layout for the translation panel.
     */
    private final SpringLayout LAYOUT_TRANSLATOR;
    
    /**
     * The panel that contains the calibration setup.
     */
    private final JPanel PANEL_CALIBRATION;
    
    /**
     * The panel that contains the color setup.
     */
    private final JPanel PANEL_COLOR;
    
    /**
     * The panel that contains the coordinating setup.
     */
    private final JPanel PANEL_COORDINATES;
    
    /**
     * The panel that contains the dictionary setup.
     */
    private final JPanel PANEL_DICTIONARY;
    
    /**
     * The panel that contains the joint selection setup.
     */
    private final JPanel PANEL_JOINT;
    
    /**
     * The panel that contains the color range setup.
     */
    private final JPanel PANEL_COLOR_RANGE;
    
    /**
     * The panel that shows the minimum, median and maximum color.
     */
    private final JPanel PANEL_COLOR_VIEW;
    
    /**
     * The panel that contains the option for the output text area.
     */
    private final JPanel PANEL_TRANSLATE;
    
    /**
     * The panel that contains the process and output information for the translation.
     */
    private final JPanel PANEL_TRANSLATOR;
    
    /**
     * The scroll for the calibration panel.
     */
    private final JScrollPane SCROLL_CALIBRATION;
    
    /**
     * The scroll for the dictionary panel.
     */
    private final JScrollPane SCROLL_DICTIONARY;
    
    /**
     * The scroll for the output text area.
     */
    private final JScrollPane SCROLL_OUTPUT;
    
    /**
     * The scroll for the process text area.
     */
    private final JScrollPane SCROLL_PROCESS;
    
    /**
     * The scroll for the translator panel.
     */
    private final JScrollPane SCROLL_TRANSLATOR;
    
    /**
     * The tool bar component.
     */
    private final JToolBar TOOL_BAR_CONTENT;
    
    /**
     * The button for clearing the content in the output text area.
     */
    protected final JButton BUTTON_CLEAR_OUTPUT;
    
    /**
     * The button for clearing the content in the process text area.
     */
    protected final JButton BUTTON_CLEAR_PROCESS;
    
    /**
     * The button that shows the median color to be detected.
     */
    protected final JButton BUTTON_MEDIAN_COLOR;
    
    /**
     * The button that show the minimum color to be detected.
     */
    protected final JButton BUTTON_LEFT_COLOR;
    
    /**
     * The button that show the maximum color to be detected.
     */
    protected final JButton BUTTON_RIGHT_COLOR;
    
    /**
     * The button that enables the joint selection.
     */
    protected final JToggleButton BUTTON_SELECT_JOINT;
    
    /**
     * The button that triggers the gesture translation.
     */
    protected final JButton BUTTON_TRANSLATE;
    
    /**
     * The combo box that contain the selection of finger parts.
     */
    protected final JComboBox DROP_DOWN_FINGER_PARTS;
    
    /**
     * The combo box that contains the selection for the hand used.
     */
    protected final JComboBox DROP_DOWN_HANDS;
    
    /**
     * The combo box that contains the selection for the orientation.
     */
    protected final JComboBox DROP_DOWN_HAND_ORIENTATION;
    
    /**
     * The combo box that contains the selection for the hand parts.
     */
    protected final JComboBox DROP_DOWN_HAND_PARTS;
    
    /**
     * Label for the alpha slider.
     */
    protected final JLabel LABEL_ALPHA;
    
    /**
     * Label for the blue slider.
     */
    protected final JLabel LABEL_BLUE;
    
    /**
     * Label for the green slider.
     */
    protected final JLabel LABEL_GREEN;
    
    /**
     * Label for the red slider.
     */
    protected final JLabel LABEL_RED;
    
    /**
     * The slider for the alpha color range.
     */
    protected final JSlider SLIDER_ALPHA;
    
    /**
     * The slider for the blue color range.
     */
    protected final JSlider SLIDER_BLUE;
    
    /**
     * The slider for the green color range.
     */
    protected final JSlider SLIDER_GREEN;
    
    /**
     * The slider for the red color range.
     */
    protected final JSlider SLIDER_RED;
    
    /**
     * The tabbed pane that contains the translator, calibration and dictionary panel.
     */
    protected final JTabbedPane TABBED_PANE_CONTENT;
    
    /**
     * The text area that displays the output text.
     */
    protected final JTextArea TEXT_AREA_OUTPUT;
    
    /**
     * The input text area that displays the process text.
     */
    protected final JTextArea TEXT_AREA_PROCESS;
    
    /**
     * The text field holds the value for the alpha.
     */
    protected final JTextField TEXT_FIELD_ALPHA;
    
    /**
     * The text field holds the value for the blue.
     */
    protected final JTextField TEXT_FIELD_BLUE;
    
    /**
     * The text field holds the value for the green.
     */
    protected final JTextField TEXT_FIELD_GREEN;
    
    /**
     * The text field holds the value for the red.
     */
    protected final JTextField TEXT_FIELD_RED;
    
    /**
     * The text field that shows the x-coordinate of the specified target.
     */
    protected final JTextField TEXT_FIELD_X;
    
    /**
     * The text field that shows the y-coordinate of the specified target.
     */
    protected final JTextField TEXT_FIELD_Y;
    
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
        
        TEXT_AREA_PROCESS.setEditable(false);
        TEXT_AREA_OUTPUT.setEditable(false);
        
        SCROLL_PROCESS.setViewportView(TEXT_AREA_PROCESS);
        
        PANEL_TRANSLATE.add(BUTTON_CLEAR_OUTPUT);
        
        LAYOUT_TRANSLATOR.putConstraint(N, LABEL_PROCESS, 5, N, PANEL_TRANSLATOR);
        LAYOUT_TRANSLATOR.putConstraint(W, LABEL_PROCESS, 5, W, PANEL_TRANSLATOR);
        
        LAYOUT_TRANSLATOR.putConstraint(N, SCROLL_PROCESS, 5, S, LABEL_PROCESS);
        LAYOUT_TRANSLATOR.putConstraint(W, SCROLL_PROCESS, 5, W, PANEL_TRANSLATOR);
        LAYOUT_TRANSLATOR.putConstraint(E, SCROLL_PROCESS, -5, E, PANEL_TRANSLATOR);
        LAYOUT_TRANSLATOR.putConstraint(S, SCROLL_PROCESS, 150, N, SCROLL_PROCESS);
        
        LAYOUT_TRANSLATOR.putConstraint(N, BUTTON_CLEAR_PROCESS, 5, S, SCROLL_PROCESS);
        LAYOUT_TRANSLATOR.putConstraint(E, BUTTON_CLEAR_PROCESS, -5, E, SCROLL_PROCESS);
        
        LAYOUT_TRANSLATOR.putConstraint(N, LABEL_OUTPUT, 20, S, BUTTON_CLEAR_PROCESS);
        LAYOUT_TRANSLATOR.putConstraint(W, LABEL_OUTPUT, 5, W, PANEL_TRANSLATOR);
        
        LAYOUT_TRANSLATOR.putConstraint(N, SCROLL_OUTPUT, 5, S, LABEL_OUTPUT);
        LAYOUT_TRANSLATOR.putConstraint(W, SCROLL_OUTPUT, 5, W, PANEL_TRANSLATOR);
        LAYOUT_TRANSLATOR.putConstraint(E, SCROLL_OUTPUT, -5, E, PANEL_TRANSLATOR);
        LAYOUT_TRANSLATOR.putConstraint(S, SCROLL_OUTPUT, 150, N, SCROLL_OUTPUT);
        
        LAYOUT_TRANSLATOR.putConstraint(N, PANEL_TRANSLATE, 5, S, SCROLL_OUTPUT);
        LAYOUT_TRANSLATOR.putConstraint(E, PANEL_TRANSLATE, 0, E, SCROLL_OUTPUT);
        
        PANEL_TRANSLATOR.setLayout(LAYOUT_TRANSLATOR);
        PANEL_TRANSLATOR.add(LABEL_PROCESS);
        PANEL_TRANSLATOR.add(SCROLL_PROCESS);
        PANEL_TRANSLATOR.add(BUTTON_CLEAR_PROCESS);
        PANEL_TRANSLATOR.add(LABEL_OUTPUT);
        PANEL_TRANSLATOR.add(SCROLL_OUTPUT);
        PANEL_TRANSLATOR.add(PANEL_TRANSLATE);
        PANEL_TRANSLATOR.setPreferredSize(new Dimension(230, 440));
        
        DROP_DOWN_FINGER_PARTS.setEnabled(false);
        DROP_DOWN_HANDS.setEnabled(false);
        DROP_DOWN_HAND_ORIENTATION.setEnabled(false);
        DROP_DOWN_HAND_PARTS.setEnabled(false);
        SLIDER_ALPHA.setEnabled(false);
        SLIDER_BLUE.setEnabled(false);
        SLIDER_GREEN.setEnabled(false);
        SLIDER_RED.setEnabled(false);
        
        PANEL_JOINT.setLayout(new GridLayout(4, 2, 5, 5));
        PANEL_JOINT.add(LABEL_HANDS);
        PANEL_JOINT.add(DROP_DOWN_HANDS);
        PANEL_JOINT.add(LABEL_HAND_ORIENTATION);
        PANEL_JOINT.add(DROP_DOWN_HAND_ORIENTATION);
        PANEL_JOINT.add(LABEL_HAND_PARTS);
        PANEL_JOINT.add(DROP_DOWN_HAND_PARTS);
        PANEL_JOINT.add(LABEL_FINGER_PARTS);
        PANEL_JOINT.add(DROP_DOWN_FINGER_PARTS);
        
        TEXT_FIELD_X.setEditable(false);
        TEXT_FIELD_Y.setEditable(false);
        TEXT_FIELD_RED.setEditable(false);
        TEXT_FIELD_GREEN.setEditable(false);
        TEXT_FIELD_BLUE.setEditable(false);
        TEXT_FIELD_ALPHA.setEditable(false);
        
        PANEL_COORDINATES.setLayout(new GridLayout(2, 2, 5, 5));
        PANEL_COORDINATES.add(LABEL_X);
        PANEL_COORDINATES.add(TEXT_FIELD_X);
        PANEL_COORDINATES.add(LABEL_Y);
        PANEL_COORDINATES.add(TEXT_FIELD_Y);
        
        PANEL_COLOR.setLayout(new GridLayout(4, 2, 5, 5));
        PANEL_COLOR.add(new JLabel("Red:"));
        PANEL_COLOR.add(TEXT_FIELD_RED);
        PANEL_COLOR.add(new JLabel("Green:"));
        PANEL_COLOR.add(TEXT_FIELD_GREEN);
        PANEL_COLOR.add(new JLabel("Blue:"));
        PANEL_COLOR.add(TEXT_FIELD_BLUE);
        PANEL_COLOR.add(new JLabel("Alpha:"));
        PANEL_COLOR.add(TEXT_FIELD_ALPHA);
        
        SLIDER_ALPHA.setMajorTickSpacing(5);
        SLIDER_ALPHA.setMinorTickSpacing(1);
        SLIDER_BLUE.setMajorTickSpacing(5);
        SLIDER_BLUE.setMinorTickSpacing(1);
        SLIDER_GREEN.setMajorTickSpacing(5);
        SLIDER_GREEN.setMinorTickSpacing(1);
        SLIDER_RED.setMajorTickSpacing(5);
        SLIDER_RED.setMinorTickSpacing(1);
        
        PANEL_COLOR_RANGE.setLayout(new GridLayout(6, 2, 5, 5));
        PANEL_COLOR_RANGE.add(LABEL_RED);
        PANEL_COLOR_RANGE.add(SLIDER_RED);
        PANEL_COLOR_RANGE.add(LABEL_GREEN);
        PANEL_COLOR_RANGE.add(SLIDER_GREEN);
        PANEL_COLOR_RANGE.add(LABEL_BLUE);
        PANEL_COLOR_RANGE.add(SLIDER_BLUE);
        PANEL_COLOR_RANGE.add(LABEL_ALPHA);
        PANEL_COLOR_RANGE.add(SLIDER_ALPHA);
        
        PANEL_COLOR_VIEW.setLayout(new GridLayout(1, 3, 20, 0));
        PANEL_COLOR_VIEW.add(BUTTON_LEFT_COLOR);
        PANEL_COLOR_VIEW.add(BUTTON_MEDIAN_COLOR);
        PANEL_COLOR_VIEW.add(BUTTON_RIGHT_COLOR);
        
        LAYOUT_CALIBRATION.putConstraint(N, LABEL_JOINT, 5, N, PANEL_CALIBRATION);
        LAYOUT_CALIBRATION.putConstraint(W, LABEL_JOINT, 5, W, PANEL_CALIBRATION);
        
        LAYOUT_CALIBRATION.putConstraint(N, PANEL_JOINT, 10, S, LABEL_JOINT);
        LAYOUT_CALIBRATION.putConstraint(E, PANEL_JOINT, -5, E, PANEL_CALIBRATION);
        LAYOUT_CALIBRATION.putConstraint(W, PANEL_JOINT, 5, W, PANEL_CALIBRATION);
        
        LAYOUT_CALIBRATION.putConstraint(N, BUTTON_SELECT_JOINT, 10, S, PANEL_JOINT);
        LAYOUT_CALIBRATION.putConstraint(HC, BUTTON_SELECT_JOINT, 0, HC, PANEL_CALIBRATION);
        
        LAYOUT_CALIBRATION.putConstraint(N, LABEL_COORDINATES, 20, S, BUTTON_SELECT_JOINT);
        LAYOUT_CALIBRATION.putConstraint(W, LABEL_COORDINATES, 5, W, PANEL_CALIBRATION);
        
        LAYOUT_CALIBRATION.putConstraint(N, PANEL_COORDINATES, 10, S, LABEL_COORDINATES);
        LAYOUT_CALIBRATION.putConstraint(E, PANEL_COORDINATES, -5, E, PANEL_CALIBRATION);
        LAYOUT_CALIBRATION.putConstraint(W, PANEL_COORDINATES, 5, W, PANEL_CALIBRATION);
        
        LAYOUT_CALIBRATION.putConstraint(N, LABEL_COLOR, 20, S, PANEL_COORDINATES);
        LAYOUT_CALIBRATION.putConstraint(W, LABEL_COLOR, 5, W, PANEL_CALIBRATION);
        
        LAYOUT_CALIBRATION.putConstraint(N, PANEL_COLOR, 10, S, LABEL_COLOR);
        LAYOUT_CALIBRATION.putConstraint(E, PANEL_COLOR, -5, E, PANEL_CALIBRATION);
        LAYOUT_CALIBRATION.putConstraint(W, PANEL_COLOR, 5, W, PANEL_CALIBRATION);
        
        LAYOUT_CALIBRATION.putConstraint(N, LABEL_COLOR_RANGE, 20, S, PANEL_COLOR);
        LAYOUT_CALIBRATION.putConstraint(W, LABEL_COLOR_RANGE, 5, W, PANEL_CALIBRATION);
        
        LAYOUT_CALIBRATION.putConstraint(N, PANEL_COLOR_VIEW, 10, S, LABEL_COLOR_RANGE);
        LAYOUT_CALIBRATION.putConstraint(S, PANEL_COLOR_VIEW, 20, N, PANEL_COLOR_VIEW);
        LAYOUT_CALIBRATION.putConstraint(HC, PANEL_COLOR_VIEW, 0, HC, PANEL_CALIBRATION);
        
        LAYOUT_CALIBRATION.putConstraint(N, PANEL_COLOR_RANGE, 10, S, PANEL_COLOR_VIEW);
        LAYOUT_CALIBRATION.putConstraint(E, PANEL_COLOR_RANGE, -5, E, PANEL_CALIBRATION);
        LAYOUT_CALIBRATION.putConstraint(W, PANEL_COLOR_RANGE, 5, W, PANEL_CALIBRATION);
        
        PANEL_CALIBRATION.setLayout(LAYOUT_CALIBRATION);
        PANEL_CALIBRATION.add(LABEL_JOINT);
        PANEL_CALIBRATION.add(PANEL_JOINT);
        PANEL_CALIBRATION.add(BUTTON_SELECT_JOINT);
        PANEL_CALIBRATION.add(LABEL_COORDINATES);
        PANEL_CALIBRATION.add(PANEL_COORDINATES);
        PANEL_CALIBRATION.add(LABEL_COLOR);
        PANEL_CALIBRATION.add(PANEL_COLOR);
        PANEL_CALIBRATION.add(LABEL_COLOR_RANGE);
        PANEL_CALIBRATION.add(PANEL_COLOR_VIEW);
        PANEL_CALIBRATION.add(PANEL_COLOR_RANGE);
        PANEL_CALIBRATION.setPreferredSize(new Dimension(230, 575));
        
        SCROLL_TRANSLATOR.setViewportView(PANEL_TRANSLATOR);
        SCROLL_CALIBRATION.setViewportView(PANEL_CALIBRATION);
        SCROLL_DICTIONARY.setViewportView(PANEL_DICTIONARY);
        
        TABBED_PANE_CONTENT.addTab("Translator", SCROLL_TRANSLATOR);
        TABBED_PANE_CONTENT.addTab("Calibration", SCROLL_CALIBRATION);
        TABBED_PANE_CONTENT.addTab("Dictionary", SCROLL_DICTIONARY);
        
        TOOL_BAR_CONTENT.add(TABBED_PANE_CONTENT);
        
        setLayout(new GridLayout(1, 1));
        add(TOOL_BAR_CONTENT);
    }
    
    /**
     * The constructor for this class.
     */
    public ToolBar()
    {
        LABEL_ALPHA = new JLabel("Alpha:");
        LABEL_BLUE = new JLabel("Blue:");
        LABEL_COLOR = new JLabel("COLOR");
        LABEL_COORDINATES = new JLabel("COORDINATES");
        LABEL_FINGER_PARTS = new JLabel("Division:");
        LABEL_GREEN = new JLabel("Green:");
        LABEL_HANDS = new JLabel("Hand:");
        LABEL_HAND_ORIENTATION = new JLabel("Orientation:");
        LABEL_HAND_PARTS = new JLabel("Part:");
        LABEL_JOINT = new JLabel("JOINT");
        LABEL_OUTPUT = new JLabel("Output");
        LABEL_PROCESS = new JLabel("Process");
        LABEL_RED = new JLabel("Red:");
        LABEL_COLOR_RANGE = new JLabel("COLOR RANGE");
        LABEL_X = new JLabel("X:");
        LABEL_Y = new JLabel("Y:");
        LAYOUT_CALIBRATION = new SpringLayout();
        LAYOUT_TRANSLATOR = new SpringLayout();
        PANEL_CALIBRATION = new JPanel();
        PANEL_COLOR = new JPanel();
        PANEL_COORDINATES = new JPanel();
        PANEL_DICTIONARY = new JPanel();
        PANEL_JOINT = new JPanel();
        PANEL_COLOR_RANGE = new JPanel();
        PANEL_COLOR_VIEW = new JPanel();
        PANEL_TRANSLATE = new JPanel();
        PANEL_TRANSLATOR = new JPanel();
        SCROLL_CALIBRATION = new JScrollPane();
        SCROLL_DICTIONARY = new JScrollPane();
        SCROLL_OUTPUT = new JScrollPane();
        SCROLL_PROCESS = new JScrollPane();
        SCROLL_TRANSLATOR = new JScrollPane();
        TABBED_PANE_CONTENT = new JTabbedPane();
        BUTTON_CLEAR_OUTPUT = new JButton("CLEAR");
        BUTTON_CLEAR_PROCESS = new JButton("CLEAR");
        BUTTON_MEDIAN_COLOR = new JButton();
        BUTTON_LEFT_COLOR = new JButton();
        BUTTON_RIGHT_COLOR = new JButton();
        BUTTON_SELECT_JOINT = new JToggleButton("SELECT JOINT");
        BUTTON_TRANSLATE = new JButton("TRANSLATE");
        DROP_DOWN_FINGER_PARTS = new JComboBox(SignLanguage.ARRAY_FINGER_PARTS);
        DROP_DOWN_HANDS = new JComboBox(SignLanguage.ARRAY_HANDS);
        DROP_DOWN_HAND_ORIENTATION = new JComboBox(SignLanguage.ARRAY_HAND_ORIENTATION);
        DROP_DOWN_HAND_PARTS = new JComboBox(SignLanguage.ARRAY_HAND_PARTS);
        SLIDER_ALPHA = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        SLIDER_BLUE = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        SLIDER_GREEN = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        SLIDER_RED = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        TEXT_AREA_OUTPUT = new JTextArea();
        TEXT_AREA_PROCESS = new JTextArea();
        TEXT_FIELD_ALPHA = new JTextField();
        TEXT_FIELD_BLUE = new JTextField();
        TEXT_FIELD_GREEN = new JTextField();
        TEXT_FIELD_RED = new JTextField();
        TEXT_FIELD_X = new JTextField();
        TEXT_FIELD_Y = new JTextField();
        TOOL_BAR_CONTENT = new JToolBar();
        
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