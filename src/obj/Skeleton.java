/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Objects;

/**
 *
 * @author Liel Tan
 */
public class Skeleton implements Serializable
{
    private final List<Joint> LIST_JOINT;
    public double acceptanceRadius;
    
    public Skeleton()
    {
        LIST_JOINT = new ArrayList();
        acceptanceRadius = 0;
    }
    
    public boolean isEmpty()
    {
        return(LIST_JOINT.isEmpty());
    }
    
    public int jointCount()
    {
        return(LIST_JOINT.size());
    }
    
    public void addJoint(Joint joint)
    {
        LIST_JOINT.add(joint);
    }
    
    public boolean containsJoint(Joint j)
    {
        return(LIST_JOINT.contains(j));
    }
    
    public Joint getJoint(int index)
    {
        return(LIST_JOINT.get(index));
    }
    
    public List<Joint> getJoints()
    {
        return(LIST_JOINT);
    }
    
    public List<Joint> getJoints(Color color)
    {
        List<Joint> joints = new ArrayList();
        
        LIST_JOINT.stream().filter((j) -> (j.COLOR == color)).forEach((j) ->
        {
            joints.add(j);
        });
        
        return(joints);
    }
    
    public void removeJoint(int index)
    {
        LIST_JOINT.remove(index);
    }
    
    public void removeJoints(Color color)
    {
        for(ListIterator<Joint> i = LIST_JOINT.listIterator(); i.hasNext();)
        {
            if(i.next().COLOR == color)
            {
                i.remove();
            }
        }
    }
    
    public void clear()
    {
        LIST_JOINT.clear();
    }
    
    public Point getLocation()
    {
        return(getBounds().getLocation());
    }
    
    public Point getCenterLocation()
    {
        Rectangle area = getBounds();
        return(new Point((int)area.getCenterX(), (int)area.getCenterY()));
    }
    
    public void setLocation(Point location)
    {
        if(!LIST_JOINT.isEmpty())
        {
            List<Joint> list_joint = new ArrayList();
            Point p = getLocation();
            int xd = Math.abs(location.x - p.x);
            int yd = Math.abs(location.y - p.y);
            
            for(Joint j : LIST_JOINT)
            {
                j.x = Math.abs(j.x - xd);
                j.y = Math.abs(j.y - yd);
                
                list_joint.add(j);
            }
            
            LIST_JOINT.clear();
            LIST_JOINT.addAll(list_joint);
        }
    }
    
    public Dimension getSize()
    {
        return(getBounds().getSize());
    }
    
    public void setSize(Dimension size)
    {
        if(!LIST_JOINT.isEmpty())
        {
            List<Joint> list_joint = new ArrayList();
            Rectangle s = getBounds();
            int xd = size.width - s.width;
            int yd = size.height - s.height;
            int xc = (int)s.getCenterX();
            int yc = (int)s.getCenterY();
            
            for(Joint j : LIST_JOINT)
            {
                j.x = j.x < xc ? j.x - xd
                    : j.x > xc ? j.x + xd : j.x;
                
                j.y = j.y < yc ? j.y - (yd / 2)
                    : j.y > yc ? j.y + (yd / 2) : j.y;
                
                list_joint.add(j);
            }
            
            LIST_JOINT.clear();
            LIST_JOINT.addAll(list_joint);
        }
    }
    
    public Rectangle getBounds()
    {
        if(LIST_JOINT.isEmpty())
        {
            return(new Rectangle());
        }
        
        Integer minX = null;
        Integer minY = null;
        Integer maxX = null;
        Integer maxY = null;
        
        for(ListIterator<Joint> i = LIST_JOINT.listIterator(); i.hasNext();)
        {
            Joint j = i.next();
            
            if(j == null)
            {
                continue;
            }
            
            if(minX == null)
            {
                minX = j.x;
                minY = j.y;
                maxX = j.x;
                maxY = j.y;

                continue;
            }
            
            minX = j.x < minX ? j.x : minX;
            minY = j.y < minY ? j.y : minY;
            maxX = j.x > maxX ? j.x : maxX;
            maxY = j.y > maxY ? j.y : maxY;
        }
        
        maxX = Objects.equals(minX, maxX) ? minX + 1 : maxX;
        maxY = Objects.equals(minY, maxY) ? minY + 1 : maxY;
        
        return(new Rectangle(minX, minY, (maxX - minX), (maxY - minY)));
    }
    
    public void setBounds(Rectangle area)
    {
        if(!isEmpty())
        {
            setSize(area.getSize());
            setLocation(area.getLocation());
        }
    }
    
    public double percentageMatch(Skeleton skeleton)
    {
        if(LIST_JOINT.isEmpty() || skeleton.isEmpty())
        {
            return(0);
        }
        
        Rectangle r1 = getBounds();
        Rectangle r2 = skeleton.getBounds();
        Point p1 = new Point((int)r1.getCenterX(), (int)r1.getCenterY());
        Point p2 = new Point((int)r2.getCenterX(), (int)r2.getCenterY());
        double percentage = 0;
        
        System.out.println("S1 Center: " + p1);
        System.out.println("S2 Center: " + p2);
        
        for(Joint j1 : LIST_JOINT)
        {
            double j_percentageMatch = 0;
            double wdp1 = (double)Math.abs(j1.x - p1.x) / (double)r1.width;
            double hdp1 = (double)Math.abs(j1.y - p1.y) / (double)r1.height;
            
            for(Joint j2 : skeleton.LIST_JOINT)
            {
                if(!j1.equivalent(j2))
                {
                    continue;
                }
                
                double wdp2 = (double)Math.abs(j2.x - p2.x) / (double)r2.width;
                double hdp2 = (double)Math.abs(j2.y - p2.y) / (double)r2.height;
                double percentageMatch = j1.percentageMatch(j2);
                
                if(Math.abs(wdp1 - wdp2) <= 0.1
                && Math.abs(hdp1 - hdp2) <= 0.1)
                {
                    j_percentageMatch = percentageMatch > j_percentageMatch
                                      ? percentageMatch : j_percentageMatch;
                    
                    System.out.println("Width Diff 1: " + wdp1);
                    System.out.println("Height Diff 1: " + hdp1);
                    System.out.println("Width Diff 2: " + wdp2);
                    System.out.println("Height Diff 2: " + hdp2);
                    System.out.println("Joint Percentage: " + percentageMatch);
                }
            }
            
            percentage += j_percentageMatch;
        }
        System.out.println("Percentage: " + (percentage / (double)LIST_JOINT.size()));
        return(percentage / (double)LIST_JOINT.size());
    }
}