package com.nagarro.meetingbot.json.pojo.nlp;

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
"msg_id",
"_text",
"entities"
})
public class NLPData {

	@JsonProperty("msg_id")
	private String msgId;
	
	@JsonProperty("_text")
	private String text;
	
	@JsonProperty("entities")
	private Entities entities;
	
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	
	@JsonProperty("msg_id")
	public String getMsgId() {
		return msgId;
	}
	
	@JsonProperty("msg_id")
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	@JsonProperty("_text")
	public String getText() {
		return text;
	}
	
	@JsonProperty("_text")
	public void setText(String text) {
		this.text = text;
	}
	
	@JsonProperty("entities")
	public Entities getEntities() {
		return entities;
	}
	
	@JsonProperty("entities")
	public void setEntities(Entities entities) {
		this.entities = entities;
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
		return "NLPData [msgId=" + msgId + ", text=" + text + ", entities=" + entities + "]";
	}

}