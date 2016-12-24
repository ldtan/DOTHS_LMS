/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import java.awt.Component;
import javafx.geometry.Rectangle2D;

/**
 *
 * @author Liel Tan
 */
public class MotionEvent
{
    private final Component EVENT_PARENT;
    private final Rectangle2D AREA;
    
    public MotionEvent(Component eventSource, Rectangle2D area)
    {
        EVENT_PARENT = eventSource;
        AREA = area;
    }
    
    public Component getParentComponent()
    {
        return(EVENT_PARENT);
    }
    
    public Rectangle2D getSourceArea()
    {
        return(AREA);
    }
}
