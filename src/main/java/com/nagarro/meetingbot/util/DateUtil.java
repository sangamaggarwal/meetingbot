package com.nagarro.meetingbot.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static String formatDateToStringAWSDB(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateStr = format.format(date);
		return dateStr;
	}
	
}
