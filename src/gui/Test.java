/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import obj.Gesture;
import obj.Joint;
import obj.SignLanguage;
import obj.Skeleton;
import sys.JavaFH;

/**
 *
 * @author LIKE_MIANN
 */
public class Test extends JFrame
{
    private final List<JButton> LIST_JBUTTON_JOINT;
    private Skeleton skeleton;
    
    private void setupGUI()
    {
        setLayout(null);
        
        JButton jButton_bounds = new JButton();
        
        jButton_bounds.setBounds(skeleton.getBounds());
        jButton_bounds.setContentAreaFilled(false);
        
        for(Joint j : skeleton.getJoints())
        {
            JButton jButton_j = new JButton();
            
            jButton_j.setSize(20, 20);
            jButton_j.setLocation(j.x - 10, j.y - 10);
            jButton_j.setBackground(j.COLOR);
            
            add(jButton_j);
        }
        
        add(jButton_bounds);
    }
    
    private void loadGUI()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(500, 500));
        this.setVisible(true);
    }
    
    
    public Test(Skeleton s)
    {
        LIST_JBUTTON_JOINT = new ArrayList();
        skeleton = s;
        
        setupGUI();
    }
    
    public static void main(String[] args)
    {
        JavaFH<SignLanguage> fh = new JavaFH("dictionary/", ".slg");
        Gesture gesture1 = fh.getObject("101").getGesture();
        Gesture gesture2 = fh.getObject("102").getGesture();
        Skeleton skeleton1 = gesture1.getSkeleton(0);
        Skeleton skeleton2 = gesture2.getSkeleton(0);
        
        System.out.println("Gesture Match: " + gesture1.percentageMatch(gesture2));
        System.out.println("Skeleton Match: " + skeleton1.percentageMatch(skeleton2));
        skeleton1.setLocation(new Point(10, 10));
        System.out.println(skeleton1.getSize());
        
        Test test = new Test(skeleton1);
        
        test.loadGUI();
    }
}
