/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import event.ColorEvent;
import event.ColorListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import marvin.MarvinPluginCollection;
import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;

/**
 *
 * @author Liel Tan
 */
public class ImageBox extends ImageScannerPanel
{
    private final List<MarvinImage> IMAGE_LIST;
    private final ComponentAdapter COMPONENT_LISTENER;
    private MarvinImage imageOut;
    private int imageIndex;
    private boolean slide;
    private long slidePause;
    public static final int NORMAL_MODE = 0;
    public static final int SLIDE_MODE = 1;
    
    public ImageBox()
    {
        IMAGE_LIST = new ArrayList();
        
        COMPONENT_LISTENER = new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                if(imageCount() > 0)
                {
                    setImage(imageIndex);
                }
            }
        };
        
        imageIndex = 0;
        slidePause = 1000;
        slide = false;
        
        super.addComponentListener(COMPONENT_LISTENER);
    }
    
    public void addImage(MarvinImage image)
    {
        IMAGE_LIST.add(image);
        setImage(imageCount() - 1);
    }
    
    public void clear()
    {
        IMAGE_LIST.clear();
    }
    
    public int getImageIndex()
    {
        return(imageIndex);
    }
    
    public MarvinImage getOriginalImage()
    {
        return(IMAGE_LIST.get(imageIndex));
    }
    
    public long getSlideSpeed()
    {
        return(slidePause);
    }
    
    public boolean hasNext()
    {
        return(imageIndex + 1 < imageCount());
    }
    
    public boolean hasPrevious()
    {
        return(imageIndex - 1 >= 0);
    }
    
    public int imageCount()
    {
        return(IMAGE_LIST.size());
    }
    
    public void nextImage()
    {
        setImage(hasNext() ? imageIndex + 1 : 0);
    }
    
    public void setSlidePause(long ms)
    {
        
    }
    
    @Override
    public void setImage(MarvinImage image)
    {
        IMAGE_LIST.clear();
        addImage(image);
    }
    
    public void setImage(int i)
    {
        MarvinImage imageIn = getOriginalImage();
        imageOut = imageIn.clone();
        imageIndex = i >= 0 && i < imageCount() ? i : 0;
        int width = getWidth();
        int height = getHeight();
        
        MarvinPluginCollection.scale(imageIn, imageOut, width > 0 ? width : 1, height > 0 ? height : 1);
        imageOut.update();
        
        super.setImage(imageOut);
        reload();
    }
    
    public void setImageMode(int mode)
    {
        switch(mode)
        {
            case 0:
                
                slide = false;
                break;
                
            case 1:
                
                slide = true;
                
                new Thread()
                {
                    @Override
                    public void run()
                    {
                        while(slide)
                        {
                            try
                            {
                                nextImage();
                                Thread.sleep(slidePause);
                            }

                            catch(InterruptedException ex)
                            {
                                Logger.getLogger(ImageBox.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }.start();
                
                break;
        }
    }
    
    public void previousImage()
    {
        setImage(hasPrevious() ? imageIndex - 1 : imageCount() - 1);
    }
    
    public void removeImage(int i)
    {
        IMAGE_LIST.remove(i);
    }
}