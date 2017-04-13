
package com.nagarro.meetingbot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "nlpdetails")
public class NLPDetail {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private String id;
	@Column(name = "userId")
    private String userId;
	@Column(name = "text")
    private String text;
	@Column(name = "pending")
    private String pending;
	@Column(name = "completed")
    private String completed;
	@Column(name = "comments")
    private String comments;
	@Column(name = "meetingId")
    private String meetingId;
	@Column(name = "issueType")
    private String issueType;
	@Column(name="status")
	private String status;
	
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getPending() {
        return pending;
    }
    public void setPending(String pending) {
        this.pending = pending;
    }
    public String getCompleted() {
        return completed;
    }
    public void setCompleted(String completed) {
        this.completed = completed;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
    public String getMeetingId() {
        return meetingId;
    }
    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }
    public String getIssueType() {
        return issueType;
    }
    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }
    public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("NlpDetails [userId=").append(userId).append(", text=").append(text)
            .append(", pending=").append(pending).append(", completed=").append(completed)
            .append(", comments=").append(comments).append(", meetingId=").append(meetingId)
            .append(", issueType=").append(issueType).append("]");
        return builder.toString();
    }
   
    
    

}
