/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import event.ColorListener;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;
import obj.Gesture;
import obj.Joint;
import obj.Skeleton;
import org.bytedeco.javacpp.opencv_core;
import static org.bytedeco.javacpp.opencv_core.CV_PI;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.VideoInputFrameGrabber;

/**
 * The class that build the media panel for the frame.
 * @author Liel Tan
 */
public final class MediaPanel extends ImageBox
{
    private static final OpenCVFrameConverter.ToIplImage FRAME_CONVERTER = new OpenCVFrameConverter.ToIplImage();
    public final Gesture GESTURE;
    private FrameGrabber frameGrabber;
    private boolean status_recorded;
    private boolean status_resumeVideoCapture;
    private boolean status_runVideoCapture;
    private Thread thread_videoCapture;
    private Thread thread_videoScan;
    private final List<MarvinImage> LIST_FRAME_QUEUE;
    private final List<Map<Rectangle, Color>> LIST_AREA_QUEUE;
    private final ImageScannerPanel FRAME_SCANNER;
    
    /**
     * Converts image of type IplImage to type BufferedImage.
     * @param img IplImage.
     * @return BufferedImage.
     */
    private static BufferedImage IplImageToBufferedImage(opencv_core.IplImage img)
    {
        OpenCVFrameConverter.ToIplImage grabberConverter = new OpenCVFrameConverter.ToIplImage();
        Java2DFrameConverter paintConverter = new Java2DFrameConverter();
        Frame frame = grabberConverter.convert(img);
        
        return(paintConverter.getBufferedImage(frame,1));
    }
    
    public void setupComponents()
    {
        setImageFiller(MarvinImageIO.loadImage("./img/logo_1001x368.png"));
    }
    
    /**
     * The constructor for thus class.
     */
    public MediaPanel()
    {
        GESTURE = new Gesture();
        LIST_FRAME_QUEUE = new ArrayList();
        LIST_AREA_QUEUE = new ArrayList();
        FRAME_SCANNER = new ImageScannerPanel();
        status_recorded = false;
        status_resumeVideoCapture = false;
        status_runVideoCapture = false;
        
        setupComponents();
    }
    
    /**
     * Informs if the video capture is running.
     * @return True if the video panel is running, else false.
     */
    public boolean isRunning()
    {
        return(status_runVideoCapture);
    }
    
    /**
     * Informs if the video capture is on play.
     * @return True if the video is on play, else false.
     */
    public boolean isPlaying()
    {
        return(status_resumeVideoCapture);
    }
    
    /**
     * Runs the video capture.
     */
    protected void startVideoCapture()
    {
        frameGrabber = new VideoInputFrameGrabber(0);
        status_recorded = false;
        status_runVideoCapture = true;
        status_resumeVideoCapture = true;
        
        FRAME_SCANNER.removeAllColorListeners();
        
        for(ColorListener cl : getColorListeners())
        {
            FRAME_SCANNER.addColorListener(cl);
        }
        
        thread_videoCapture = new Thread(() ->
        {
            try
            {
                frameGrabber.start();
                frameGrabber.setAspectRatio(CV_PI);
                
                while(status_runVideoCapture)
                {
                    opencv_core.IplImage frame = FRAME_CONVERTER.convert(frameGrabber.grabFrame());
                    
                    if(frame == null || !status_resumeVideoCapture)
                    {
                        continue;
                    }
                    
                    MarvinImage img = new MarvinImage(IplImageToBufferedImage(frame));
                    
                    setImage(img);
                    LIST_FRAME_QUEUE.add(img);
                }
                
                frameGrabber.stop();
            }
            
            catch(FrameGrabber.Exception e)
            {
                System.out.println(e.getMessage());
            }
        });
        
        thread_videoScan = new Thread(() ->
        {
            while(true)
            {
                if(!LIST_FRAME_QUEUE.isEmpty())
                {
                    MarvinImage img = LIST_FRAME_QUEUE.get(0);
                    Skeleton skeleton = new Skeleton();
                    
                    if(img == null)
                    {
                        continue;
                    }
                    
                    LIST_FRAME_QUEUE.remove(0);
                    FRAME_SCANNER.setImage(img);
                    FRAME_SCANNER.scanImage();
                    scanImage();
                    viewSelectedAreas(true);
                    
                    System.out.println("Joints Scanned: " + FRAME_SCANNER.getSelectedAreas().size());
                    System.out.println("Frame Queue: " + LIST_FRAME_QUEUE.size());
                    
                    for(Rectangle r : FRAME_SCANNER.getSelectedAreas())
                    {
                        Point p = new Point((int)r.getCenterX(), (int)r.getCenterY());
                        skeleton.addJoint(new Joint(new Color(img.getIntColor(p.x, p.y)), p));
                    }
                    
                    if(GESTURE.skeletonCount() == Integer.MAX_VALUE)
                    {
                        GESTURE.removeSkeleton(0);
                    }
                    
                    GESTURE.addSkeleton(skeleton);
                }
                
                if(!thread_videoCapture.isAlive() && LIST_FRAME_QUEUE.isEmpty())
                {
                    break;
                }
            }
        });
        
        thread_videoCapture.start();
        thread_videoScan.start();
    }
    
    /**
     * Temporarily stops the video capture.
     */
    protected void pauseVideoCapture()
    {
        status_resumeVideoCapture = false;
    }
    
    /**
     * Resumes the video capture.
     */
    protected void resumeVideoCapture()
    {
        status_resumeVideoCapture = true;
    }
    
    /**
     * Permanently stops the video capture.
     */
    protected void stopVideoCapture()
    {
        try
        {
            status_runVideoCapture = false;
            thread_videoCapture.join();
        }
        
        catch(InterruptedException ex)
        {
            Logger.getLogger(MediaPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Enables the system to record the captured frames.
     * @param state True to enable recording, else false.
     */
    public void setEnableRecordImages(boolean state)
    {
        if(state)
        {
            clearRecordedGesture();
        }
        
        status_recorded = state;
    }
    
    public Gesture getRecordedGesture()
    {
        return(GESTURE);
    }
    
    /**
     * Clear all gestures that have been stored.
     */
    public void clearRecordedGesture()
    {
        GESTURE.clear();
    }
}