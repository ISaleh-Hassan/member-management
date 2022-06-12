package com.sweden.association.membermanagement.controller;

import com.sweden.association.membermanagement.model.UserAccount;
import com.sweden.association.membermanagement.repository.UserAccountRepository;
import com.sweden.association.membermanagement.util.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class UserAccountController {
    @Autowired
    private UserAccountRepository userAccountRepository;

    @GetMapping("/user-accounts")
    public List<UserAccount> getAllUserAccounts() {
        return userAccountRepository.findAll();
    }

    @PostMapping("/user-accounts")
    public void addUserAccount(@RequestBody UserAccount userAccount) {
        userAccountRepository.save(userAccount);
    }

    @GetMapping("/user-accounts/{id}")
    public UserAccount getUserAccountById(@PathVariable Long id) throws Exception {
        return userAccountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User account with id " + id + " was not found"));
    }
}
