package com.nagarro.meetingbot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nagarro.meetingbot.entity.NLPDetail;

@Repository
public interface NLPDetailRepository extends CrudRepository<NLPDetail, Integer>{

	public Iterable<NLPDetail> findByMeetingId(@Param("meetingId") String meetingId);
	
	public Iterable<NLPDetail> findByMeetingIdAndStatus(@Param("meetingId") String meetingId, @Param("status") String status);

	@Modifying
	@Transactional
	@Query("update NLPDetail ear set ear.status = :status where ear.id IN :id")
	public int updatsStatusFor(@Param("status") String status, @Param("id") List<Integer> id);
	
}
