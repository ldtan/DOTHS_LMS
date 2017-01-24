/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sys;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class that performs basic file writing and reading.
 * @author LIKE_MIANN
 * @param <T> The type of object to be handled.
 */
public class JavaFH<T>
{
    private String fileExtension;
    private String filePath;
    
    private FileInputStream fileInput;
    private FileOutputStream fileOutput;
    private ObjectInputStream objectInput;
    private ObjectOutputStream objectOutput;
    
    public JavaFH(String filePath)
    {
        this(filePath, "");
    }
    
    public JavaFH(String filePath, String fileExtension)
    {
        if(!isValidFileExtension(fileExtension) || !isValidFilePath(filePath))
        {
            throw new IllegalArgumentException();
        }
        
        this.filePath = filePath;
        this.fileExtension = fileExtension;
    }
    
    public static boolean isValidFilePath(String filePath)
    {
        int lastIndex = filePath.length() - 1;
        boolean foundSlash = filePath.contains("/");
        boolean foundBackslash = filePath.contains("\\");
        
        return(filePath.equals("")
            || ((filePath.charAt(lastIndex) == '/' || filePath.charAt(lastIndex) == '\\')
             && ((foundSlash && !foundBackslash) || (!foundSlash && foundBackslash))));
    }
    
    public static boolean isValidFileName(String fileName)
    {
        return(!(fileName.contains("\\")
              || fileName.contains("/")
              || fileName.contains(":")
              || fileName.contains("*")
              || fileName.contains("?")
              || fileName.contains("\"")
              || fileName.contains("<")
              || fileName.contains(">")
              || fileName.contains("|")));
    }
    
    public static boolean isValidFileExtension(String fileExtension)
    {
        return(fileExtension.equals("") || (isValidFileName(fileExtension) && fileExtension.charAt(0) == '.'));
    }
    
    public static boolean isFound(String filePath, String fileName, String fileExtension)
    {
        return(new File(filePath + fileName + fileExtension).canRead());
    }
    
    public static boolean writeObject(String filePath, String fileName, String fileExtension, Object object)
    {
        if(!isValidFilePath(filePath) || !isValidFileName(fileName) || !isValidFileExtension(fileExtension))
        {
            throw new IllegalArgumentException();
        }
        
        try
        {
            FileOutputStream fileOutput = new FileOutputStream(filePath + fileName + fileExtension);
            ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
            
            objectOutput.writeObject(object);
            objectOutput.close();
            fileOutput.close();
            
            return(true);
        }
        
        catch(IOException ex)
        {
            return(false);
        }
    }
    
    public static File getFile(String filePath, String fileName, String fileExtension)
    {
        return(new File(filePath + fileName + fileExtension));
    }
    
    public static Object getObject(String filePath, String fileName, String fileExtension)
    {
        try
        {
            Object object = null;
            
            if(isFound(filePath, fileName, fileExtension))
            {
                FileInputStream fileInput = new FileInputStream(filePath + fileName + fileExtension);
                ObjectInputStream objectInput = new ObjectInputStream(fileInput);
                object = objectInput.readObject();
                
                objectInput.close();
                fileInput.close();
            }
            
            return(object);
        }
        
        catch(IOException | ClassNotFoundException ex)
        {
            return(null);
        }
    }
    
    public static boolean deleteFile(String filePath, String fileName, String fileExtension, Object object)
    {
        try
        {
            return(Files.deleteIfExists(new File(filePath + fileName + fileExtension).toPath()));
        }

        catch(IOException ex)
        {
            return(false);
        }
    }
    
    public String getFilePath()
    {
        return(filePath);
    }
    
    public void setFilePathString(String filePath)
    {
        if(!isValidFilePath(filePath))
        {
            throw new IllegalArgumentException();
        }
        
        this.filePath = filePath;
    }
    
    public String getFileExtension()
    {
        return(fileExtension);
    }
    
    public void setFileExtension(String fileExtension)
    {
        if(!isValidFileExtension(fileExtension))
        {
            throw new IllegalArgumentException();
        }
        
        this.fileExtension = fileExtension;
    }
    
    public boolean isFound(String fileName)
    {
        return(new File(filePath + fileName + fileExtension).canRead());
    }
    
    public boolean writeObject(String fileName, T object)
    {
        try
        {
            fileOutput = new FileOutputStream(filePath + fileName + fileExtension);
            objectOutput = new ObjectOutputStream(fileOutput);
            
            objectOutput.writeObject(object);
            objectOutput.close();
            fileOutput.close();
            
            return(true);
        }
        
        catch(IOException ex)
        {
            return(false);
        }
    }
    
    public File getFile(String fileName)
    {
        return(new File(filePath + fileName + fileExtension));
    }
    
    public T getObject(String fileName)
    {
        try
        {
            T object = null;
            
            if(isFound(fileName))
            {
                fileInput = new FileInputStream(filePath + fileName + fileExtension);
                objectInput = new ObjectInputStream(fileInput);
                object = (T)objectInput.readObject();
                
                objectInput.close();
                fileInput.close();
            }
            
            return(object);
        }
        
        catch(IOException | ClassNotFoundException ex)
        {
            return(null);
        }
    }
    
    public boolean deleteFile(String fileName)
    {
        try
        {
            return(Files.deleteIfExists(new File(filePath + fileName + fileExtension).toPath()));
        }
        
        catch (IOException ex)
        {
            return(false);
        }
    }
}