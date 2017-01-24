/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj;

import sys.JavaDB;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import sys.JavaFH;

/**
 * The class that holds the gesture and text translation of a sign language.
 * @author Liel Tan
 */
public class SignLanguage implements Serializable
{
    private static final JavaDB DB = new JavaDB("jdbc:mysql://localhost:3306/doths_lms", "root", "");
    private static final JavaFH<SignLanguage> FH = new JavaFH("dictionary/", ".slg");
    
    private Gesture gesture;
    private String text;
    
    public static final String[] ARRAY_FINGER_PARTS = {"distal", "middle", "proximal"};
    public static final String[] ARRAY_HAND_USED = {"left", "right"};
    public static final String[] ARRAY_HAND_ORIENTATION = {"front", "back"};
    public static final String[] ARRAY_HAND_PARTS = {"pinky", "ring", "middle", "index", "thumb", "palm"};
    
    public final int ID;
    
    private SignLanguage(int id, Gesture gesture, String text)
    {
        ID = id;
        this.gesture = gesture;
        this.text = text;
    }
    
    public static boolean addSignLanguage(Gesture gesture, String text)
    {
        try
        {
            DB.setIsConnected(true);
            DB.setIsUpdatable(true);
            DB.executeUpdate("INSERT INTO sign_language(file_name) VALUES('')");
            
            
            ResultSet rs = DB.executeQuery("SELECT id FROM sign_language ORDER BY id DESC LIMIT 1");
            rs.next();
            
            int id = rs.getInt(1);
            SignLanguage signLanguage = new SignLanguage(id, gesture, text);
            
            FH.writeObject(id + "", signLanguage);
            
            return(DB.executeUpdate("UPDATE sign_language" 
                                  + " SET file_name = '" + id + "'"
                                  + " WHERE id = " + id));
        }
        
        catch (SQLException ex)
        {
            return(false);
        }
        
        finally
        {
            DB.setIsUpdatable(false);
            DB.setIsConnected(false);
        }
    }
    
    public static SignLanguage getSignLanguage(int id)
    {
        try
        {
            DB.setIsConnected(true);
            DB.setIsUpdatable(false);
            
            ResultSet rs = DB.executeQuery("SELECT file_name FROM sign_language WHERE id = " + id);
            rs.next();
            
            return(FH.getObject(rs.getString(1)));
        }
        
        catch(SQLException ex)
        {
            return(null);
        }
    }
    
    public static String translateGesture(Gesture gesture)
    {
        try
        {
            DB.setIsConnected(true);
            DB.setIsUpdatable(false);

            ResultSet rs = DB.executeQuery("SELECT file_name FROM sign_language");
            SignLanguage signLanguageMatch = null;
            double percentageMatch = 0;
            
            while(rs.next())
            {
                SignLanguage sl = FH.getObject(rs.getString(1));
                double pm = sl.gesture.percentageMatch(gesture);
                
                if(pm > percentageMatch)
                {
                    signLanguageMatch = sl;
                    percentageMatch = pm;
                }
            }
            
            return(signLanguageMatch.text);
        }

        catch(SQLException | NullPointerException ex)
        {
            return(null);
        }

        finally
        {
            DB.setIsUpdatable(false);
            DB.setIsConnected(false);
        }
    }
    
    public static SignLanguage translateText(String text)
    {
        try
        {
            DB.setIsConnected(true);
            DB.setIsUpdatable(false);

            ResultSet rs = DB.executeQuery("SELECT file_name FROM sign_language");
            
            while(rs.next())
            {
                SignLanguage sl = (SignLanguage)FH.getObject(rs.getString(1));
                
                if(sl.text.equals(text))
                {
                    return(sl);
                }
            }
            
            return(null);
        }

        catch(SQLException | NullPointerException ex)
        {
            return(null);
        }

        finally
        {
            DB.setIsUpdatable(false);
            DB.setIsConnected(false);
        }
    }
    
    public static boolean removeSignLanguage(int id)
    {
        try
        {
            DB.setIsConnected(true);
            DB.setIsUpdatable(true);
            
            ResultSet rs = DB.executeQuery("SELECT file_name FROM sign_language WHERE id = " + id);
            rs.next();
            
            return(FH.deleteFile(rs.getString(1)) && DB.executeUpdate("DELETE FROM sign_language WHERE id = " + id));
        }
        
        catch(SQLException ex)
        {
            return(false);
        }
        
        finally
        {
            DB.setIsUpdatable(false);
            DB.setIsConnected(false);
        }
    }
    
    public Gesture getGesture()
    {
        return(gesture);
    }
    
    public void setGesture(Gesture gesture)
    {
        this.gesture = gesture;
    }
    
    public String getText()
    {
        return(text);
    }
    
    public void setText(String text)
    {
        this.text = text;
    }
}