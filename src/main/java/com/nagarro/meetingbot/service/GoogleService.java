package com.nagarro.meetingbot.service;

import org.springframework.stereotype.Service;
import com.nagarro.meetingbot.dto.AudioData;
import com.nagarro.meetingbot.json.pojo.google.Result;
import com.nagarro.meetingbot.restclient.HttpClient;

@Service
public class GoogleService {

	private HttpClient HttpClient = new HttpClient();

	public String getTextData(final AudioData audioData) {
		String text = null;
		if(null!=audioData) {
			Result result = HttpClient.getGoogleData(generateFLACJsonForGoogleSpeech(audioData.getAudioData()));
			if(null!=result && null!=result.getResults() && null!=result.getResults().get(0).getAlternatives() && null!=result.getResults().get(0).getAlternatives().get(0).getTranscript()) {
				text = result.getResults().get(0).getAlternatives().get(0).getTranscript();
			}
		}
			return text;
	}

	private String generateFLACJsonForGoogleSpeech(String audioData) {
		String data = null;
		if(null!=audioData && !"".equals(audioData)) {
			data = "{ \"config\": {  \"encoding\": \"FLAC\",\"languageCode\": \"en-IN\" }, \"audio\": {  \"content\": \""+ audioData+"\" }} ";
		}
		return data;
	}
}
