package com.nagarro.meetingbot.util;

import java.util.List;

import com.nagarro.meetingbot.dao.impl.ScrumParserDetailsDAOImpl;
import com.nagarro.meetingbot.dto.nlp.NLPData;
import com.nagarro.meetingbot.entity.Question;
import com.nagarro.meetingbot.entity.WorkDetail;
import com.nagarro.meetingbot.restclient.HttpClient;

public class PushDataToNLP extends Thread {

	List<WorkDetail> list = null;
	
	private HttpClient httpClient = new HttpClient();
	
	public void setList(List<WorkDetail> list) {
		this.list = list;
	}
	
    @Override
    public void run() {
    	for(WorkDetail detail: list) {

    		NLPData nlpData = null;
    		if(detail.getQues().name().equals(Question.any_issues.name())) {
    			nlpData = httpClient.getAnyIssueNLPData(detail.getAnswer());
    		} else {
    			nlpData = httpClient.getNLPData(detail.getAnswer());
    		}
    		new ScrumParserDetailsDAOImpl().saveSrcumDetails(nlpData, list.get(0).getUserId(), list.get(0).getMeetingId());
    	}
    	
    }

}
