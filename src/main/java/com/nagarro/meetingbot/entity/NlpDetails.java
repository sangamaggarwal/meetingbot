package com.nagarro.meetingbot.entity;

public class NlpDetails {
    private String userId;
    private String text;
    private String pending;
    private String completed;
    private String comments;
    private String meetingId;
    private String issueType;
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
