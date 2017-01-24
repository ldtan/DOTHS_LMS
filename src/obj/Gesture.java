/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Liel Tan
 */
public class Gesture implements Serializable
{
    private final List<Skeleton> LIST_SKELETON;
    public double acceptanceRadius;
    
    public Gesture()
    {
        LIST_SKELETON = new ArrayList();
        acceptanceRadius = 0;
    }
    
    public boolean isEmpty()
    {
        return(LIST_SKELETON.isEmpty());
    }
    
    public int skeletonCount()
    {
        return(LIST_SKELETON.size());
    }
    
    public boolean addSkeleton(Skeleton skeleton)
    {
        if(LIST_SKELETON.contains(skeleton))
        {
            return(false);
        }
        
        LIST_SKELETON.add(skeleton);
        return(true);
    }
    
    public boolean containsSkeleton(Skeleton skeleton)
    {
        return(LIST_SKELETON.contains(skeleton));
    }
    
    public Skeleton getSkeleton(int index)
    {
        return(LIST_SKELETON.get(index));
    }
    
    public List<Skeleton> getSkeletons()
    {
        return(LIST_SKELETON);
    }
    
    public boolean removeSkeleton(int index)
    {
        try
        {
            LIST_SKELETON.remove(index);
            return(true);
        }
        
        catch(IndexOutOfBoundsException ex)
        {
            return(false);
        }
    }
    
    public void clear()
    {
        LIST_SKELETON.clear();
    }
    
    public Point getLocation()
    {
        return(getBounds().getLocation());
    }
    
    public void setLocation(Point location)
    {
        if(!LIST_SKELETON.isEmpty())
        {
            List<Skeleton> list_skeleton = new ArrayList();
            Point p1 = getLocation();
            int xd = Math.abs(location.x - p1.x);
            int yd = Math.abs(location.y - p1.y);
            
            for(Skeleton s : LIST_SKELETON)
            {
                Point p2 = s.getLocation();
                
                s.setLocation(new Point(Math.abs(p2.x - xd), Math.abs(p2.y - yd)));
                list_skeleton.add(s);
            }
            
            LIST_SKELETON.clear();
            LIST_SKELETON.addAll(list_skeleton);
        }
    }
    
    public Dimension getSize()
    {
        return(getBounds().getSize());
    }
    
    public void setSize(Dimension size)
    {
        if(!LIST_SKELETON.isEmpty())
        {
            List<Skeleton> list_skeleton = new ArrayList();
            Dimension s1 = getSize();
            double wr = size.width / s1.width;
            double hr = size.height / s1.height;
            
            for(Skeleton s : LIST_SKELETON)
            {
                Dimension s2 = s.getSize();
                double nw = s2.width * wr;
                double nh = s2.height * hr;
                
                s.setSize(new Dimension((int)nw, (int)nh));
                list_skeleton.add(s);
            }
            
            LIST_SKELETON.clear();
            LIST_SKELETON.addAll(list_skeleton);
        }
    }
    
    public Rectangle getBounds()
    {
        if(LIST_SKELETON.isEmpty())
        {
            return(new Rectangle());
        }
        
        Integer minX = null;
        Integer minY = null;
        Integer maxX = null;
        Integer maxY = null;
        
        for(Skeleton s : LIST_SKELETON)
        {
            for(Joint j : s.getJoints())
            {
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
        }
        
        maxX = Objects.equals(minX, maxX) ? minX + 1 : maxX;
        maxY = Objects.equals(minY, maxY) ? maxY + 1 : maxY;
        
        return(new Rectangle(minX, minY, (maxX - minX), (maxY - minY)));
    }
    
    public void setBounds(Rectangle area)
    {
        if(!LIST_SKELETON.isEmpty())
        {
            setSize(area.getSize());
            setLocation(area.getLocation());
        }
    }
    
    public double percentageMatch(Gesture gesture)
    {
        if(LIST_SKELETON.isEmpty() || gesture.isEmpty())
        {
            return(0);
        }
        
        double percentage = 0;
        
        for(Skeleton s1 : LIST_SKELETON)
        {
            double s_percentageMatch = 0;
            
            for(Skeleton s2 : gesture.LIST_SKELETON)
            {
                double percentageMatch = s1.percentageMatch(s2);
                s_percentageMatch = percentageMatch > s_percentageMatch
                                  ? percentageMatch : s_percentageMatch;
            }
            
            percentage += s_percentageMatch;
        }
        System.out.println("Percentage: " + (percentage / (double)LIST_SKELETON.size()));
        return(percentage / (double)LIST_SKELETON.size());
    }
}