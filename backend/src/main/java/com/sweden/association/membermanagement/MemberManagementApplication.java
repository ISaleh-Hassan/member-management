package com.sweden.association.membermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.sweden.association.membermanagement..repository")
public class MemberManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemberManagementApplication.class, args);
	}
}
