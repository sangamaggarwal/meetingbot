
package com.nagarro.meetingbot.json.pojo.google;

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
    "transcript",
    "confidence"
})
public class Alternative {

    @JsonProperty("transcript")
    private String transcript;
    @JsonProperty("confidence")
    private Double confidence;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("transcript")
    public String getTranscript() {
        return transcript;
    }

    @JsonProperty("transcript")
    public void setTranscript(String transcript) {
        this.transcript = transcript;
    }

    @JsonProperty("confidence")
    public Double getConfidence() {
        return confidence;
    }

    @JsonProperty("confidence")
    public void setConfidence(Double confidence) {
        this.confidence = confidence;
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
