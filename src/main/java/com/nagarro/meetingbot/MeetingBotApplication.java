package com.nagarro.meetingbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@ImportResource("classpath:applicationContext.xml")
@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class MeetingBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetingBotApplication.class, args);
	}
}
