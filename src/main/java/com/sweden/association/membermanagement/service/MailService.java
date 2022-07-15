package com.sweden.association.membermanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.sweden.association.membermanagement.model.UserAccount;

@Service
public class MailService {

  @Autowired
  private JavaMailSender mailSender;

  protected void sendConfirmRegistration(UserAccount userAccount) throws MailException {
    String subject = "Registration Confirmation";
    String confirmationUrl = "http://localhost:8083/api/v1/user-accounts/regitrationConfirm?token="
        + userAccount.getVerificationToken();
    String message = "Click this link to verify your email address";
    SimpleMailMessage email = new SimpleMailMessage();
    email.setTo(userAccount.getEmail());
    email.setSubject(subject);
    email.setText(message + "\r\n" + confirmationUrl);
    mailSender.send(email);
  }

  public void sendGrantUserToAdmin(UserAccount userAccount) throws MailException {
    String subject = "Grant admin priviliges";
    String message = "The user " + userAccount.getUsername() + " has just been verified, grant him/her admin priviliges in the membermanagement application";
    SimpleMailMessage email = new SimpleMailMessage();
    email.setTo("carl.hillman@gmail.com");//here we set the mail of the admin who grants admin priviliges to users
    email.setSubject(subject);
    email.setText(message);
    mailSender.send(email);
  }
}