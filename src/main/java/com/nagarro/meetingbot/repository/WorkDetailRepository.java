package com.nagarro.meetingbot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.meetingbot.entity.WorkDetail;

@Repository
public interface WorkDetailRepository extends CrudRepository<WorkDetail, Integer>{

	
}
