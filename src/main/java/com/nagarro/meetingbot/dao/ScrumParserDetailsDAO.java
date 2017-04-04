package com.nagarro.meetingbot.dao;

import java.util.List;

import com.nagarro.meetingbot.dto.nlp.NLPData;
import com.nagarro.meetingbot.entity.NlpDetails;

public interface ScrumParserDetailsDAO {
    
	List<NlpDetails> getScrumDetailsFor(String meetingId);
    boolean saveSrcumDetails(NLPData nlpData,String userId,String meetingId);

}
