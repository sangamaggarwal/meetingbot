package com.nagarro.meetingbot.restclient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarro.meetingbot.json.pojo.google.Result;
import com.nagarro.meetingbot.json.pojo.nlp.NLPData;
import com.nagarro.meetingbot.util.DateUtil;
import com.nagarro.meetingbot.util.StringUtil;

public class HttpClient {

	private static final Logger logger = LoggerFactory.getLogger(HttpClient.class);
	
    public Result getGoogleData(String message) {
    	HttpURLConnection con = null;
    	String requestUrl = null;
    	Result obj = null;
   		requestUrl = "https://speech.googleapis.com/v1beta1/speech:syncrecognize?fields=results&key=AIzaSyBbr7C0GCiUfFOt1GiuY5wtf8QepgYuvXw";
         try {
        	 	logger.info("Requesting Google Speech API.");
    	        URL preparedRequestUrl = new URL(requestUrl.replaceAll(" ", "%20"));
		   		con = (HttpURLConnection) preparedRequestUrl.openConnection();
		   		con.setRequestMethod("POST");
		   		con.setRequestProperty("Content-Type","application/json");
		   		con.setDoOutput(true);
		   		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	            wr.writeBytes(message);
	            wr.flush();
	            wr.close();
	            BufferedReader in = new BufferedReader(new InputStreamReader(
	                    con.getInputStream()));
	            String inputLine;
	            StringBuffer response = new StringBuffer();
	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }
	            in.close();
	            ObjectMapper mapper = new ObjectMapper();
	            obj = mapper.readValue(response.toString(), Result.class);
	            logger.info("result : {}", response.toString());
        } catch(Exception e){
        	logger.debug("Exception : {}", e.getMessage());
        } finally {
        	if(null != con) {
        		con.disconnect();
        	}
        }
		return obj;
    }
    
    public NLPData getNLPData(String message) {
    	HttpURLConnection con = null;
    	String requestUrl = null;
    	NLPData nlpData = null;
   		requestUrl = "https://api.wit.ai/message?v=" + DateUtil.formatDateToStringAWSDB(new Date()) + "&q=" + message;
         try {
        	 logger.info("Requesting NLP for pending/completed issues.");
    	     URL preparedRequestUrl = new URL(requestUrl.replaceAll(" ", "%20"));
		   	 con = (HttpURLConnection) preparedRequestUrl.openConnection();
		   	 con.setRequestMethod("GET");
		   	 con.setRequestProperty("Authorization", "Bearer P7H2FXHUGVTB2RZMCCML6TNGTH7NTLVC");
		   	 InputStream inputStream = con.getInputStream();
		   	 ObjectMapper mapper = new ObjectMapper();
		   	 String str = StringUtil.getStringFromInputStream(inputStream);
		   	 if(!StringUtils.isEmpty(str)) {
		   		nlpData = mapper.readValue(str, NLPData.class);
		   	 }
		   	 logger.info("Result Received : {}", nlpData);
        } catch(Exception e){
        	logger.debug("Eception : {}", e.getMessage());
        } finally {
        	if(null != con) {
        		con.disconnect();
        	}
        }
         return nlpData;
    }
    
    public NLPData getAnyIssueNLPData(String message) {
    	HttpURLConnection con = null;
    	String requestUrl = null;
    	NLPData nlpData = null;
   		requestUrl = "https://api.wit.ai/message?v=" + DateUtil.formatDateToStringAWSDB(new Date()) + "&q=" + message;
         try {
        	 logger.info("Requesting NLP for any issues.");
        	 URL preparedRequestUrl = new URL(requestUrl.replaceAll(" ", "%20"));
		   	 con = (HttpURLConnection) preparedRequestUrl.openConnection();
		   	 con.setRequestMethod("GET");
		   	 con.setRequestProperty("Authorization", "Bearer Q32IUWWCMNCEXOJBANNJN4EPD7474NUZ");
		   	 InputStream inputStream = con.getInputStream();
		   	 ObjectMapper mapper = new ObjectMapper();
		   	 String str = StringUtil.getStringFromInputStream(inputStream);
		   	 if(!StringUtils.isEmpty(str)) {
		   	 	nlpData = mapper.readValue(str, NLPData.class);
		   	 }
		   	 logger.info("Result Received : {}", nlpData);
        } catch(Exception e){
        	logger.debug("Eception : {}", e.getMessage());
        } finally {
        	if(null != con) {
        		con.disconnect();
        	}
        }
         return nlpData;
    }
}
