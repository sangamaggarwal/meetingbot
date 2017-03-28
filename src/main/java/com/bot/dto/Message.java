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
"yesterdaysTask",
"todaysTask",
"anyIssues"
})
public class Message {

@JsonProperty("yesterdaysTask")
private String yesterdaysTask;
@JsonProperty("todaysTask")
private String todaysTask;
@JsonProperty("anyIssues")
private String anyIssues;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("yesterdaysTask")
public String getYesterdaysTask() {
return yesterdaysTask;
}

@JsonProperty("yesterdaysTask")
public void setYesterdaysTask(String yesterdaysTask) {
this.yesterdaysTask = yesterdaysTask;
}

@JsonProperty("todaysTask")
public String getTodaysTask() {
return todaysTask;
}

@JsonProperty("todaysTask")
public void setTodaysTask(String todaysTask) {
this.todaysTask = todaysTask;
}

@JsonProperty("anyIssues")
public String getAnyIssues() {
return anyIssues;
}

@JsonProperty("anyIssues")
public void setAnyIssues(String anyIssues) {
this.anyIssues = anyIssues;
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
