/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sys;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ldtan
 */
public class JavaDB
{
    private String host;
    private String username;
    private String password;
    
    private Connection connection;
    private Statement sqlStatement;
    
    public JavaDB()
    {
        host = null;
        username = null;
        password = null;
        connection = null;
        sqlStatement = null;
    }
    
    public JavaDB(String host, String username, String password)
    {
        this.host = host;
        this.username = username;
        this.password = password;
        this.connection = null;
        this.sqlStatement = null;
    }
    
    public String getHost()
    {
        return(host);
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public String getUsername()
    {
        return(username);
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return(password);
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public void setIsConnected(boolean state)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            connection = state ? DriverManager.getConnection(host, username, password) : null;
        }
        
        catch(SQLException | ClassNotFoundException ex)
        {
            connection = null;
        }
    }
    
    public boolean isConnected()
    {
        return(connection != null);
    }
    
    public void setIsUpdatable(boolean state)
    {
        try
        {
            sqlStatement = state ? connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)
                                 : connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        }
        
        catch(SQLException sqle)
        {
            sqlStatement = null;
        }
    }
    
    public ResultSet executeQuery(String query)
    {
        try
        {
            return(sqlStatement.executeQuery(query));
        }
        
        catch(SQLException ex)
        {
            return(null);
        }
    }
    
    public boolean executeUpdate(String query)
    {
        try
        {
            sqlStatement.executeUpdate(query);
            return(true);
        }
        
        catch(SQLException ex)
        {
            return(false);
        }
    }
}