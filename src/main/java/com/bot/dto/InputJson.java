package com.bot.dto;


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
"email",
"userId",
"userName",
"message",
"meetingId"
})
public class InputJson {

@JsonProperty("email")
private String email;
@JsonProperty("userId")
private String userId;
@JsonProperty("userName")
private String userName;
@JsonProperty("message")
private Message message;
@JsonProperty("meetingId")
private String meetingId;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("email")
public String getEmail() {
return email;
}

@JsonProperty("email")
public void setEmail(String email) {
this.email = email;
}

@JsonProperty("userId")
public String getUserId() {
return userId;
}

@JsonProperty("userId")
public void setUserId(String userId) {
this.userId = userId;
}

@JsonProperty("userName")
public String getUserName() {
return userName;
}

@JsonProperty("userName")
public void setUserName(String userName) {
this.userName = userName;
}

@JsonProperty("message")
public Message getMessage() {
return message;
}

@JsonProperty("message")
public void setMessage(Message message) {
this.message = message;
}

@JsonProperty("meetingId")
public String getMeetingId() {
return meetingId;
}

@JsonProperty("meetingId")
public void setMeetingId(String meetingId) {
this.meetingId = meetingId;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}
