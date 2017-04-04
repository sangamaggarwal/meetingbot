package com.nagarro.meetingbot.util;

import org.springframework.stereotype.Component;

import com.nagarro.meetingbot.dto.AudioData;
import com.nagarro.meetingbot.entity.Question;
import com.nagarro.meetingbot.entity.User;
import com.nagarro.meetingbot.entity.WorkDetail;

@Component
public class ConverterUtil {

	public User convertAudioDataToUser(AudioData audioData) {
		User user = new User();
        user.setEmail(audioData.getEmail());
        user.setUserId(audioData.getUserId());
        user.setUserName(audioData.getUserName());
        return user;
	}

	public WorkDetail convertAudioDataToWorkDetail(AudioData audioData,String answer) {
		 WorkDetail workDetail = new WorkDetail();
         workDetail.setUserId(audioData.getUserId());
         workDetail.setMeetingId(audioData.getMeetingId());
         workDetail.setQues(Question.valueOf(audioData.getQuestion()));
         workDetail.setAnswer(answer);
         return workDetail;
	}
	
}
