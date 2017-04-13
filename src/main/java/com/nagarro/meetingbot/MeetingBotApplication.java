package com.nagarro.meetingbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ImportResource("classpath:applicationContext.xml")
@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
@EntityScan(basePackages = { "com.nagarro.meetingbot.entity" })
@EnableJpaRepositories(basePackages = { "com.nagarro.meetingbot.repository" })
public class MeetingBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetingBotApplication.class, args);
	}
}
