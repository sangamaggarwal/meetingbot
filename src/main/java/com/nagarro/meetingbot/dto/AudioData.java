package com.nagarro.meetingbot.dto;


import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"Email",
	"UserId",
	"UserName",
	"Question",
	"AudioData",
	"MeetingId"
})
public class AudioData {

@JsonProperty("Email")
private String email;

@JsonProperty("UserId")
private String userId;

@JsonProperty("UserName")
private String userName;

@JsonProperty("AudioData")
private String audioData;

@JsonProperty("MeetingId")
private String meetingId;

@JsonProperty("Question")
private String question;

@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("Email")
public String getEmail() {
	return email;
}

@JsonProperty("Email")
public void setEmail(String email) {
	this.email = email;
}

@JsonProperty("UserId")
public String getUserId() {
	return userId;
}

@JsonProperty("UserId")
public void setUserId(String userId) {
	this.userId = userId;
}

@JsonProperty("UserName")
public String getUserName() {
	return userName;
}

@JsonProperty("UserName")
public void setUserName(String userName) {
	this.userName = userName;
}

@JsonProperty("MeetingId")
public String getMeetingId() {
	return meetingId;
}

@JsonProperty("MeetingId")
public void setMeetingId(String meetingId) {
	this.meetingId = meetingId;
}

@JsonProperty("AudioData")
public String getAudioData() {
return audioData;
}

@JsonProperty("AudioData")
public void setAudioData(String audioData) {
this.audioData = audioData;
}

@JsonProperty("Question")
public String getQuestion() {
return question;
}

@JsonProperty("Question")
public void setQuestion(String Question) {
this.question = Question;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

@Override
public String toString() {
	return "AudioData [email=" + email + ", userId=" + userId + ", userName=" + userName + ", audioData=" + audioData
			+ ", meetingId=" + meetingId + ", question=" + question + ", additionalProperties=" + additionalProperties
			+ "]";
}


}
