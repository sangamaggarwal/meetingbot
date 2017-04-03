package com.bot.util;

import org.springframework.stereotype.Component;

import com.bot.dto.AudioData;
import com.bot.entity.Question;
import com.bot.entity.User;
import com.bot.entity.WorkDetail;

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
		 WorkDetail yesterdaywork = new WorkDetail();
         yesterdaywork.setUserId(audioData.getUserId());
         yesterdaywork.setMeetingId(audioData.getMeetingId());
         yesterdaywork.setQues(Question.valueOf("yesterday_task"));
         yesterdaywork.setAnswer(answer);
         return yesterdaywork;
	}

	
}
