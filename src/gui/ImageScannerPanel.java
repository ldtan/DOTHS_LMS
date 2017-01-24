/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import event.ColorEvent;
import event.ColorListener;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import marvin.gui.MarvinImagePanel;
import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;

/**
 * 
 * @author Liel Tan
 */
public class ImageScannerPanel extends MarvinImagePanel
{
    private final Set<ColorListener> LISTENER_COLOR;;
    private Set<Rectangle> selectedAreas;
    private boolean viewSelected;
    
    private static double colorPercentageMatch(Color cc, Color sc, double redLimit, double greenLimit, double blueLimit, double alphaLimit)
    {
        double ad = 1 - (Math.abs(sc.getAlpha() - cc.getAlpha()) / (alphaLimit + 1));
        double rd = 1 - (Math.abs(sc.getRed()- cc.getRed()) / (redLimit + 1));
        double gd = 1 - (Math.abs(sc.getGreen()- cc.getGreen()) / (greenLimit + 1));
        double bd = 1 - (Math.abs(sc.getBlue()- cc.getBlue()) / (blueLimit + 1));
        
        return((ad + rd + gd + bd) / 4);
    }
    
    public static boolean isAcceptedColor(Color c1, Color c2, double redLimit, double greenLimit, double blueLimit, double alphaLimit)
    {
        boolean rc = Math.abs(c1.getRed() - c2.getRed()) <= redLimit;
        boolean gc = Math.abs(c1.getGreen()- c2.getGreen()) <= greenLimit;
        boolean bc = Math.abs(c1.getBlue()- c2.getBlue()) <= blueLimit;
        boolean ac = Math.abs(c1.getAlpha()- c2.getAlpha()) <= alphaLimit;
        
        return(rc && gc && bc && ac);
    }
    
    private void setupComponents()
    {
        setBackground(Color.BLACK);
    }
    
    public ImageScannerPanel()
    {
        LISTENER_COLOR = new HashSet();
        viewSelected = false;
        selectedAreas = new HashSet();
        
        setupComponents();
    }
    
    public ImageScannerPanel(MarvinImage img)
    {
        LISTENER_COLOR = new HashSet();
        viewSelected = false;
        selectedAreas = new HashSet();
        
        if(img != null)
        {
            setImage(img);
        }
        
        setupComponents();
    }
    
    public boolean addColorListener(ColorListener l)
    {
        return(LISTENER_COLOR.add(l));
    }
    
    public void clearSelectedAreas()
    {
        selectedAreas.clear();
        reload();
    }
    
    public Set<ColorListener> getColorListeners()
    {
        return(LISTENER_COLOR);
    }
    
    public static List<Rectangle> getColorRegions(MarvinImage image, Color targetColor, double redLimit, double greenLimit, double blueLimit, double alphaLimit)
    {
        MarvinImage img = image;
        List<Rectangle> points = new ArrayList();
        List<Rectangle> regions = new ArrayList();
        int width = img.getWidth();
        int height = img.getHeight();
        
        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width; x++)
            {
                if(isAcceptedColor(targetColor, new Color(img.getIntColor(x, y), true), redLimit, greenLimit, blueLimit, alphaLimit))
                {
                    points.add(new Rectangle(x - 1, y - 1, 2, 2));
                }
            }
        }
        
        while(true)
        {
            boolean intersected = false;

            for(Rectangle r1 : points)
            {
                if(regions.isEmpty())
                {
                    regions.add(r1);
                    continue;
                }

                List<Rectangle> temp = new ArrayList();
                Rectangle r_intersect = null;
                intersected = false;

                temp.addAll(regions);

                for(Rectangle r2 : temp)
                {
                    if(r1 == null || r2 == null)
                    {
                        continue;
                    }
                    
                    if(r2.intersects(r1))
                    {
                        r_intersect = r2;
                        intersected = true;
                        break;
                    }
                }

                if(intersected)
                {
                    int x = (int)Math.min(r1.getMinX(), r_intersect.getMinX());
                    int y = (int)Math.min(r1.getMinY(), r_intersect.getMinY());
                    int w = (int)Math.abs(Math.max(r1.getMaxX(), r_intersect.getMaxX()) - x);
                    int h = (int)Math.abs(Math.max(r1.getMaxY(), r_intersect.getMaxY()) - y);

                    regions.remove(r_intersect);
                    regions.add(new Rectangle(x, y, w, h));
                }

                else
                {
                    regions.add(r1);
                }
            }

            if(!intersected)
            {
                break;
            }

            points.clear();
            points.addAll(regions);
            regions.clear();
        }
        
        return(regions);
    }
    
    @Override
    public MarvinImage getImage()
    {
        return(super.getImage());
    }
    
    public Set<Rectangle> getSelectedAreas()
    {
        return(selectedAreas);
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        if(viewSelected)
        {
            Graphics2D g2 = (Graphics2D)g;
            
            g2.setColor(Color.GREEN);
            g2.setStroke(new BasicStroke(3));

            for(Rectangle region : selectedAreas)
            {
                g2.draw(region);
            }
        }
    }
    
    public void reload()
    {
        revalidate();
        repaint();
    }
    
    public void removeAllColorListeners()
    {
        LISTENER_COLOR.clear();
    }
    
    public boolean removeColorListener(ColorListener l)
    {
        return(LISTENER_COLOR.remove(l));
    }
    
    public void scanImage()
    {
        MarvinImage img = getImage();
        
        if(!LISTENER_COLOR.isEmpty() && img != null)
        {
            selectedAreas.clear();

            final ImageScannerPanel THIS = this;
            final Thread[] THREADS = new Thread[LISTENER_COLOR.size()];
            int i = 0;

            for(ColorListener l : LISTENER_COLOR)
            {
                THREADS[i] = new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        List<Rectangle> regions = getColorRegions(img, l.getTargetColor(), l.getRedLimit(), l.getGreenLimit(), l.getBlueLimit(), l.getAlphaLimit());

                        for(Rectangle region : regions)
                        {
                            Dimension area = region.getSize();
                            Dimension maxArea = l.getMaxArea();
                            Dimension minArea = l.getMinArea();

                            maxArea = maxArea.width <= 0 || maxArea.height <= 0 ? null : maxArea;
                            minArea = minArea.width <= 0 || minArea.height <= 0 ? null : minArea;

                            if(maxArea != null && minArea != null)
                            {
                                selectedAreas.add(region);
                                l.colorDetected(new ColorEvent(THIS, region));
                            }

                            else if(maxArea == null && minArea != null && area.width >= minArea.width && area.height >= minArea.height)
                            {
                                selectedAreas.add(region);
                                l.colorDetected(new ColorEvent(THIS, region));
                            }

                            else if(maxArea != null && minArea == null && area.width <= maxArea.width && area.height <= maxArea.height)
                            {
                                selectedAreas.add(region);
                                l.colorDetected(new ColorEvent(THIS, region));
                            }

                            else if(maxArea == null && minArea == null)
                            {
                                selectedAreas.add(region);
                                l.colorDetected(new ColorEvent(THIS, region));
                            }
                        }
                    }
                });

                THREADS[i++].start();
            }

            for(Thread thread : THREADS)
            {
                try
                {
                    thread.join();
                }

                catch(InterruptedException ex)
                {
                    Logger.getLogger(ImageScannerPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void selectArea(Rectangle region)
    {
        selectedAreas.add(region);
        reload();
    }
    
    public void viewSelectedAreas(boolean state)
    {
        viewSelected = state;
        reload();
    }
}