package com.nagarro.meetingbot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.nagarro.meetingbot.dto.AudioData;
import com.nagarro.meetingbot.json.pojo.google.Result;
import com.nagarro.meetingbot.restclient.HttpClient;

@Service
public class GoogleService {

	private HttpClient httpClient = new HttpClient();

	private static final Logger logger = LoggerFactory.getLogger(GoogleService.class);
	
	public String getTextData(final AudioData audioData) {
		String speechText = null;
		if(null!=audioData) {
			logger.info("Audio Data recieved from Client.");
			Result googleSpeechResult = httpClient.getGoogleData(generateFLACJsonForGoogleSpeech(audioData.getAudioData()));
			if(null!=googleSpeechResult && null!=googleSpeechResult.getResults() && null!=googleSpeechResult.getResults().get(0).getAlternatives() && null!=googleSpeechResult.getResults().get(0).getAlternatives().get(0).getTranscript()) {
				speechText = googleSpeechResult.getResults().get(0).getAlternatives().get(0).getTranscript();
				logger.info("Google Speech return the result : {}", speechText);
			} else {
				logger.info("Google speech return NULL for provided Audio Data.");
			}
		} else {
			logger.debug("Audio Data not recieved from Client.");
		}
			return speechText;
	}

	private String generateFLACJsonForGoogleSpeech(String audioData) {
		String jsonData = null;
		if(null!=audioData && !"".equals(audioData)) {
			logger.info("Generating FLAC encoding JSON for Google Speech.");
			jsonData = "{ \"config\": {  \"encoding\": \"FLAC\",\"languageCode\": \"en-IN\" }, \"audio\": {  \"content\": \""+ audioData+"\" }} ";
		}
		return jsonData;
	}
}