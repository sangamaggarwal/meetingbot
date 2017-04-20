package com.nagarro.meetingbot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nagarro.meetingbot.entity.WorkDetail;
import com.nagarro.meetingbot.json.pojo.nlp.NLPData;
import com.nagarro.meetingbot.restclient.HttpClient;
import com.nagarro.meetingbot.service.NLPDetailService;

public class PushDataToNLP extends Thread {

	private WorkDetail workDetail = null;;
	
	private HttpClient httpClient = new HttpClient();
	
	private NLPDetailService detailService;
	
	private static final Logger logger = LoggerFactory.getLogger(PushDataToNLP.class);
	
	public PushDataToNLP(WorkDetail workDetail, NLPDetailService nlpDetailService) {
		this.workDetail= workDetail;
		this.detailService = nlpDetailService;
	}
	
    @Override
    public void run() {
    	if(null!=workDetail) {
    		NLPData nlpData = null;
    		if(workDetail.getQues().equals(Question.any_issues.name())) {
    			logger.info("Any issue question found in work detail. Forwarding request to NLP");
    			nlpData = httpClient.getAnyIssueNLPData(workDetail.getAnswer());
    		} else {
    			logger.info("Pending/Completed question found in work detail. Forwarding request to NLP");
    			nlpData = httpClient.getNLPData(workDetail.getAnswer());
    		}
    		
    		if(null!=nlpData) {
    			logger.info("NLP data found. Hence saving into DB");
    			detailService.save(workDetail.getAnswer(), nlpData, workDetail.getUserId(), workDetail.getMeetingId());
    		} else {
    			logger.info("NLP data not  found.");
    		}
    	}
    }

}
