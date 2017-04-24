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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.nagarro.meetingbot.util.QuestionEnum;
import com.nagarro.meetingbot.util.StatusEnum;
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
	
	private static final Logger logger = LoggerFactory.getLogger(BotController.class);
	
	@RequestMapping(value = "bot/meeting/{meetingId}/finished", method=RequestMethod.PUT)
	public ResponseEntity<String> meetingFinished(@PathVariable String meetingId){

		try{
			logger.info("Calling bot/meeting/{}/finished", meetingId);
			List<NLPDetail> nlpdetailList =  detailService.getAllNLPDetailsFor(meetingId, StatusEnum.SAVED.name());

			if(null!=nlpdetailList && 0<nlpdetailList.size()) {
				logger.info("NLP data found for this meeting in DB");
				String msg = getMOMData(nlpdetailList);
				
				if(!StringUtils.isEmpty(msg)) {
					Set<Address> addressSet = new HashSet<Address>();
					for(NLPDetail nlpObj : nlpdetailList) {
						addressSet.add(InternetAddress.parse(nlpObj.getUserId())[0]);
					}
					sendMail(msg, addressSet.toArray(new Address[addressSet.size()]));
					detailService.updateStatusFor(StatusEnum.PROCESSED.name(),  nlpdetailList.stream().filter(e-> e.getId()!=null).map(e->e.getId()).collect(Collectors.toList()));
				} else {
					logger.warn("Email Body message not generated");
				}
			} else {
				logger.warn("NLP data not  found in Database for this meeting ID");
			}
			
		}
		catch (IllegalArgumentException e) {
			logger.debug("IllegalArgumentException: {}", e.getMessage());
		}catch(Exception e){
			logger.debug("Exception: {}", e.getMessage());
		}
		return new ResponseEntity<String>("{\"Status\":\"OK\"}", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/bot/meeting/{meetingId}/user/{userId}/question/{questionId}/response", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<String> getAudio(@PathVariable String meetingId,@PathVariable String userId,@PathVariable String questionId,@RequestBody AudioData audioData){

		try{
			logger.info("Calling /bot/meeting/{}/user/{}/question/{}/response", meetingId, userId, questionId);
			String answerScript = googleService.getTextData(audioData);
			
			if(!StringUtils.isEmpty(answerScript)) {
				logger.info("Create Work Detail object");
				WorkDetail workDetail = new WorkDetail();
				workDetail.setUserId(userId);
				workDetail.setMeetingId(meetingId);
				workDetail.setQues(QuestionEnum.find(questionId).name());
				workDetail.setAnswer(answerScript);
				workDetailService.save(workDetail);
				logger.info("Saving workDetail object in DB");
				
				PushDataToNLP pushDataToNLP = new PushDataToNLP(workDetail, detailService);
				pushDataToNLP.start();
			}
		}
		catch (IllegalArgumentException e) {
			logger.debug("IllegalArgumentException: {}", e.getMessage());
		}catch(Exception e){
			logger.debug("Exception: {}", e.getMessage());
		}
		return new ResponseEntity<String>("{\"Status\":\"OK\"}", HttpStatus.OK);
	}

	public void sendMail(String msg, Address[] addresses) {
		final String username = "meetingbotnagarro@gmail.com";
		final String password = "MeetingBot@1";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			javax.mail.Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("no-reply@nagarromeetingbot.com"));
			message.setRecipients(javax.mail.Message.RecipientType.TO, addresses);
			message.setSubject("MOM");
			message.setText(msg);
			Transport.send(message);
			logger.info("Mail Successfully Sent.");
		} catch (MessagingException e) {
			logger.debug("MessagingException: {}", e.getMessage());
		}
	}

	public String getMOMData(List<NLPDetail> nlpdetailList){
        StringBuilder sb = new StringBuilder("Hi Team,\n\n");
        Set<String> distinctUsers= nlpdetailList.stream().map(e->e.getUserId()).collect(Collectors.toSet());
        for(String user :distinctUsers){
            sb.append("\n"+ StringUtil.getAttendeeNameFromEmail(user) +"\n");
            List<NLPDetail> userNlpDetail = nlpdetailList.stream().filter(e-> (e.getUserId().equals(user))).collect(Collectors.toList());
            List<String> pending= userNlpDetail.stream().filter(e-> e.getPending()!=null).map(e->e.getPending()).collect(Collectors.toList());
            List<String> completed = userNlpDetail.stream().filter(e-> e.getCompleted()!=null).map(e->e.getCompleted()).collect(Collectors.toList());
            if(null!=pending && 0<pending.size()) {
            		sb.append("\n\t Pending : " +pending.toString().substring(1, pending.toString().length()-1));	
            }
            if(null!=completed && 0<completed.size()) {
            	sb.append("\n\t Completed : "+ completed.toString().substring(1, completed.toString().length()-1));
            }
            
            List<NLPDetail> issueNlpDetails = userNlpDetail.stream().filter(e -> (e.getIssueType()!=null && e.getIssueType().trim().equals("issue"))).collect(Collectors.toList());
            if(null!=issueNlpDetails && issueNlpDetails.size()>0){
                List<String> issues = issueNlpDetails.stream().map(e -> e.getComments()).collect(Collectors.toList());
                sb.append("\n\t Issues : " + issues.toString().substring(1, issues.toString().length()-1));
                
            }
            List<NLPDetail> commentsNlpDetails = userNlpDetail.stream().filter(e -> (e.getIssueType()!=null && e.getIssueType().trim().equals("risk"))).collect(Collectors.toList());
            if(null!=commentsNlpDetails && commentsNlpDetails.size()>0){
                List<String> risk = commentsNlpDetails.stream().map(e -> e.getComments()).collect(Collectors.toList());
                sb.append("\n\t Risk : " + risk.toString().substring(1, risk.toString().length()-1) );
            }
            List<NLPDetail> riskNlpDetails = userNlpDetail.stream().filter(e -> (e.getIssueType()!=null && e.getIssueType().trim().equals("comment"))).collect(Collectors.toList());
            if(null!=riskNlpDetails && riskNlpDetails.size()>0){
                List<String> comments = riskNlpDetails.stream().map(e -> e.getComments()).collect(Collectors.toList());
                sb.append("\n\t Comment : " + comments.toString().substring(1, comments.toString().length()-1));
            }
            List<NLPDetail> otherNlpDetails = userNlpDetail.stream().filter(e -> (e.getIssueType()!=null && e.getIssueType().trim().equals("others"))).collect(Collectors.toList());
            if(null!=otherNlpDetails && otherNlpDetails.size()>0){
                List<String> others = otherNlpDetails.stream().map(e -> e.getComments()).collect(Collectors.toList());
                sb.append("\n\t Others : " + others.toString().substring(1, others.toString().length()-1));
            }
            sb.append("\n");
        }
        sb.append("\nThanks,\nMeeting BOT");
        return sb.toString();
    }

}
