package com.bot.service;

import com.bot.dto.AudioData;
import com.bot.json.pojo.Result;
import com.bot.restclient.HttpClient;

public class GoogleService {

	HttpClient HttpClient = new HttpClient();
	
	public String getTextData(AudioData audioData) {
		String result = null;
		String data = "{ \"config\": {  \"languageCode\": \"en-IN\" }, \"audio\": {  \"content\": \""+ audioData.getBase64String()+"\" }} ";
		Result obj = HttpClient.getGoogleData(data);
		if(null!=obj && null!=obj.getResults() && null!=obj.getResults().get(0).getAlternatives() && null!=obj.getResults().get(0).getAlternatives().get(0).getTranscript()) {
			result = obj.getResults().get(0).getAlternatives().get(0).getTranscript();
		}
		return result;
	}
}
