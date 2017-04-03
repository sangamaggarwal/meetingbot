package com.bot.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPostgress {
 static Connection conn = null;
    
    public static Connection getConnection(){
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://ec2-54-163-234-20.compute-1.amazonaws.com:5432/d582v6llnpfu2t?user=ywilsyxqvkrsxd&password=592667d45e7bd0c506755c89ca4f93e16a4aeb8a22a18fba10b1de0ec9d20fac&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory");
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
