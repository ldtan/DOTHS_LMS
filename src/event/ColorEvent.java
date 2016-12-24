/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;

/**
 *
 * @author Liel Tan
 */
public class ColorEvent
{
    public final Component EVENT_PARENT;
    public final Rectangle AREA;
    
    public ColorEvent(Component eventSource, Rectangle area)
    {
        EVENT_PARENT = eventSource;
        AREA = area;
    }
}