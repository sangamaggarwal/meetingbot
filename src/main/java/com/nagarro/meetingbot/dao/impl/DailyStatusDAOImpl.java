package com.nagarro.meetingbot.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import com.nagarro.meetingbot.dao.DailyStatusDAO;
import com.nagarro.meetingbot.entity.DailyStatus;

public class DailyStatusDAOImpl implements DailyStatusDAO{
    
    Connection conn = null;
    
    private Connection getConnection(){
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://ec2-54-243-249-154.compute-1.amazonaws.com:5432/d9rlombttcl7gp?user=zfhvewkbtzlnwi&password=9TRpj1FdKh6S-cCE7CK1tXvHWF&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory");
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn;
    }
    
    public boolean saveDailyStatus(List<DailyStatus> dailyStatus){
        boolean result = false;
        System.out.println("Inside save method");
        for(DailyStatus status:dailyStatus){
            getConnection();
            if(conn !=null){
                String query = " insert into public.daily_status (meetingid,emailid,status,date)"
                  + " values (?, ?, ?,?)";

                PreparedStatement preparedStmt =null;
                try {
                
                    preparedStmt = conn.prepareStatement(query);
                    preparedStmt.setInt(1, status.getMeetingId());
                    preparedStmt.setString (2, status.getEmail());
                    preparedStmt.setString (3, status.getData());
                    preparedStmt.setTimestamp(4,getCurrentTimeStamp());
                    result = preparedStmt.execute();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            closeConnection();
        }
        return result;
    }
    
    public List<DailyStatus> readDailyStatus(String date){
        List<DailyStatus> list = new LinkedList<>();
        try{
            getConnection();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Timestamp startDate=null;
            try {
                startDate = new java.sql.Timestamp(df.parse(date).getTime());
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String selectSQL = "SELECT * FROM public.daily_status WHERE date = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
            preparedStatement.setTimestamp(1, startDate);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                DailyStatus dailyStatus = new DailyStatus();
                dailyStatus.setEmail(rs.getString("emailid"));
                dailyStatus.setData(rs.getString("status"));
                list.add(dailyStatus);
            }
            rs.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }
    private void closeConnection(){
        try {
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private static java.sql.Timestamp getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());

}

}
