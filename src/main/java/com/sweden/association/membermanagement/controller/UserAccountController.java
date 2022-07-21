package com.sweden.association.membermanagement.controller;

import java.util.Calendar;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.annotation.PostMapping;

import com.sweden.association.membermanagement.dto.MemberDto;
import com.sweden.association.membermanagement.model.UserAccount;
import com.sweden.association.membermanagement.service.MailService;
import com.sweden.association.membermanagement.service.UserAccountService;

import com.sweden.association.membermanagement.utility.JwtResponse;
import com.sweden.association.membermanagement.validator.UserAccountValidator;

@CrossOrigin(origins = { "http://localhost:3000", "https://localhost:3000" })
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

  @PostMapping("/user-accounts/register")
  public JwtResponse register(@RequestParam String firstname, @RequestParam String lastname,
      @RequestParam String mobileNumber,
      @RequestParam String email, @RequestParam String username, @RequestParam String password) {
    try {
      var memberDto = new MemberDto();
      memberDto.setFirstName(firstname);
      memberDto.setLastName(lastname);
      memberDto.setMobileNumber(mobileNumber);

      var userAccount = new UserAccount();
      userAccount.setEmail(email);
      userAccount.setUserName(username);
      userAccount.setPassword(password);

      UserAccountValidator.validateUserAccount(userAccount);
      memberDto.setUserAccount(userAccount);
      var response = userAccountService.register(memberDto);
      return response;
    } catch (Exception ex) {
      return null;
    }
  }

  @GetMapping("/user-accounts/registrationConfirm")
  public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token)
      throws Exception {
    Locale locale = request.getLocale();
    if (token == null) {
      String message = messages.getMessage("auth.message.invalidToken", null, locale);
      model.addAttribute("message", message);
      return "redirect:/badUser.html?lang=" + locale.getLanguage();
    }
    Calendar cal = Calendar.getInstance();
    var userAccount = userAccountService.getUserAccountByVerificationToken(token);
    if ((userAccount.getVerificationTokenExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
      String messageValue = messages.getMessage("auth.message.expired", null, locale);
      model.addAttribute("message", messageValue);
      return "redirect:/badUser.html?lang=" + locale.getLanguage();
    }
    userAccount.setIsActivated(true);
    userAccountService.setUserAccountToActive(userAccount);
    mailService.sendGrantUserToAdmin(userAccount);
    return "redirect:/login.html?lang=" + request.getLocale().getLanguage();
  }

  @GetMapping("/user-accounts/grantAdminRights")
  public void grantAdminRights(@RequestParam("id") long id)
      throws Exception {
    var userAccount = userAccountService.getUserAccountById(id);
    userAccount.setIsAdmin(true);
    userAccountService.setUserAccountToAdmin(userAccount);
  }
}
