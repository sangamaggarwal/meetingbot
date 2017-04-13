package com.nagarro.meetingbot.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	private static final String EMAIL_USER_NAME_REGEX = "([a-zA-Z0-9][\\w\\-.]*[a-zA-Z0-9])@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public static final Pattern EMAIL_USER_NAME_REGEX_PATTERN = Pattern.compile(EMAIL_USER_NAME_REGEX);
	
	public static String getAttendeeNameFromEmail(String attendeeEmail) {
        String attendeeName = null;
        if (attendeeEmail != null) {
            Matcher emailUserNameMatcher = EMAIL_USER_NAME_REGEX_PATTERN.matcher(attendeeEmail);
            if (emailUserNameMatcher.find()) {
                String userName = emailUserNameMatcher.group(1);
                if (userName != null) {
                    StringTokenizer stringTokenizer = new StringTokenizer(userName, ".");
                    attendeeName = getNameForStringTokens(stringTokenizer);
                    if (attendeeName == null) {
                        stringTokenizer = new StringTokenizer(userName, "_");
                        attendeeName = getNameForStringTokens(stringTokenizer);
                    }
                }
            }
        }
        return attendeeName;
    }
private static String getNameForStringTokens(StringTokenizer stringTokenizer) {
        String attendeeName = null;
        if (stringTokenizer != null && stringTokenizer.countTokens() >= 2) {
            String firstName = stringTokenizer.nextToken();
            String lastName = stringTokenizer.nextToken();
            // First letter of firstname and lastname should be capital....
            firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
            lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
            attendeeName = firstName + " " + lastName;
        }

        return attendeeName;
    }
	
	public static String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
}
