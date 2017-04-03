package com.bot.restclient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bot.dto.nlp.NLPData;
import com.bot.json.pojo.Result;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpClient {

    public NLPData getNLPData(String message) {
    	HttpURLConnection con = null;
    	String requestUrl = null;
    	NLPData nlpData = null;
   		requestUrl = "https://api.wit.ai/message?v=" + formatDateToStringAWSDB(new Date()) + "&q=" + message;
         try {
    	        URL preparedRequestUrl = new URL(requestUrl.replaceAll(" ", "%20"));
		   		con = (HttpURLConnection) preparedRequestUrl.openConnection();
		   		con.setRequestMethod("GET");
		   		con.setRequestProperty("Authorization", "Bearer P7H2FXHUGVTB2RZMCCML6TNGTH7NTLVC");
		   		InputStream inputStream = con.getInputStream();
		   		com.fasterxml.jackson.databind.ObjectMapper mapper = new ObjectMapper();
		   		String str = getStringFromInputStream(inputStream);
		   		nlpData = mapper.readValue(str, NLPData.class);
        } catch(Exception e){
        	System.out.println(e);
        } finally {
        	if(null != con) {
        		con.disconnect();
        	}
        }
         return nlpData;
    }
    
    public Result getGoogleData(String message) {
    	HttpURLConnection con = null;
    	String requestUrl = null;
    	Result obj = null;
   		requestUrl = "https://speech.googleapis.com/v1beta1/speech:syncrecognize?fields=results&key=AIzaSyBbr7C0GCiUfFOt1GiuY5wtf8QepgYuvXw";
         try {
    	        URL preparedRequestUrl = new URL(requestUrl.replaceAll(" ", "%20"));
		   		con = (HttpURLConnection) preparedRequestUrl.openConnection();
		   		con.setRequestMethod("POST");
		   		con.setRequestProperty("Content-Type","application/json");
		   		con.setDoOutput(true);
		   		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	            wr.writeBytes(message);
	            wr.flush();
	            wr.close();
	            String responseStatus = con.getResponseMessage();
	            System.out.println(responseStatus);
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
	            System.out.println("response:" + response.toString());
        } catch(Exception e){
        	System.out.println(e);
        } finally {
        	if(null != con) {
        		con.disconnect();
        	}
        }
		return obj;
    }
    
    public NLPData getAnyIssueNLPData(String message) {
    	HttpURLConnection con = null;
    	String requestUrl = null;
    	NLPData nlpData = null;
   		requestUrl = "https://api.wit.ai/message?v=" + formatDateToStringAWSDB(new Date()) + "&q=" + message;
         try {
    	        URL preparedRequestUrl = new URL(requestUrl.replaceAll(" ", "%20"));
		   		con = (HttpURLConnection) preparedRequestUrl.openConnection();
		   		con.setRequestMethod("GET");
		   		con.setRequestProperty("Authorization", "Bearer Q32IUWWCMNCEXOJBANNJN4EPD7474NUZ");
		   		InputStream inputStream = con.getInputStream();
		   		com.fasterxml.jackson.databind.ObjectMapper mapper = new ObjectMapper();
		   		String str = getStringFromInputStream(inputStream);
		   		nlpData = mapper.readValue(str, NLPData.class);
        } catch(Exception e){
        	System.out.println(e);
        } finally {
        	if(null != con) {
        		con.disconnect();
        	}
        }
         return nlpData;
    }
    
    public static String formatDateToStringAWSDB(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateStr = format.format(date);
		return dateStr;
	}
    
    private String getStringFromInputStream(InputStream is) {

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
