package com.nagarro.meetingbot.rest.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.meetingbot.dao.impl.ScrumParserDetailsDAOImpl;
import com.nagarro.meetingbot.dao.impl.WorkDetailDAOImpl;
import com.nagarro.meetingbot.dto.AudioData;
import com.nagarro.meetingbot.entity.NlpDetails;
import com.nagarro.meetingbot.entity.Question;
import com.nagarro.meetingbot.entity.WorkDetail;
import com.nagarro.meetingbot.service.GoogleServices;
import com.nagarro.meetingbot.util.PushDataToNLP;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class BotController {

	@Autowired
	private GoogleServices googleServices;

	@Autowired
	private WorkDetailDAOImpl workDetailDAO;

	@RequestMapping(value = "bot/meeting/{meetingId}/finished", method=RequestMethod.PUT)
	public ResponseEntity<String> saveMessagestoDB(@PathVariable String meetingId){

		try{

			//fetch nlp data from DB

			List<NlpDetails> nlpdetailList =  new ScrumParserDetailsDAOImpl().getScrumDetailsFor(meetingId);
		
			String msg = getMOMData(nlpdetailList);
			//send MOP
			Set<Address> set = new HashSet<Address>();
			if(null!=nlpdetailList) {
				for(NlpDetails nlpObj : nlpdetailList) {
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

			String answerScript = googleServices.getTextData(audioData);

			WorkDetail workDetail = new WorkDetail();
			workDetail.setUserId(userId);
			workDetail.setMeetingId(meetingId);
			workDetail.setQues(Question.find(questionId));
			workDetail.setAnswer(answerScript);

			List<WorkDetail> details = new ArrayList<>(1);
			details.add(workDetail);
			workDetailDAO.saveWorkDetail(details);

			PushDataToNLP pushDataToNLP = new PushDataToNLP();
			pushDataToNLP.setList(details);
			pushDataToNLP.start();

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

	/*public String getMOMData(List<NlpDetails> nlpdetailList){

		StringBuilder sb = new StringBuilder();
		Set<String> distinctUsers= nlpdetailList.stream().map(e->e.getUserId()).collect(Collectors.toSet());
		for(String user :distinctUsers){
			sb.append("\n"+user);
			List<NlpDetails> userNlpDetail = nlpdetailList.stream().filter(e-> !(e.getUserId().equals(user))).collect(Collectors.toList());
			List<String> pending= userNlpDetail.stream().map(e->e.getPending()).collect(Collectors.toList());
			List<String> completed = userNlpDetail.stream().map(e->e.getCompleted()).collect(Collectors.toList());
			sb.append("\n pending" +pending ).append("\n + completed"+ completed);
			List<NlpDetails> issueNotBlank =  userNlpDetail.stream().filter(e -> e.getIssueType().isEmpty()).collect(Collectors.toList());
			System.out.println(issueNotBlank);
		}

		return sb.toString();

	}*/
	
	public String getMOMData(List<NlpDetails> nlpdetailList){
        StringBuilder sb = new StringBuilder();
        Set<String> distinctUsers= nlpdetailList.stream().map(e->e.getUserId()).collect(Collectors.toSet());
        for(String user :distinctUsers){
            sb.append("\n"+user);
            List<NlpDetails> userNlpDetail = nlpdetailList.stream().filter(e-> (e.getUserId().equals(user))).collect(Collectors.toList());
            List<String> pending= userNlpDetail.stream().filter(e-> e.getPending()!=null).map(e->e.getPending()).collect(Collectors.toList());
            List<String> completed = userNlpDetail.stream().filter(e-> e.getCompleted()!=null).map(e->e.getCompleted()).collect(Collectors.toList());
            sb.append("\n Pending " +pending ).append("\n Completed "+ completed);
            
            List<NlpDetails> issueNlpDetails = userNlpDetail.stream().filter(e -> (e.getIssueType()!=null && e.getIssueType().trim().equals("issue"))).collect(Collectors.toList());
            if(issueNlpDetails.size()>0){
                List<String> issues = issueNlpDetails.stream().map(e -> e.getComments()).collect(Collectors.toList());
                sb.append("\n Issues " + issues );
                
            }
            List<NlpDetails> commentsNlpDetails = userNlpDetail.stream().filter(e -> (e.getIssueType()!=null && e.getIssueType().trim().equals("risk"))).collect(Collectors.toList());
            if(commentsNlpDetails.size()>0){
                List<String> comments = issueNlpDetails.stream().map(e -> e.getComments()).collect(Collectors.toList());
                sb.append("\n Comments " + comments );
            }
            List<NlpDetails> riskNlpDetails = userNlpDetail.stream().filter(e -> (e.getIssueType()!=null && e.getIssueType().trim().equals("comment"))).collect(Collectors.toList());
            if(riskNlpDetails.size()>0){
                List<String> risk = riskNlpDetails.stream().map(e -> e.getComments()).collect(Collectors.toList());
                sb.append("\n Risk " + risk );
            }
            
        }
        
        return sb.toString();
        
    }


}
