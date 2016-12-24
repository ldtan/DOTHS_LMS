/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Objects;

/**
 *
 * @author Liel Tan
 */
public class Skeleton
{
    private final List<Joint> JOINTS;
    public double acceptanceRadius;
    
    public Skeleton()
    {
        JOINTS = new ArrayList();
        acceptanceRadius = 0;
    }
    
    public void addJoint(Joint j)
    {
        JOINTS.add(j);
    }
    
    public void clear()
    {
        JOINTS.clear();
    }
    
    public boolean containsJoint(Joint j)
    {
        return(JOINTS.contains(j));
    }
    
    public Rectangle getBounds()
    {
        if(JOINTS.isEmpty())
        {
            return(new Rectangle());
        }
        
        Integer minX = null;
        Integer minY = null;
        Integer maxX = null;
        Integer maxY = null;
        
        for(ListIterator<Joint> i = JOINTS.listIterator(); i.hasNext();)
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
    
    public Joint getJoint(int i)
    {
        return(JOINTS.get(i));
    }
    
    public List<Joint> getJoints()
    {
        return(JOINTS);
    }
    
    public List<Joint> getJoints(Color c)
    {
        List<Joint> joints = new ArrayList();
        
        JOINTS.stream().filter((j) -> (j.COLOR == c)).forEach((j) -> {
            joints.add(j);
        });
        
        return(joints);
    }
    
    public boolean isEmpty()
    {
        return(JOINTS.isEmpty());
    }
    
    public int jointCount()
    {
        return(JOINTS.size());
    }
    
    public double percentageMatch(Skeleton s)
    {
        if(JOINTS.isEmpty() && s.isEmpty())
        {
            return(1);
        }
        
        if(JOINTS.isEmpty() || s.isEmpty())
        {
            return(0);
        }
        
        double percentage = 0;
        
        s.setBounds(getBounds());
        
        for(Joint tj : JOINTS)
        {
            for(Joint sj : s.getJoints())
            {
                double j_percentage = tj.percentageMatch(sj);
                
                if(tj.distance(sj) <= acceptanceRadius && j_percentage > 0)
                {
                    percentage += j_percentage;
                }
            }
        }
        
        return(percentage / Math.max(JOINTS.size(), s.jointCount()));
    }
    
    public void setBounds(Rectangle r)
    {
        if(!isEmpty())
        {
            List<Joint> joints = new ArrayList();
            Rectangle sr = getBounds();
            int xd = r.x - sr.x;
            int yd = r.y - sr.y;
            double wr = r.getWidth() / sr.getWidth();
            double hr = r.height / sr.getHeight();

            for(Joint j : JOINTS)
            {
                j.setLocation((j.x - xd) * wr, (j.y - yd) * hr);
                joints.add(j);
            }

            JOINTS.clear();
            JOINTS.addAll(joints);
        }
    }
    
    public void removeJoint(int i)
    {
        JOINTS.remove(i);
    }
    
    public void removeJoints(Color c)
    {
        for(ListIterator<Joint> i = JOINTS.listIterator(); i.hasNext();)
        {
            if(i.next().COLOR == c)
            {
                i.remove();
            }
        }
    }
}