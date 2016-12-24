/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import marvin.image.MarvinImage;
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
 * The class that build the video panel for the frame.
 * @author Liel Tan
 */
public final class VideoPanel extends ImageBox
{
    private static final OpenCVFrameConverter.ToIplImage FRAME_CONVERTER = new OpenCVFrameConverter.ToIplImage();
    public final Gesture GESTURE;
    private FrameGrabber frameGrabber;
    private boolean status_recorded;
    private boolean status_resumeVideoCapture;
    private boolean status_runVideoCapture;
    private Thread thread_videoCapture;
    
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
    
    /**
     * The constructor for thus class.
     */
    public VideoPanel()
    {
        GESTURE = new Gesture();
        status_recorded = false;
        status_resumeVideoCapture = false;
        status_runVideoCapture = false;
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
     * Informs if the video capture is running.
     * @return True if the video panel is running, else false.
     */
    public boolean isRunning()
    {
        return(status_runVideoCapture);
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
     * Runs the video capture.
     */
    protected void startVideoCapture()
    {
        frameGrabber = new VideoInputFrameGrabber(1);
        status_recorded = false;
        status_runVideoCapture = true;
        status_resumeVideoCapture = true;
        
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
                    
                    if(status_recorded)
                    {
                        addImage(img);
                    }
                    
                    else
                    {
                        setImage(img);
                    }
                    
                    scanPanel();
                    viewSelectedAreas(true);
                    
                    Set<Rectangle> areas = getSelectedAreas();
                    
                    if(status_recorded && !areas.isEmpty())
                    {
                        Skeleton skeleton = new Skeleton();
                        
                        for(Rectangle r : areas)
                        {
                            Point p = new Point((int)r.getCenterX(), (int)r.getCenterY());
                            Color c = new Color(img.getIntColor(p.x, p.y));
                            
                            skeleton.addJoint(new Joint(c, p));
                        }
                        
                        GESTURE.addSkeleton(GESTURE.skeletonCount(), skeleton);
                        addImage(img);
                    }
                }
                
                frameGrabber.stop();
            }
            
            catch(FrameGrabber.Exception e)
            {
                System.out.println(e.getMessage());
            }
        });
        
        thread_videoCapture.start();
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
            Logger.getLogger(VideoPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Clear all gestures that have been stored.
     */
    public void clearGesture()
    {
        GESTURE.clear();
    }
    
    /**
     * Enables the system to record the captured frames.
     * @param state True to enable recording, else false.
     */
    public void setEnableRecordImages(boolean state)
    {
        status_recorded = state;
    }
}