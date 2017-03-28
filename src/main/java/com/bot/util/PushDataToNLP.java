package com.bot.util;

import java.util.List;

import com.bot.dao.impl.ScrumParserDetailsDAOImpl;
import com.bot.dto.nlp.NLPData;
import com.bot.entity.Question;
import com.bot.entity.WorkDetail;
import com.bot.restclient.HttpClient;

public class PushDataToNLP extends Thread {

	List<WorkDetail> list = null;
	
	HttpClient HttpClient = new HttpClient();
	
	public PushDataToNLP(List<WorkDetail> list) {
		this.list = list;
	}
    @Override
    public void run() {
    	for(WorkDetail detail: list) {

    		NLPData nlpData = null;
    		if(detail.getQues().name().equals(Question.any_issues.name())) {
    			nlpData = HttpClient.getAnyIssueNLPData(detail.getAnswer());
    		} else {
    			nlpData = HttpClient.getNLPData(detail.getAnswer());
    		}
    		new ScrumParserDetailsDAOImpl().saveSrcumDetails(nlpData, list.get(0).getUserId(), list.get(0).getMeetingId());
    	}
    	
    }

}
