package com.sweden.association.membermanagement.service;

import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import com.sweden.association.membermanagement.model.UserAccount;
import com.sweden.association.membermanagement.repository.UserAccountRepository;
import com.sweden.association.membermanagement.utility.JwtResponse;
import com.sweden.association.membermanagement.utility.JwtUtility;

@Service
public class UserAccountService {
    
    JwtResponse jwtResponse = new JwtResponse();
    @Autowired
    private UserAccountRepository userAccountRepository;

    public JwtResponse login(String userName, String password) {
        
        try {
            UserAccount userAccount = userAccountRepository.findByUserNameAndPassword(userName, password);
            if (userAccount != null) {
                JwtUtility jwtUtility = new JwtUtility();
               jwtResponse.setJwtToken(jwtUtility.generateToken(userAccount));
            }
            return jwtResponse;
        } catch (Exception ex) {
            return jwtResponse;
        }
    }

    // @Override
    // public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //     try {
    //         var userAccount = userAccountRepository.findByUserName(username);
    //         User user = new User(userAccount.getUserName(), userAccount.getPassword(), null);
    //         return user;
    //     } catch (Exception e) {
    //         return null;
    //     }
    // }
}
