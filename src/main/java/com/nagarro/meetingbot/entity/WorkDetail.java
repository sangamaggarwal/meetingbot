package com.nagarro.meetingbot.entity;

public class WorkDetail {
	private String userId;
	private String meetingId;
	private Question ques;
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
	public Question getQues() {
		return ques;
	}
	public void setQues(Question ques) {
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
