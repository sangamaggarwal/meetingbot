package com.nagarro.meetingbot.dao;

import java.util.List;

import com.nagarro.meetingbot.entity.WorkDetail;

public interface WorkDetailDAO {
    
  public boolean saveWorkDetail(List<WorkDetail> dailyStatus);
    
    public List<WorkDetail> readWorkDetail(String date);

}
