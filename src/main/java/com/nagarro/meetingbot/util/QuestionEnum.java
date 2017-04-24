package com.nagarro.meetingbot.util;

public enum QuestionEnum {

	yesterday_task("YT"),
	today_task("TT"),
	any_issues("XX");

	private final String text;

	public String getText() {
		return text;
	}

	private QuestionEnum(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
	
	public static QuestionEnum find(String name) {
	    for (QuestionEnum question : QuestionEnum.values()) {
	        if (question.getText().equalsIgnoreCase(name)) {
	            return question;
	        }
	    }
	    return null;
	}

}
