package com.nagarro.meetingbot.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nagarro.meetingbot.entity.WorkDetail;
import com.nagarro.meetingbot.repository.WorkDetailRepository;

@Service
public class WorkDetailService {

	@Autowired
	private WorkDetailRepository detailRepository;
	
	public List<WorkDetail> getAllNLPDetails() {
		List<WorkDetail> workDetailList = new ArrayList<WorkDetail>();
		detailRepository.findAll().forEach(workDetailList::add);
		return workDetailList;
	}
	
	public WorkDetail save(WorkDetail detail) {
		WorkDetail persistWorkDetail = detailRepository.save(detail);
		return persistWorkDetail;
	}
}
