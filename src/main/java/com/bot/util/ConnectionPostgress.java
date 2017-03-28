package com.bot.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPostgress {
 static Connection conn = null;
    
    public static Connection getConnection(){
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://ec2-54-243-249-154.compute-1.amazonaws.com:5432/d9rlombttcl7gp?user=zfhvewkbtzlnwi&password=9TRpj1FdKh6S-cCE7CK1tXvHWF&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory");
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn;
    }
    
    public static void closeConnection(){
        try {
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
