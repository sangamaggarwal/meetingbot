package com.nagarro.meetingbot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nagarro.meetingbot.entity.NLPDetail;

@Repository
public interface NLPDetailRepository extends CrudRepository<NLPDetail, Integer>{

	public Iterable<NLPDetail> findByMeetingId(@Param("meetingId") String meetingId);
	
}
