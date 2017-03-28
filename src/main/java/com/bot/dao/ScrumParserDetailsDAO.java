package com.bot.dao;

import java.util.List;

import com.bot.dto.nlp.NLPData;
import com.bot.entity.NlpDetails;

public interface ScrumParserDetailsDAO {
    
	List<NlpDetails> getScrumDetailsFor(String meetingId);
    boolean saveSrcumDetails(NLPData nlpData,String userId,String meetingId);

}
