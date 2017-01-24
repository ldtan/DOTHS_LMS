/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import java.awt.Color;
import java.awt.Dimension;

/**
 *
 * @author Liel Tan
 */
public interface ColorListener
{
    public abstract Color getTargetColor();
    public abstract double getRedLimit();
    public abstract double getGreenLimit();
    public abstract double getBlueLimit();
    public abstract double getAlphaLimit();
    public abstract Dimension getMinArea();
    public abstract Dimension getMaxArea();
    public abstract void colorDetected(ColorEvent event);
}