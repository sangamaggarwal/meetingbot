package com.bot.dao;

import java.util.List;

import com.bot.entity.DailyStatus;

public interface DailyStatusDAO {
    
    public boolean saveDailyStatus(List<DailyStatus> dailyStatus);
    
    public List<DailyStatus> readDailyStatus(String date);

}
