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
    public abstract void colorDetected(ColorEvent event);
    public abstract double getAlphaLimit();
    public abstract double getBlueLimit();
    public abstract double getGreenLimit();
    public abstract Dimension getMaxArea();
    public abstract Dimension getMinArea();
    public abstract double getRedLimit();
    public abstract Color getTargetColor();
}