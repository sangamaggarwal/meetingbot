package com.nagarro.meetingbot.dto.nlp;

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
"confidence",
"value",
"type"
})
public class MessageBody {

@JsonProperty("confidence")
private Double confidence;
@JsonProperty("value")
private String value;
@JsonProperty("type")
private String type;
@JsonProperty("suggested")
private Boolean suggested;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("confidence")
public Double getConfidence() {
return confidence;
}

@JsonProperty("confidence")
public void setConfidence(Double confidence) {
this.confidence = confidence;
}

@JsonProperty("value")
public String getValue() {
return value;
}

@JsonProperty("value")
public void setValue(String value) {
this.value = value;
}

@JsonProperty("type")
public String getType() {
return type;
}

@JsonProperty("type")
public void setType(String type) {
this.type = type;
}

@JsonProperty("suggested")
public Boolean getSuggested() {
return suggested;
}

@JsonProperty("suggested")
public void setSuggested(Boolean suggested) {
this.suggested = suggested;
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