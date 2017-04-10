package com.nagarro.meetingbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.meetingbot.dao.UserDAO;
import com.nagarro.meetingbot.dao.impl.WorkDetailDAOImpl;
import com.nagarro.meetingbot.dto.AudioData;
import com.nagarro.meetingbot.json.pojo.Result;
import com.nagarro.meetingbot.restclient.HttpClient;
import com.nagarro.meetingbot.util.ConverterUtil;

@Service
public class GoogleServices {

	private HttpClient HttpClient = new HttpClient();

	@Autowired
	private ConverterUtil converterUtil;

	@Autowired
	private WorkDetailDAOImpl workDetailDAO;

	@Autowired
	private UserDAO userDAO;

	public String getTextData(AudioData audioData) {
		//		User user = converterUtil.convertAudioDataToUser(audioData);
		//		List<User> list = new ArrayList<>(1);
		//		list.add(user);
		String result = null;
		String data = "{ \"config\": {  \"encoding\": \"FLAC\",\"languageCode\": \"en-IN\",\"sampleRate\": 44100 }, \"audio\": {  \"content\": \""+ audioData.getAudioData()+"\" }} ";
		Result obj = HttpClient.getGoogleData(data);
		if(null!=obj && null!=obj.getResults() && null!=obj.getResults().get(0).getAlternatives() && null!=obj.getResults().get(0).getAlternatives().get(0).getTranscript()) {
			result = obj.getResults().get(0).getAlternatives().get(0).getTranscript();
		}
		//		WorkDetail workDetail = converterUtil.convertAudioDataToWorkDetail(audioData, result);
		//		List<WorkDetail> details = new ArrayList<>(1);
		//		details.add(workDetail);
		//		userDAO.saveUser(list);
		//		workDetailDAO.saveWorkDetail(details);
		//        PushDataToNLP  pushDataToNLP = new PushDataToNLP(details);
		//        pushDataToNLP.setList(details);
		//        pushDataToNLP.start();
		return result;
	}
}
