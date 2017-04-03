package com.bot.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.bot.dao.UserDAO;
import com.bot.entity.User;
import com.bot.util.ConnectionPostgress;

public class UserDAOImpl implements UserDAO{

    @Override
    public boolean saveUser(List<User> users) {
        boolean result = false;
        System.out.println("Inside save method");
        for(User status:users){
            Connection conn = ConnectionPostgress.getConnection();
            if(conn !=null){
                String query = " insert into public.user (userid,email,username)"
                  + " values (?, ?, ?)";
               
                PreparedStatement preparedStmt =null;
                try {
                
                    preparedStmt = conn.prepareStatement(query);
                    preparedStmt.setString(1, status.getUserId());
                    preparedStmt.setString (2, status.getEmail());
                    preparedStmt.setString (3, status.getUserName());
                    result = preparedStmt.execute();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            ConnectionPostgress.closeConnection();
        }
        return result;


    }

    @Override
    public List<User> readUser(String date) {
        // TODO Auto-generated method stub
        return null;
    }

}
