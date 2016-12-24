/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import java.awt.Dimension;

/**
 *
 * @author Liel Tan
 */
public interface MotionListener
{
    public abstract Dimension getMinSize();
    public abstract Dimension getMaxSize();
    public abstract void motionOccured(MotionEvent e);
}