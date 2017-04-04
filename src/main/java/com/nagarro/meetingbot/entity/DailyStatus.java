package com.nagarro.meetingbot.entity;

import java.io.Serializable;

public class DailyStatus implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -8878712307631499508L;

	int id;
    
    int meetingId;
    
    String email;
    
    String data;

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    
    @Override
    public String toString() {
    return new StringBuffer(" Id : ")
    .append(String.valueOf(this.id))
    .append(" meetingId : ")
    .append(String.valueOf(this.meetingId))
    .append("email ")
    .append(this.email)
    .append(" data : ")
    .append(this.data).toString();
    }
}
