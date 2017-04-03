package com.bot.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import com.bot.dao.WorkDetailDAO;
import com.bot.entity.Question;
import com.bot.entity.WorkDetail;
import com.bot.util.ConnectionPostgress;

public class WorkDetailDAOImpl implements WorkDetailDAO{

    @Override
    public boolean saveWorkDetail(List<WorkDetail> dailyStatus) {
        boolean result = false;
        System.out.println("Inside save method");
        for(WorkDetail status:dailyStatus){
            Connection conn = ConnectionPostgress.getConnection();
            if(conn !=null){
                String query = " insert into public.workdetail (userId,meetingid,ques,answer,date)"
                  + " values (?, ?, CAST( ? AS questions) ,? ,?)";

                PreparedStatement preparedStmt =null;
                try {
                
                    preparedStmt = conn.prepareStatement(query);
                    preparedStmt.setString(1, status.getUserId());
                    preparedStmt.setString (2, status.getMeetingId());
                    preparedStmt.setString (3, status.getQues().name());
                    preparedStmt.setString (4, status.getAnswer());
                    preparedStmt.setTimestamp(5,getCurrentTimeStamp());
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

    private static java.sql.Timestamp getCurrentTimeStamp() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());
}

    
    @Override
    public List<WorkDetail> readWorkDetail(String date) {
        List<WorkDetail> list = new LinkedList<>();
        try{
            Connection conn = ConnectionPostgress.getConnection();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Timestamp startDate=null;
            try {
                startDate = new java.sql.Timestamp(df.parse(date).getTime());
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String selectSQL = "SELECT * FROM public.workdetail WHERE date = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
            preparedStatement.setTimestamp(1, startDate);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                WorkDetail dailyStatus = new WorkDetail();
                dailyStatus.setUserId(rs.getString("userId"));
                dailyStatus.setMeetingId(rs.getString("meetingid"));
                dailyStatus.setQues(Question.valueOf(rs.getString("ques")));
                dailyStatus.setAnswer(rs.getString("answer"));
                list.add(dailyStatus);
            }
            rs.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;    }

}
