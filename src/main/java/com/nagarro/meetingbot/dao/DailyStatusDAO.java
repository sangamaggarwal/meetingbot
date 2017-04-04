package com.nagarro.meetingbot.dao;

import java.util.List;

import com.nagarro.meetingbot.entity.DailyStatus;

public interface DailyStatusDAO {
    
    public boolean saveDailyStatus(List<DailyStatus> dailyStatus);
    
    public List<DailyStatus> readDailyStatus(String date);

}
