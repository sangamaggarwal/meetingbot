package com.bot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bot.dao.UserDAO;
import com.bot.dao.impl.WorkDetailDAOImpl;
import com.bot.dto.AudioData;
import com.bot.entity.User;
import com.bot.entity.WorkDetail;
import com.bot.json.pojo.Result;
import com.bot.restclient.HttpClient;
import com.bot.util.ConverterUtil;
import com.bot.util.PushDataToNLP;

@Service
public class GoogleService {

	private HttpClient HttpClient = new HttpClient();
	
	@Autowired
	private ConverterUtil converterUtil;
	
	@Autowired
	private PushDataToNLP  pushDataToNLP;
	 
	@Autowired
	private WorkDetailDAOImpl workDetailDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	public String getTextData(AudioData audioData) {
		User user = converterUtil.convertAudioDataToUser(audioData);
		List<User> list = new ArrayList<>(1);
		list.add(user);
		String result = null;
		String data = "{ \"config\": {  \"languageCode\": \"en-IN\" }, \"audio\": {  \"content\": \""+ audioData.getAudioData()+"\" }} ";
		Result obj = HttpClient.getGoogleData(data);
		if(null!=obj && null!=obj.getResults() && null!=obj.getResults().get(0).getAlternatives() && null!=obj.getResults().get(0).getAlternatives().get(0).getTranscript()) {
			result = obj.getResults().get(0).getAlternatives().get(0).getTranscript();
		}
		WorkDetail workDetail = converterUtil.convertAudioDataToWorkDetail(audioData, result);
		List<WorkDetail> details = new ArrayList<>(1);
		details.add(workDetail);
		userDAO.saveUser(list);
		workDetailDAO.saveWorkDetail(details);
        //PushDataToNLP  pushDataToNLP = new PushDataToNLP(details);
        pushDataToNLP.setList(details);
        pushDataToNLP.start();
		return result;
	}
}
