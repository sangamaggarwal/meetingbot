package com.nagarro.meetingbot.entity;

public enum Question {

	yesterday_task("YT"),
	today_task("TT"),
	any_issues("XX");

	private final String text;

	public String getText() {
		return text;
	}

	private Question(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
	
	public static Question find(String name) {
	    for (Question question : Question.values()) {
	        if (question.getText().equalsIgnoreCase(name)) {
	            return question;
	        }
	    }
	    return null;
	}

}
