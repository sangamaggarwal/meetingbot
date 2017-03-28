package com.bot.dao;

import java.util.List;

import com.bot.entity.WorkDetail;

public interface WorkDetailDAO {
    
  public boolean saveWorkDetail(List<WorkDetail> dailyStatus);
    
    public List<WorkDetail> readWorkDetail(String date);

}
