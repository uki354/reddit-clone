package com.example.redditclone;

import com.example.redditclone.utility.DateUtility;
import com.ocpsoft.pretty.time.PrettyTime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

@SpringBootApplication
@EnableAsync
public class RedditCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedditCloneApplication.class, args);
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	PrettyTime prettyTime() {
		return new PrettyTime();
	}
	@Bean
	DateUtility dateUtility(){return new DateUtility();}



}



