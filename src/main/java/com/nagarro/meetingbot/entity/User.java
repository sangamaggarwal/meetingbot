package com.nagarro.meetingbot.entity;

public class User {
    private String userId;
    private String userName;
    private String email;
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Users [userId=").append(userId).append(", userName=").append(userName)
            .append(", email=").append(email).append("]");
        return builder.toString();
    }
}
