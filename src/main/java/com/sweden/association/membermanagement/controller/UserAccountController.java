package com.sweden.association.membermanagement.controller;

import javax.security.auth.login.CredentialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sweden.association.membermanagement.model.UserAccount;
import com.sweden.association.membermanagement.service.UserAccountService;
import com.sweden.association.membermanagement.utility.JwtRequest;
import com.sweden.association.membermanagement.utility.JwtResponse;
import com.sweden.association.membermanagement.utility.JwtUtility;

@CrossOrigin(origins = { "http://localhost:3000", "https://localhost:3000" })
@RestController
@RequestMapping("/api/v1")
public class UserAccountController {
  @Autowired
  private UserAccountService userAccountService;

  @Autowired
  private JwtUtility jwtUtility;

  @GetMapping("/user-accounts/login")
  public JwtResponse login(@RequestParam String userName, @RequestParam String password) {
    var response = new JwtResponse();
    try {
       response = userAccountService.login(userName, password);
      return response;
    } catch (Exception ex) {
      // return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      return response;
    }
  }

  @PostMapping("/authenticate")
  public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {
   
    final String token = jwtUtility.generateToken(new UserAccount());
    return new JwtResponse(token);
  }
}
