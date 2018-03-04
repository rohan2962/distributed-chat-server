/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javaapplication1.main_server.message;

/**
 *
 * @author vips
 */
public class db_connect {
    public static volatile boolean send=false;
    public static volatile String message="";
    

    /**
     * @param args the command line arguments
     */
   public static Connection con;
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        
  
       Class.forName("com.mysql.jdbc.Driver");
       con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/Main_Server", "main_server", "main@server");
        
        main_server a=new main_server();
        
    }
    
}
