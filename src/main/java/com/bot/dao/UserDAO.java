package com.bot.dao;

import java.util.List;

import com.bot.entity.User;

public interface UserDAO {
    
 public boolean saveUser(List<User> user);
    
    public List<User> readUser(String date);

}
