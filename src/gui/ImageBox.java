/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import event.ColorListener;
import java.awt.Rectangle;
import static marvin.MarvinPluginCollection.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;

/**
 *
 * @author Liel Tan
 */
public class ImageBox extends ImageScannerPanel
{
    private final List<MarvinImage> IMAGE_LIST;
    private final ImageScannerPanel IMAGE_SCANNER;
    private MarvinImage imageOut;
    private MarvinImage imageFiller;
    private int imageIndex;
    private boolean slide;
    private boolean hasImage;
    private long slidePause;
    public static final int NORMAL_MODE = 0;
    public static final int SLIDE_MODE = 1;
    
    private void configureComponents()
    {
        addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                setImage(IMAGE_LIST.size() > 0 ? IMAGE_LIST.get(imageIndex) : null);
            }
        });
    }
    
    public ImageBox()
    {
        IMAGE_LIST = new ArrayList();
        IMAGE_SCANNER = new ImageScannerPanel();
        imageFiller = MarvinImageIO.loadImage("./img/no_image.png");
        imageOut = imageFiller;
        imageIndex = 0;
        slidePause = 1000;
        slide = false;
        hasImage = false;
        
        configureComponents();
    }
    
    @Override
    public boolean addColorListener(ColorListener cl)
    {
        return(super.addColorListener(cl) && IMAGE_SCANNER.addColorListener(cl));
    }
    
    @Override
    public boolean removeColorListener(ColorListener cl)
    {
        return(super.removeColorListener(cl) && IMAGE_SCANNER.removeColorListener(cl));
    }
    
    public void addImage(MarvinImage image)
    {
        IMAGE_LIST.add(image);
        setImage(imageCount() - 1);
        
        hasImage = !IMAGE_LIST.isEmpty();
    }
    
    public void clearImages()
    {
        IMAGE_LIST.clear();
        setImage(null);
        
        hasImage = !IMAGE_LIST.isEmpty();
    }
    
    @Override
    public MarvinImage getImage()
    {
        return(hasImage && !imageOut.getFormatName().equals(imageFiller.getFormatName()) ? super.getImage() : null);
    }
    
    public int getImageIndex()
    {
        return(imageIndex);
    }
    
    public MarvinImage getImageFiller()
    {
        return(imageFiller);
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
        if(!IMAGE_LIST.isEmpty())
        {
            clearSelectedAreas();
            setImage(hasNext() ? imageIndex + 1 : 0);
        }
    }
    
    @Override
    public void scanImage()
    {
        if(hasImage)
        {
            super.scanImage();
        }
    }
    
    public void scanOriginalImage()
    {
        if(hasImage)
        {
            IMAGE_SCANNER.setImage(getOriginalImage());
            IMAGE_SCANNER.scanImage();
            clearSelectedAreas();
            
            for(Rectangle r : IMAGE_SCANNER.getSelectedAreas())
            {
                selectArea(r);
            }
        }
    }
    
    @Override
    public void setImage(MarvinImage image)
    {
        imageOut = image != null ? image.clone() : imageFiller.clone();
        hasImage = image != null;
        int width = getWidth();
        int height = getHeight();
        
        if(width > 0 && height > 0)
        {
            imageOut.resize(width, height);
            imageOut.update();
        }
        
        super.setImage(imageOut);
        clearSelectedAreas();
        reload();
    }
    
    public void setImage(int i)
    {
        if(i >= 0 && i < IMAGE_LIST.size())
        {
            imageIndex = i;
            hasImage = true;
            
            setImage(IMAGE_LIST.get(imageIndex));
        }
        
        else
        {
            setImage(imageFiller);
            hasImage = false;
        }
    }
    
    public void setImageFiller(MarvinImage img)
    {
        if(img != null)
        {
            imageFiller = img;
            
            if(IMAGE_LIST.isEmpty())
            {
                setImage(null);
            }
        }
    }
    
    public void setImageMode(int mode)
    {
        switch(mode)
        {
            case 0:
                
                slide = false;
                break;
                
            case 1:
                
                slide = !IMAGE_LIST.isEmpty();
                
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
        if(!IMAGE_LIST.isEmpty())
        {
            clearSelectedAreas();
            setImage(hasPrevious() ? imageIndex - 1 : imageCount() - 1);
        }
    }
    
    public void removeImage()
    {
        removeImage(imageIndex);
        hasImage = !IMAGE_LIST.isEmpty();
    }
    
    public void removeImage(int i)
    {
        IMAGE_LIST.remove(i);
        setImage(imageIndex >= IMAGE_LIST.size() ? --imageIndex : imageIndex);
        
        hasImage = !IMAGE_LIST.isEmpty();
    }
}