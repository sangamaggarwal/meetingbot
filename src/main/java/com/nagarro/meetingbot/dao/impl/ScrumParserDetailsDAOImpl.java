package com.nagarro.meetingbot.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.nagarro.meetingbot.dao.ScrumParserDetailsDAO;
import com.nagarro.meetingbot.dto.nlp.Entities;
import com.nagarro.meetingbot.dto.nlp.NLPData;
import com.nagarro.meetingbot.entity.NlpDetails;
import com.nagarro.meetingbot.util.DBConnectionManager;

public class ScrumParserDetailsDAOImpl implements ScrumParserDetailsDAO{
    
    Connection conn = null;
    
    private Connection getConnection(){
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://ec2-54-243-249-154.compute-1.amazonaws.com:5432/d9rlombttcl7gp?user=zfhvewkbtzlnwi&password=9TRpj1FdKh6S-cCE7CK1tXvHWF&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    
    private void closeConnection(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<NlpDetails> getScrumDetailsFor(String meetingId){
        List<NlpDetails> list = new LinkedList<NlpDetails>();
        try{
            getConnection();
            String selectSQL = "SELECT * FROM public.nlp_details WHERE meetingId = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
            preparedStatement.setString(1, meetingId);
            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()) {
                NlpDetails nlpDetails = new NlpDetails();
                nlpDetails.setComments(rs.getString("comments"));
                nlpDetails.setCompleted(rs.getString("completed"));
                nlpDetails.setIssueType(rs.getString("issuetype"));
                nlpDetails.setMeetingId(rs.getString("meetingid"));
                nlpDetails.setPending(rs.getString("pending"));
                nlpDetails.setText(rs.getString("text"));
                nlpDetails.setUserId(rs.getString("userid"));
                list.add(nlpDetails);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
        return list;
    }
    
    @Override
    public boolean saveSrcumDetails(NLPData nlpData,String userId,String meetingId) {
        Connection conn = DBConnectionManager.getConnection();
        boolean result = false;
        if(conn !=null){
            String query = " insert into public.nlp_details (userid,text,pending,completed,comments,date,meetingid,issuetype)"
              + " values (?, ?, ? , ? ,? , ? ,?, ?)";

            
            PreparedStatement preparedStmt =null;
            try {
            
                preparedStmt = conn.prepareStatement(query);
                Entities entities = nlpData.getEntities();
                if(null != entities.getNumber()) {
                	for(int i=0;i<entities.getNumber().size();i++) {
                		preparedStmt.setString(1, userId);
                		preparedStmt.setString(2, nlpData.getText());
                		String status = entities.getStatus().get(i>entities.getStatus().size()-1?0:i).getValue();
                		String projIn = entities.getProjectIdentifier().get(i>entities.getStatus().size()-1?0:i).getValue();
                		if(status.equalsIgnoreCase("IN PROGRESS"))  {
                			preparedStmt.setString(3, projIn+"-"+entities.getNumber().get(i).getValue());
                			preparedStmt.setString(4, null);
                		} else {
                			preparedStmt.setString(3, null);
                			preparedStmt.setString(4, projIn+"-"+entities.getNumber().get(i).getValue());
                		}
                		preparedStmt.setString (5, null);
                		preparedStmt.setTimestamp(6,getCurrentTimeStamp());
                		preparedStmt.setString(7,meetingId);
                		preparedStmt.setString(8, null);
                		result = preparedStmt.execute();
                	}
                } else {
                	
                	preparedStmt.setString(1, userId);
                	preparedStmt.setString(2, nlpData.getText());
                	for(int i=0;i<entities.getMessageBody().size();i++) {
                		String issueType = entities.getIssueType().get(i>entities.getIssueType().size()-1?0:i).getValue();
                		preparedStmt.setString(3, null);
                		preparedStmt.setString(4, null);
                		preparedStmt.setString(5, entities.getMessageBody().get(i).getValue());
                		preparedStmt.setTimestamp(6,getCurrentTimeStamp());
                		preparedStmt.setString(7,meetingId);
                		preparedStmt.setString(8, issueType.trim());
                		result = preparedStmt.execute();
                	}
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        finally{
        DBConnectionManager.closeConnection();
        }
        }
    return result;
    }
    
private static java.sql.Timestamp getCurrentTimeStamp() {
    java.util.Date today = new java.util.Date();
    return new java.sql.Timestamp(today.getTime());
}

}
