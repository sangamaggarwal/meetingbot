package com.nagarro.meetingbot.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nagarro.meetingbot.entity.WorkDetail;
import com.nagarro.meetingbot.repository.WorkDetailRepository;

@Service
public class WorkDetailService {

	@Autowired
	private WorkDetailRepository detailRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(WorkDetailService.class);
	
	public List<WorkDetail> getAllNLPDetails() {
		List<WorkDetail> workDetailList = new ArrayList<WorkDetail>();
		detailRepository.findAll().forEach(workDetailList::add);
		logger.info("Items found. Count : {}", workDetailList.size());
		return workDetailList;
	}
	
	public WorkDetail save(WorkDetail detail) {
		WorkDetail persistWorkDetail = detailRepository.save(detail);
		logger.info("Item saved in DB.");
		return persistWorkDetail;
	}
}
