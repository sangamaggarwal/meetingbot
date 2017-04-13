package com.nagarro.meetingbot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.meetingbot.entity.NLPDetail;
import com.nagarro.meetingbot.json.pojo.nlp.Entities;
import com.nagarro.meetingbot.json.pojo.nlp.NLPData;
import com.nagarro.meetingbot.repository.NLPDetailRepository;

@Service
public class NLPDetailService {

	@Autowired
	private NLPDetailRepository nlpDetailRepository;
	
	public List<NLPDetail> getAllNLPDetails() {
		List<NLPDetail> nlpList = new ArrayList<NLPDetail>();
		nlpDetailRepository.findAll().forEach(nlpList::add);
		return nlpList;
	}
	
	public NLPDetail save(NLPData nlpData,String userId, String meetingId) {
		NLPDetail detail = null;
		Entities entities = nlpData.getEntities();
        if(null != entities.getNumber()) {
        	for(int i=0;i<entities.getNumber().size();i++) {
        		detail = new NLPDetail();
        		detail.setUserId(userId);
        		detail.setText(nlpData.getText());
        		String status = null;
        		String projIn = null;
        		if(null!=entities.getStatus()) {
        			status = entities.getStatus().get(i>entities.getStatus().size()-1?0:i).getValue();
        		}
        		if(null!=entities.getProjectIdentifier()) {
        			projIn = entities.getProjectIdentifier().get(i>entities.getStatus().size()-1?0:i).getValue();
        		}
        		if(status.equalsIgnoreCase("IN PROGRESS"))  {
        			detail.setPending(projIn+"-"+entities.getNumber().get(i).getValue());
        		} else {
        			detail.setCompleted(projIn+"-"+entities.getNumber().get(i).getValue());
        		}
        		detail.setMeetingId(meetingId);
        	}
        } else {
        	
        	for(int i=0;i<entities.getMessageBody().size();i++) {
        		detail = new NLPDetail();
        		detail.setUserId(userId);
        		detail.setText(nlpData.getText());
        		String issueType = null;
        		if(null!=entities.getIssueType()) {
        			issueType = entities.getIssueType().get(i>entities.getIssueType().size()-1?0:i).getValue();
        		}
        		detail.setComments(entities.getMessageBody().get(i).getValue());
        		detail.setMeetingId(meetingId);
        		detail.setIssueType(issueType.trim());
        	}
        }
        nlpDetailRepository.save(detail);
		return detail;
	}
	
	public List<NLPDetail> getAllNLPDetailsFor(String meetingId) {
		List<NLPDetail> nlpList = new ArrayList<NLPDetail>();
		nlpDetailRepository.findByMeetingId(meetingId).forEach(nlpList::add);
		return nlpList;
	}
}
