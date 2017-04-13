package com.nagarro.meetingbot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "workdetail")
public class WorkDetail {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private String id;
	@Column(name = "userId")
	private String userId;
	@Column(name = "meetingId")
	private String meetingId;
	@Column(name = "ques")
	private String ques;
	@Column(name = "answer")
	private String answer;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}
	public String getQues() {
		return ques;
	}
	public void setQues(String ques) {
		this.ques = ques;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WorkDetail [userId=").append(userId).append(", meetingId=").append(meetingId)
		.append(", ques=").append(ques).append(", answer=").append(answer).append("]");
		return builder.toString();
	}
}
