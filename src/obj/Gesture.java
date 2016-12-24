/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj;

import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Liel Tan
 */
public class Gesture
{
    private final Map<Long, Skeleton> SKELETONS;
    public double acceptanceRadius;
    
    public Gesture()
    {
        SKELETONS = new HashMap();
        acceptanceRadius = 0;
    }
    
    public boolean addSkeleton(long t, Skeleton s)
    {
        if(SKELETONS.containsKey(t))
        {
            return(false);
        }
        
        SKELETONS.put(t, s);
        return(true);
    }
    
    public void clear()
    {
        SKELETONS.clear();
    }
    
    public boolean containsTimeInterval(long t)
    {
        return(SKELETONS.containsKey(t));
    }
    
    public boolean containsSkeleton(Skeleton s)
    {
        return(SKELETONS.containsValue(s));
    }
    
    public Rectangle getBounds()
    {
        if(SKELETONS.isEmpty())
        {
            return(new Rectangle());
        }
        
        Integer minX = null;
        Integer minY = null;
        Integer maxX = null;
        Integer maxY = null;
        
        for(long t : SKELETONS.keySet())
        {
            Skeleton s = SKELETONS.get(t);
            
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
    
    public Skeleton getSkeleton(long t)
    {
        return(SKELETONS.get(t));
    }
    
    public Set<Long> getTimeIntervals()
    {
        return(SKELETONS.keySet());
    }
    
    public boolean isEmpty()
    {
        return(SKELETONS.isEmpty());
    }
    
    public double percentageMatch(Gesture g)
    {
        if(SKELETONS.isEmpty() && g.isEmpty())
        {
            return(1);
        }
        
        if(SKELETONS.isEmpty() || g.isEmpty())
        {
            return(0);
        }
        
        double percentage = 0;
        int matchCount = 0;
        
        g.setBounds(getBounds());
        
        for(long tt : getTimeIntervals())
        {
            Skeleton ts = getSkeleton(tt);
            Rectangle tr = ts.getBounds();
            
            for(long st : g.getTimeIntervals())
            {
                Skeleton ss = g.getSkeleton(st);
                Rectangle sr = ss.getBounds();
                double s_percentage = ts.percentageMatch(ss);
                
                if(new Point2D.Double(tr.getCenterX(), tr.getCenterY()).distance(new Point2D.Double(sr.getCenterX(), sr.getCenterY())) <= acceptanceRadius && s_percentage > 0)
                {
                    percentage += s_percentage;
                    ++matchCount;
                }
            }
        }
        
        return(percentage / Math.max(SKELETONS.size(), g.skeletonCount()));
    }
    
    public boolean removeSkeleton(long t)
    {
        return(SKELETONS.remove(t) != null);
    }
    
    public void setBounds(Rectangle r)
    {
        if(!SKELETONS.isEmpty())
        {
            Map<Long, Skeleton> skeletons = new HashMap();
            Rectangle sr = getBounds();
            int xd = r.x - sr.x;
            int yd = r.y - sr.y;
            double wr = r.getWidth() / sr.getWidth();
            double hr = r.getHeight() / sr.getHeight();

            for(long t : getTimeIntervals())
            {
                Skeleton s = new Skeleton();

                for(Joint j : getSkeleton(t).getJoints())
                {
                    j.setLocation((j.x - xd) * wr, (j.y - yd) * hr);
                    s.addJoint(j);
                }

                skeletons.put(t, s);
            }

            SKELETONS.clear();
            SKELETONS.putAll(skeletons);
        }
    }
    
    public int skeletonCount()
    {
        return(SKELETONS.size());
    }
}