/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ldtan
 */
public class JavaDB
{
    private String host;
    private String username;
    private String password;
    private ResultSet resultSet;
    
    private Connection connection;
    private Statement sqlStatement;
    
    public JavaDB()
    {
        host = null;
        username = null;
        password = null;
        connection = null;
        sqlStatement = null;
        resultSet = null;
    }
    
    public JavaDB(String host, String username, String password)
    {
        this.host = host;
        this.username = username;
        this.password = password;
        this.connection = null;
        this.sqlStatement = null;
        this.resultSet = null;
    }
    
    public String getHost()
    {
        return host;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public Connection getConnection()
    {
        return connection;
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
    
    public boolean createObject(String objectType, String objectName, String columnDeclaration)
    {
        if(this.isConnected())
        {
            try
            {
                sqlStatement.executeUpdate("CREATE " + objectType + " " + objectName + "(" + columnDeclaration + ")");
                return(true);
            }

            catch(SQLException sqle)
            {
                return(false);
            }
        }
        
        else
        {
            return(false);
        }
    }
    
    public boolean drop(String schemaType, String schemaName)
    {
        if(this.isConnected())
        {
            try
            {
                sqlStatement.executeUpdate("DROP " + schemaType + " " + schemaName);
                return(true);
            }

            catch(SQLException sqle)
            {
                return(false);
            }
        }
        
        else
        {
            return(false);
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
    
    public Statement getSQLStatement()
    {
        return(sqlStatement);
    }
    
    public boolean insert(String objectName, String columns, String values)
    {
        if(this.isConnected())
        {
            try
            {
                this.sqlStatement.executeUpdate("INSERT INTO " + objectName + "(" + columns + ") VALUES(" + values + ")");
                return(true);
            }

            catch(SQLException sqle)
            {
                return(false);
            }
        }
        
        else
        {
            return(false);
        }
    }
    
    public boolean insert(String objectName, String values)
    {
        if(this.isConnected())
        {
            try
            {
                this.resultSet = sqlStatement.executeQuery("SELECT * FROM " + objectName);
                ResultSetMetaData rsmd = this.resultSet.getMetaData();
                String columns = "";
                int columnCount = rsmd.getColumnCount();
                
                for(int i = 1; i <= columnCount; i++)
                {
                    columns += rsmd.getColumnClassName(i);
                    
                    if((i + 1) <= columnCount)
                    {
                        columns += ", ";
                    }
                }
                
                this.insert(objectName, columns, values);
                return(true);
            }
            
            catch(SQLException sqle)
            {
                return(false);
            }
        }
        
        else
        {
            return(false);
        }
    }
    
    public ResultSet select(String columns, String objectName)
    {
        try
        {
            this.resultSet = this.sqlStatement.executeQuery("SELECT " + columns + " FROM " + objectName);
            return(this.resultSet);
        }
        
        catch(SQLException | NullPointerException sqle)
        {
            return(null);
        }
    }
    
    public ResultSet select(String objectName)
    {
        return(this.select("*", objectName));
    }
    
    /*public static void main(String args[]) throws SQLException
    {
        Scanner cin = new Scanner(System.in);
        
        JavaDB ms = new JavaDB("jdbc:mysql://localhost:3306/doths_lms", "root", "");
        ms.setIsConnected(true);
        ms.setIsUpdatable(true);
        
        System.out.println("Connected: " + ms.isConnected());
    }*/
}