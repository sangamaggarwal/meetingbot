package com.nagarro.meetingbot.entity;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class BaseModel{
	
	@JsonProperty("error")
	private String error;
	@JsonIgnore
	private int httpResponseCode;
	
	public BaseModel() {
		
	}
	public String getError() {
		return error != null ? HttpStatus.valueOf(httpResponseCode).name():null;
	}
	public void setError(String error) {
		this.error = error;
	}
	public int getHttpResponseCode() {
		return httpResponseCode;
	}
	public void setHttpResponseCode(int httpResponseCode) {
		this.httpResponseCode = httpResponseCode;
	}
	

}
