package com.sweden.association.membermanagement.controller;

import java.net.URI;
import java.util.Calendar;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.sweden.association.membermanagement.dto.MemberDto;
import com.sweden.association.membermanagement.model.RegisterUser;
import com.sweden.association.membermanagement.model.UserAccount;
import com.sweden.association.membermanagement.service.MailService;
import com.sweden.association.membermanagement.service.UserAccountService;
import com.sweden.association.membermanagement.utility.JwtResponse;
import com.sweden.association.membermanagement.validator.UserAccountValidator;

@CrossOrigin(origins = { "http://localhost:3000", "https://localhost:3000",
    "https://member-payments-management.herokuapp.com" })
@RestController
@RequestMapping("/api/v1")
public class UserAccountController {

  @Autowired
  private MessageSource messages;

  @Autowired
  private UserAccountService userAccountService;

  @Autowired
  private MailService mailService;

  @GetMapping("/user-accounts/login")
  public JwtResponse login(@RequestParam String userName, @RequestParam String password) {
    var response = new JwtResponse();
    try {
      response = userAccountService.login(userName, password);
      return response;
    } catch (Exception ex) {
      // return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      return null;
    }
  }

  @PostMapping(value = "/user-accounts/register", consumes = "application/json", produces = "application/json")
  public ResponseEntity<JwtResponse> register(
      @RequestBody RegisterUser registerUser) {
    try {
      var memberDto = new MemberDto();
      memberDto.setName(registerUser.name);
      memberDto.setMobileNumber(registerUser.mobileNumber);

      var userAccount = new UserAccount();
      userAccount.setEmail(registerUser.email);
      userAccount.setUserName(registerUser.username);
      userAccount.setPassword(registerUser.password);

      UserAccountValidator.validateUserAccount(userAccount);
      memberDto.setUserAccount(userAccount);
      var response = userAccountService.register(memberDto);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception ex) {
      JwtResponse jwtResponse = new JwtResponse();
      jwtResponse.setExceptionMessage(ex.getMessage());
      return new ResponseEntity<>(jwtResponse, HttpStatus.BAD_REQUEST);
    }
  }

  // the responses have to be changed to something better... Carl
  @PutMapping(value = "/user-accounts", consumes = "application/json", produces = "application/json")
  public ResponseEntity<String> update(
      @RequestBody UserAccount userAccount) {
    try {
      var response = userAccountService.updateUserAccount(userAccount);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception ex) {
      return new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/user-accounts/registrationConfirm")
  public ResponseEntity<Object> confirmRegistration(WebRequest request, Model model,
      @RequestParam("token") String token)
      throws Exception {
    Locale locale = request.getLocale();
    if (token == null) {
      String message = messages.getMessage("auth.message.invalidToken", null, locale);
      model.addAttribute("message", message);
      return ResponseEntity.status(HttpStatus.FOUND)
          .location(URI.create("http://localhost:3000/invalidtoken"))
          .build();
    }
    Calendar cal = Calendar.getInstance();
    var userAccount = userAccountService.getUserAccountByVerificationToken(token);
    if ((userAccount.getVerificationTokenExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
      String messageValue = messages.getMessage("auth.message.expired", null, locale);
      model.addAttribute("message", messageValue);
      return ResponseEntity.status(HttpStatus.FOUND)
          .location(URI.create("http://localhost:3000/invalidtoken"))
          .build();
    }
    userAccount.setIsActivated(true);
    userAccountService.setUserAccountToActive(userAccount);
    mailService.sendGrantUserToAdmin(userAccount);

    return ResponseEntity.status(HttpStatus.FOUND)
        .location(URI.create("http://localhost:3000/thankyoupage"))
        .build();
  }

  @GetMapping("/user-accounts/grantAdminRights")
  public void grantAdminRights(@RequestParam("id") long id)
      throws Exception {
    var userAccount = userAccountService.getUserAccountById(id);
    userAccount.setIsAdmin(true);
    userAccountService.setUserAccountToAdmin(userAccount);
  }
}
