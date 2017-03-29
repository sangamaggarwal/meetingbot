package com.bot.rest.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bot.dao.UserDAO;
import com.bot.dao.WorkDetailDAO;
import com.bot.dao.impl.ScrumParserDetailsDAOImpl;
import com.bot.dao.impl.UserDAOImpl;
import com.bot.dao.impl.WorkDetailDAOImpl;
import com.bot.dto.AudioData;
import com.bot.dto.InputJson;
import com.bot.dto.Message;
import com.bot.dto.ScrumData;
import com.bot.entity.NlpDetails;
import com.bot.entity.Question;
import com.bot.entity.User;
import com.bot.entity.WorkDetail;
import com.bot.service.GoogleService;
import com.bot.util.PushDataToNLP;


@RestController
public class BotController {
    
    @RequestMapping(value = "botatwork/processData", method=RequestMethod.POST, consumes="application/json")
    public void saveMessagestoDB(@RequestBody ScrumData inputJsonWrapper){
        List<WorkDetail> list = new LinkedList<>();
        List<User> usersList = new LinkedList<>();
        WorkDetailDAO workDetailDao= new WorkDetailDAOImpl();
        UserDAO userDao= new UserDAOImpl();
        
        try{
                for(InputJson input :inputJsonWrapper.getInputJsons()){
                        String userName = input.getUserName();
                        String email = input.getEmail();
                        String userId = input.getUserId();
                        String meetingId = input.getMeetingId();
                        Message msg = input.getMessage();
                        User user = new User();
                        user.setEmail(email);
                        user.setUserId(userId);
                        user.setUserName(userName);
                        
                            WorkDetail yesterdaywork = new WorkDetail();
                            yesterdaywork.setUserId(userId);
                            yesterdaywork.setMeetingId(meetingId);
                            yesterdaywork.setQues(Question.yesterday_task);
                            yesterdaywork.setAnswer(msg.getYesterdaysTask());
                            list.add(yesterdaywork);
                            
                            WorkDetail todayWork = new WorkDetail();
                            todayWork.setUserId(userId);
                            todayWork.setMeetingId(meetingId);
                            todayWork.setQues(Question.today_task);
                            todayWork.setAnswer(msg.getTodaysTask());
                            list.add(todayWork);
                            
                            WorkDetail anyIssue = new WorkDetail();
                            anyIssue.setUserId(userId);
                            anyIssue.setMeetingId(meetingId);
                            anyIssue.setQues(Question.any_issues);
                            anyIssue.setAnswer(msg.getAnyIssues());
                            list.add(anyIssue);
                        
                        usersList.add(user);
                }
                userDao.saveUser(usersList);
                workDetailDao.saveWorkDetail(list);
                PushDataToNLP  pushDataToNLP = new PushDataToNLP(list);
                pushDataToNLP.start();
        }
        catch (IllegalArgumentException e) {
        	System.out.println(e);
        }catch(Exception e){
        	System.out.println(e);
        }
    }
    
    @RequestMapping(value = "botatwork/meetingover", method=RequestMethod.GET)
    public void saveMessagestoDB(@RequestParam("meetingId") String meetingId){
        
        try{
        	
        	//fetch nlp data from DB
        	
        	List<NlpDetails> nlpdetailList =  new ScrumParserDetailsDAOImpl().getScrumDetailsFor(meetingId);
        	//Update JIRA
        	String msg = getMOMData(nlpdetailList);
        	//send MOP
        	sendMail(msg);
        }
        catch (IllegalArgumentException e) {
        	System.out.println(e);
        }catch(Exception e){
        	System.out.println(e);
        }
    }
    
    @RequestMapping(value = "botatwork/sendaudio", method=RequestMethod.POST, consumes="application/json")
    public ResponseEntity<String> getAudio(@RequestBody AudioData audioData){
    	String data = "";
        try{
        	/*//hit google speech api
        	
        	//List<NlpDetails> nlpdetailList =  new ScrumParserDetailsDAOImpl().getScrumDetailsFor(audioData);
        	//Update JIRA
        	String msg = "";//getMOMData(nlpdetailList);
        	//send MOP
        	sendMail(msg);*/
        	
        	data = new GoogleService().getTextData(audioData);
        }
        catch (IllegalArgumentException e) {
        	System.out.println(e);
        }catch(Exception e){
        	System.out.println(e);
        }
        return new ResponseEntity<String>(data, HttpStatus.OK);
    }
    
    public void sendMail(String msg) {
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
			message.setRecipients(javax.mail.Message.RecipientType.TO,
				InternetAddress.parse("sangam.aggarwal@yahoo.com"));
			message.setSubject("MOM test");
			message.setText(msg);
			Transport.send(message);
			System.out.println("Done");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
    }
    
    public String getMOMData(List<NlpDetails> nlpdetailList){
        
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
        
    }
}
