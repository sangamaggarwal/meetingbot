package com.nagarro.meetingbot.util;

import java.util.ArrayList;
import java.util.List;

import com.nagarro.meetingbot.entity.WorkDetail;
import com.nagarro.meetingbot.json.pojo.nlp.NLPData;
import com.nagarro.meetingbot.restclient.HttpClient;
import com.nagarro.meetingbot.service.NLPDetailService;

public class PushDataToNLP extends Thread {

	List<WorkDetail> list = new ArrayList<WorkDetail>();;
	
	private HttpClient httpClient = new HttpClient();
	
	private NLPDetailService detailService;
	
	public void setList(List<WorkDetail> list) {
		this.list = list;
	}
	
	public PushDataToNLP(WorkDetail detail, NLPDetailService nlpDetailService) {
		this.list.add(detail);
		this.detailService = nlpDetailService;
	}
	
    @Override
    public void run() {
    	for(WorkDetail detail: list) {
    		NLPData nlpData = null;
    		if(detail.getQues().equals(Question.any_issues.name())) {
    			nlpData = httpClient.getAnyIssueNLPData(detail.getAnswer());
    		} else {
    			nlpData = httpClient.getNLPData(detail.getAnswer());
    		}
    		//Save result to DB
    		if(null!=nlpData) {
    			detailService.save(nlpData, list.get(0).getUserId(), list.get(0).getMeetingId());
    		}
    	}
    }

}
