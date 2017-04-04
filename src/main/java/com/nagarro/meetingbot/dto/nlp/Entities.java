package com.nagarro.meetingbot.dto.nlp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"status",
"projectIdentifier",
"number",
"issue_type",
"message_body",
"intent"
})
public class Entities {

@JsonProperty("status")
private List<Status> status = null;
@JsonProperty("projectIdentifier")
private List<ProjectIdentifier> projectIdentifier = null;
@JsonProperty("number")
private List<Number> number = null;
@JsonProperty("issue_type")
private List<IssueType> issueType = null;
@JsonProperty("message_body")
private List<MessageBody> messageBody = null;
@JsonProperty("intent")
private List<Intent> intent = null;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("status")
public List<Status> getStatus() {
return status;
}

@JsonProperty("status")
public void setStatus(List<Status> status) {
this.status = status;
}

@JsonProperty("projectIdentifier")
public List<ProjectIdentifier> getProjectIdentifier() {
return projectIdentifier;
}

@JsonProperty("projectIdentifier")
public void setProjectIdentifier(List<ProjectIdentifier> projectIdentifier) {
this.projectIdentifier = projectIdentifier;
}

@JsonProperty("message_body")
public List<MessageBody> getMessageBody() {
return messageBody;
}

@JsonProperty("message_body")
public void setMessageBody(List<MessageBody> messageBody) {
this.messageBody = messageBody;
}

@JsonProperty("issue_type")
public List<IssueType> getIssueType() {
return issueType;
}

@JsonProperty("issue_type")
public void setIssueType(List<IssueType> issueType) {
this.issueType = issueType;
}

@JsonProperty("number")
public List<Number> getNumber() {
return number;
}

@JsonProperty("number")
public void setNumber(List<Number> Number) {
this.number = Number;
}

@JsonProperty("intent")
public List<Intent> getIntent() {
return intent;
}

@JsonProperty("intent")
public void setIntent(List<Intent> intent) {
this.intent = intent;
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