package com.bot.util;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bot.dao.impl.ScrumParserDetailsDAOImpl;
import com.bot.dto.nlp.NLPData;
import com.bot.entity.Question;
import com.bot.entity.WorkDetail;
import com.bot.restclient.HttpClient;

@Component
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
