/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj;

import java.awt.Color;
import java.awt.Point;

/**
 *
 * @author Liel Tan
 */
public class Joint extends Point
{
    public final Color COLOR;
    public double alphaLimit;
    public double blueLimit;
    public double greenLimit;
    public int id;
    public double redLimit;
    
    public Joint()
    {
        this(Color.WHITE, new Point());
    }
    
    public Joint(Color c)
    {
        this(c, new Point());
    }
    
    public Joint(Color c, Point p)
    {
        alphaLimit = 0;
        blueLimit = 0;
        greenLimit = 0;
        redLimit = 0;
        COLOR = c;
        
        super.setLocation(p);
    }
    
    public Joint(Color c, int x, int y)
    {
        this(c, new Point(x, y));
    }
    
    public Joint(int r, int g, int b, int x, int y)
    {
        this(new Color(r, g, b), new Point(x, y));
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
    
    @Override
    public String toString()
    {
        return("joint=DOTHS_LMS.obj.Joint[rgb=" + COLOR.getRGB() + ",x=" + x + ",y=" + y + "]");
    }
}