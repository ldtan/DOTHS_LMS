/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;

/**
 *
 * @author Liel Tan
 */
public class Joint extends Point implements Serializable
{
    public final Color COLOR;
    public int redLimit;
    public int greenLimit;
    public int blueLimit;
    public int alphaLimit;
    
    public Joint()
    {
        this(Color.WHITE, new Point());
    }
    
    public Joint(Color color)
    {
        this(color, new Point());
    }
    
    public Joint(Color color, Point p)
    {
        alphaLimit = 0;
        blueLimit = 0;
        greenLimit = 0;
        redLimit = 0;
        COLOR = color;
        
        super.setLocation(p);
    }
    
    public Joint(Color color, int x, int y)
    {
        this(color, new Point(x, y));
    }
    
    public Joint(int red, int green, int blue, int x, int y)
    {
        this(new Color(red, green, blue), new Point(x, y));
    }
    
    public double percentageMatch(Joint j)
    {
        Color sc = COLOR;
        Color cc = j.COLOR;
        
        double ad = 1 - (Math.abs(sc.getAlpha() - cc.getAlpha()) / (alphaLimit + 1));
        double rd = 1 - (Math.abs(sc.getRed()- cc.getRed()) / (redLimit + 1));
        double gd = 1 - (Math.abs(sc.getGreen()- cc.getGreen()) / (greenLimit + 1));
        double bd = 1 - (Math.abs(sc.getBlue()- cc.getBlue()) / (blueLimit + 1));
        
        return((ad + rd + gd + bd) / 4);
    }
    
    public boolean equivalent(Joint j)
    {
        final Color OTHER = j.COLOR;
        
        return(Math.abs(COLOR.getRed() - OTHER.getRed()) <= redLimit
            && Math.abs(COLOR.getGreen() - OTHER.getGreen()) <= greenLimit
            && Math.abs(COLOR.getBlue() - OTHER.getBlue()) <= blueLimit
            && Math.abs(COLOR.getAlpha() - OTHER.getAlpha()) <= alphaLimit);
    }
    
    @Override
    public String toString()
    {
        return("joint=DOTHS_LMS.obj.Joint[r=" + COLOR.getRed()
                                      + ",g=" + COLOR.getGreen()
                                      + ",b=" + COLOR.getBlue()
                                      + ",x=" + x
                                      + ",y=" + y + "]");
    }
}