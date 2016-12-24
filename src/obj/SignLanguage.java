/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj;

import db.JavaDB;

/**
 *
 * @author Liel Tan
 */
public class SignLanguage extends Gesture
{
    private static final JavaDB DB = new JavaDB("jdbc:mysql://localhost:3306/doths_lms", "root", "");
    public static final String[] ARRAY_FINGER_PARTS = { "distal", "middle", "proximal" };
    public static final String[] ARRAY_HANDS = { "left", "right" };
    public static final String[] ARRAY_HAND_ORIENTATION = { "front", "back" };
    public static final String[] ARRAY_HAND_PARTS = { "pinky", "ring", "middle", "index", "thumb", "palm" };
    
    /*private static final Joint JOINT_LEFT_FRONT_INDEX_DISTAL = getJoint("left", "front", "index", "distal");
    private static final Joint JOINT_LEFT_FRONT_INDEX_MIDDLE = getJoint("left", "front", "index", "middle");
    private static final Joint JOINT_LEFT_FRONT_INDEX_PROXIMAL = getJoint("left", "front", "index", "proximal");
    private static final Joint JOINT_LEFT_FRONT_MIDDLE_DISTAL = getJoint("left", "front", "middle", "distal");
    private static final Joint JOINT_LEFT_FRONT_MIDDLE_MIDDLE = getJoint("left", "front", "middle", "middle");
    private static final Joint JOINT_LEFT_FRONT_MIDDLE_PROXIMAL = getJoint("left", "front", "middle", "proximal");
    private static final Joint JOINT_LEFT_FRONT_PINKY_DISTAL = getJoint("left", "front", "pinky", "distal");
    private static final Joint JOINT_LEFT_FRONT_PINKY_MIDDLE = getJoint("left", "front", "pinky", "middle");
    private static final Joint JOINT_LEFT_FRONT_PINKY_PROXIMAL = getJoint("left", "front", "pinky", "proximal");
    private static final Joint JOINT_LEFT_FRONT_RING_DISTAL = getJoint("left", "front", "ring", "distal");
    private static final Joint JOINT_LEFT_FRONT_RING_MIDDLE = getJoint("left", "front", "ring", "middle");
    private static final Joint JOINT_LEFT_FRONT_RING_PROXIMAL = getJoint("left", "front", "ring", "proximal");
    private static final Joint JOINT_LEFT_FRONT_THUMB_DISTAL = getJoint("left", "front", "thumb", "distal");
    private static final Joint JOINT_LEFT_FRONT_THUMB_PROXIMAL = getJoint("left", "front", "thumb", "proximal");
    private static final Joint JOINT_LEFT_FRONT_PALM = getJoint("left", "front", "palm", "");
    private static final Joint JOINT_LEFT_BACK_INDEX_DISTAL = getJoint("left", "back", "index", "distal");
    private static final Joint JOINT_LEFT_BACK_INDEX_MIDDLE = getJoint("left", "back", "index", "middle");
    private static final Joint JOINT_LEFT_BACK_INDEX_PROXIMAL = getJoint("left", "back", "index", "proximal");
    private static final Joint JOINT_LEFT_BACK_MIDDLE_DISTAL = getJoint("left", "back", "middle", "distal");
    private static final Joint JOINT_LEFT_BACK_MIDDLE_MIDDLE = getJoint("left", "back", "middle", "middle");
    private static final Joint JOINT_LEFT_BACK_MIDDLE_PROXIMAL = getJoint("left", "back", "middle", "proximal");
    private static final Joint JOINT_LEFT_BACK_PINKY_DISTAL = getJoint("left", "back", "pinky", "distal");
    private static final Joint JOINT_LEFT_BACK_PINKY_MIDDLE = getJoint("left", "back", "pinky", "middle");
    private static final Joint JOINT_LEFT_BACK_PINKY_PROXIMAL = getJoint("left", "back", "pinky", "proximal");
    private static final Joint JOINT_LEFT_BACK_RING_DISTAL = getJoint("left", "back", "ring", "distal");
    private static final Joint JOINT_LEFT_BACK_RING_MIDDLE = getJoint("left", "back", "ring", "middle");
    private static final Joint JOINT_LEFT_BACK_RING_PROXIMAL = getJoint("left", "back", "ring", "proximal");
    private static final Joint JOINT_LEFT_BACK_THUMB_DISTAL = getJoint("left", "back", "thumb", "distal");
    private static final Joint JOINT_LEFT_BACK_THUMB_PROXIMAL = getJoint("left", "back", "thumb", "proximal");
    private static final Joint JOINT_LEFT_BACK_PALM = getJoint("left", "back", "palm", "");
    private static final Joint JOINT_RIGHT_FRONT_INDEX_DISTAL = getJoint("right", "front", "index", "distal");
    private static final Joint JOINT_RIGHT_FRONT_INDEX_MIDDLE = getJoint("right", "front", "index", "middle");
    private static final Joint JOINT_RIGHT_FRONT_INDEX_PROXIMAL = getJoint("right", "front", "index", "proximal");
    private static final Joint JOINT_RIGHT_FRONT_MIDDLE_DISTAL = getJoint("right", "front", "middle", "distal");
    private static final Joint JOINT_RIGHT_FRONT_MIDDLE_MIDDLE = getJoint("right", "front", "middle", "middle");
    private static final Joint JOINT_RIGHT_FRONT_MIDDLE_PROXIMAL = getJoint("right", "front", "middle", "proximal");
    private static final Joint JOINT_RIGHT_FRONT_PINKY_DISTAL = getJoint("right", "front", "pinky", "distal");
    private static final Joint JOINT_RIGHT_FRONT_PINKY_MIDDLE = getJoint("right", "front", "pinky", "middle");
    private static final Joint JOINT_RIGHT_FRONT_PINKY_PROXIMAL = getJoint("right", "front", "pinky", "proximal");
    private static final Joint JOINT_RIGHT_FRONT_RING_DISTAL = getJoint("right", "front", "ring", "distal");
    private static final Joint JOINT_RIGHT_FRONT_RING_MIDDLE = getJoint("right", "front", "ring", "middle");
    private static final Joint JOINT_RIGHT_FRONT_RING_PROXIMAL = getJoint("right", "front", "ring", "proximal");
    private static final Joint JOINT_RIGHT_FRONT_THUMB_DISTAL = getJoint("right", "front", "thumb", "distal");
    private static final Joint JOINT_RIGHT_FRONT_THUMB_PROXIMAL = getJoint("right", "front", "thumb", "proximal");
    private static final Joint JOINT_RIGHT_FRONT_PALM = getJoint("right", "front", "palm", "");
    private static final Joint JOINT_RIGHT_BACK_INDEX_DISTAL = getJoint("right", "back", "index", "distal");
    private static final Joint JOINT_RIGHT_BACK_INDEX_MIDDLE = getJoint("right", "back", "index", "middle");
    private static final Joint JOINT_RIGHT_BACK_INDEX_PROXIMAL = getJoint("right", "back", "index", "proximal");
    private static final Joint JOINT_RIGHT_BACK_MIDDLE_DISTAL = getJoint("right", "back", "middle", "distal");
    private static final Joint JOINT_RIGHT_BACK_MIDDLE_MIDDLE = getJoint("right", "back", "middle", "middle");
    private static final Joint JOINT_RIGHT_BACK_MIDDLE_PROXIMAL = getJoint("right", "back", "middle", "proximal");
    private static final Joint JOINT_RIGHT_BACK_PINKY_DISTAL = getJoint("right", "back", "pinky", "distal");
    private static final Joint JOINT_RIGHT_BACK_PINKY_MIDDLE = getJoint("right", "back", "pinky", "middle");
    private static final Joint JOINT_RIGHT_BACK_PINKY_PROXIMAL = getJoint("right", "back", "pinky", "proximal");
    private static final Joint JOINT_RIGHT_BACK_RING_DISTAL = getJoint("right", "back", "ring", "distal");
    private static final Joint JOINT_RIGHT_BACK_RING_MIDDLE = getJoint("right", "back", "ring", "middle");
    private static final Joint JOINT_RIGHT_BACK_RING_PROXIMAL = getJoint("right", "back", "ring", "proximal");
    private static final Joint JOINT_RIGHT_BACK_THUMB_DISTAL = getJoint("right", "back", "thumb", "distal");
    private static final Joint JOINT_RIGHT_BACK_THUMB_PROXIMAL = getJoint("right", "back", "thumb", "proximal");
    private static final Joint JOINT_RIGHT_BACK_PALM = getJoint("right", "back", "palm", "");*/
    
    private SignLanguage()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public static String translateGesture(Gesture gesture)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public static SignLanguage translateText(String text)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public static boolean addSignLanguage(Gesture gesture, String text)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public static boolean removeSignLanguage(SignLanguage language)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}