package com.sweden.association.membermanagement.controller;

import javax.security.auth.login.CredentialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
  

  private AuthenticationManager authenticationManager;

  @GetMapping("/user-accounts/login")
  public Boolean login(@RequestParam String userName, @RequestParam String password) {
    try {
      var status = userAccountService.login(userName, password);
      if (status) {
        return true;
      }
      return false;
    } catch (Exception ex) {
      // return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      return false;
    }
  }

  @PostMapping("/authenticate")
  public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {
    try {
  
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              jwtRequest.getUsername(),
              jwtRequest.getPassword()));
    } catch (BadCredentialsException ex) {
      throw new Exception("INVALID_CREDENTIALS", ex);
    }
    final UserDetails userDetails = userAccountService.loadUserByUsername(jwtRequest.getUsername());
    final String token = jwtUtility.generateToken(userDetails);
    return new JwtResponse(token);
  }
}
