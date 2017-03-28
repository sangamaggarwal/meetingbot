package com.bot.service;

import com.bot.dto.AudioData;
import com.bot.restclient.HttpClient;

public class GoogleService {

	HttpClient HttpClient = new HttpClient();
	
	public String getTextData(AudioData audioData) {
		String result = null;
		String data = "{ \"config\": {  \"languageCode\": \"en-IN\" }, \"audio\": {  \"content\": \""+ audioData.getBase64String()+"\" }} ";
		HttpClient.getGoogleData(data);
		
		return result;
	}
}
