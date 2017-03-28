package com.bot.entity;

import java.io.Serializable;

public class ScrumParserDetails implements Serializable{
    
    /**
	 * Serial version UUID
	 */
	private static final long serialVersionUID = -2422875148803653469L;
	int meetingId;
    
    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }
}
