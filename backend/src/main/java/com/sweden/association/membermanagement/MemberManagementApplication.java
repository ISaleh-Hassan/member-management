package com.sweden.association.membermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-${spring.profiles.active:prod}.properties")
public class MemberManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemberManagementApplication.class, args);
	}
}
