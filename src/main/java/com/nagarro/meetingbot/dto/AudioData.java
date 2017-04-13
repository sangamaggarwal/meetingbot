package com.nagarro.meetingbot.dto;


import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"AudioData",
})
public class AudioData {

@JsonProperty("AudioData")
private String audioData;

@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("AudioData")
public String getAudioData() {
return audioData;
}

@JsonProperty("AudioData")
public void setAudioData(String audioData) {
this.audioData = audioData;
}

@Override
public String toString() {
	return "AudioData [audioData=" + audioData+ ", additionalProperties=" + additionalProperties + "]";
}


}
	