package com.bot.entity;

public enum Question {
	
	yesterday_task("yesterdaysTask"),today_task("todaysTask"),any_issues("anyIssues");
	  private final String text;

    private Question(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
	
}
