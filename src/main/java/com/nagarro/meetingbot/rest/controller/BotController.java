package com.nagarro.meetingbot.rest.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.meetingbot.dto.AudioData;
import com.nagarro.meetingbot.entity.NLPDetail;
import com.nagarro.meetingbot.entity.WorkDetail;
import com.nagarro.meetingbot.service.GoogleService;
import com.nagarro.meetingbot.service.NLPDetailService;
import com.nagarro.meetingbot.service.WorkDetailService;
import com.nagarro.meetingbot.util.PushDataToNLP;
import com.nagarro.meetingbot.util.Question;
import com.nagarro.meetingbot.util.StringUtil;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class BotController {

	@Autowired
	private GoogleService googleService;

	@Autowired
	private WorkDetailService workDetailService;
	
	@Autowired
	private NLPDetailService detailService;
	
	@RequestMapping(value = "bot/meeting/{meetingId}/finished", method=RequestMethod.PUT)
	public ResponseEntity<String> saveMessagestoDB(@PathVariable String meetingId){

		try{
			//fetch nlp data from DB
			List<NLPDetail> nlpdetailList =  detailService.getAllNLPDetailsFor(meetingId);

			//Generating mom data
			String msg = getMOMData(nlpdetailList);
			
			//send MOP
			Set<Address> set = new HashSet<Address>();
			if(null!=nlpdetailList) {
				for(NLPDetail nlpObj : nlpdetailList) {
					set.add(InternetAddress.parse(nlpObj.getUserId())[0]);
				}
			}
			sendMail(msg, set.toArray(new Address[set.size()]));
		}
		catch (IllegalArgumentException e) {
			System.out.println(e);
		}catch(Exception e){
			System.out.println(e);
		}
		return new ResponseEntity<String>("", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/bot/meeting/{meetingId}/user/{userId}/question/{questionId}/response", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<String> getAudio(@PathVariable String meetingId,@PathVariable String userId,@PathVariable String questionId,@RequestBody AudioData audioData){

		try{
			String answerScript = googleService.getTextData(audioData);
			answerScript = "I am working on PIL 1234";
			//Saving result to DB
			if(!StringUtils.isEmpty(answerScript)) {
				WorkDetail workDetail = new WorkDetail();
				workDetail.setUserId(userId);
				workDetail.setMeetingId(meetingId);
				workDetail.setQues(Question.find(questionId).name());
				workDetail.setAnswer(answerScript);
				workDetailService.save(workDetail);
				//Push data on NLP.
				PushDataToNLP pushDataToNLP = new PushDataToNLP(workDetail, detailService);
				pushDataToNLP.start();
			}
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ResponseEntity<String>("", HttpStatus.OK);
	}

	public void sendMail(String msg, Address[] addresses) {
		final String username = "sangamaggarwal1990@gmail.com";
		final String password = "gauravsharma";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			javax.mail.Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("no-reply@botatwork.com"));
			message.setRecipients(javax.mail.Message.RecipientType.TO, addresses);
			message.setSubject("MOM test");
			message.setText(msg);
			Transport.send(message);
			System.out.println("Done");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public String getMOMData(List<NLPDetail> nlpdetailList){
        StringBuilder sb = new StringBuilder();
        Set<String> distinctUsers= nlpdetailList.stream().map(e->e.getUserId()).collect(Collectors.toSet());
        for(String user :distinctUsers){
            sb.append("\n"+ StringUtil.getAttendeeNameFromEmail(user) +"\n");
            List<NLPDetail> userNlpDetail = nlpdetailList.stream().filter(e-> (e.getUserId().equals(user))).collect(Collectors.toList());
            List<String> pending= userNlpDetail.stream().filter(e-> e.getPending()!=null).map(e->e.getPending()).collect(Collectors.toList());
            List<String> completed = userNlpDetail.stream().filter(e-> e.getCompleted()!=null).map(e->e.getCompleted()).collect(Collectors.toList());
            if(null!=pending && 0<pending.size()) {
            		sb.append("\n Pending " +pending.toString().substring(1, pending.toString().length()-1));	
            }
            if(null!=completed && 0<completed.size()) {
            	sb.append("\n Completed "+ completed.toString().substring(1, completed.toString().length()-1));
            }
            
            List<NLPDetail> issueNlpDetails = userNlpDetail.stream().filter(e -> (e.getIssueType()!=null && e.getIssueType().trim().equals("issue"))).collect(Collectors.toList());
            if(null!=issueNlpDetails && issueNlpDetails.size()>0){
                List<String> issues = issueNlpDetails.stream().map(e -> e.getComments()).collect(Collectors.toList());
                sb.append("\n Issues " + issues.toString().substring(1, issues.toString().length()-1));
                
            }
            List<NLPDetail> commentsNlpDetails = userNlpDetail.stream().filter(e -> (e.getIssueType()!=null && e.getIssueType().trim().equals("risk"))).collect(Collectors.toList());
            if(null!=commentsNlpDetails && commentsNlpDetails.size()>0){
                List<String> comments = issueNlpDetails.stream().map(e -> e.getComments()).collect(Collectors.toList());
                sb.append("\n Comments " + comments.toString().substring(1, comments.toString().length()-1) );
            }
            List<NLPDetail> riskNlpDetails = userNlpDetail.stream().filter(e -> (e.getIssueType()!=null && e.getIssueType().trim().equals("comment"))).collect(Collectors.toList());
            if(null!=riskNlpDetails && riskNlpDetails.size()>0){
                List<String> risk = riskNlpDetails.stream().map(e -> e.getComments()).collect(Collectors.toList());
                sb.append("\n Risk " + risk.toString().substring(1, risk.toString().length()-1));
            }
        }
        return sb.toString();
    }

}
