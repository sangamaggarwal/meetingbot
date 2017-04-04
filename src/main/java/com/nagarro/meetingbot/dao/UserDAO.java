package com.nagarro.meetingbot.dao;

import java.util.List;

import com.nagarro.meetingbot.entity.User;

public interface UserDAO {
    
 public boolean saveUser(List<User> user);
    
    public List<User> readUser(String date);

}
