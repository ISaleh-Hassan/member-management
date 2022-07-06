package com.sweden.association.membermanagement.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.sweden.association.membermanagement.model.UserAccount;

@Service
public class MailService {

  @Autowired
  private MessageSource messages;

  @Autowired
  private JavaMailSender mailSender;

  protected void sendConfirmRegistration(UserAccount userAccount) throws MailException {
    // send token to database of the saved user account row
    String subject = "Registration Confirmation";
    String confirmationUrl = "http://localhost:8083/api/v1/user-accounts/regitrationConfirm?token="
        + userAccount.getVerificationToken();
    Locale currentLocale = new Locale("sv", "SE"); // can be replaced by Locale.getDefault() to handle virtual machine
                                                   // country
    String message = messages.getMessage("message.regSucc", null, currentLocale);

    SimpleMailMessage email = new SimpleMailMessage();
    email.setTo(userAccount.getEmail());
    email.setSubject(subject);
    email.setText(message + "\r\n" + "http://localhost:8083" + confirmationUrl);
    mailSender.send(email);
  }
}