package com.sweden.association.membermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sweden.association.membermanagement.service.UserAccountService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class UserAccountController {
  @Autowired
  private UserAccountService userAccountService;

  @GetMapping("/useraccounts/login")
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
}
