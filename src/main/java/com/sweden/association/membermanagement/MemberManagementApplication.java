package com.sweden.association.membermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MemberManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemberManagementApplication.class, args);
        System.out.println("The application is running on http://localhost:8083");
    }
}
