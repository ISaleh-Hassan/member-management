package com.sweden.association.membermanagement.service;

import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.sweden.association.membermanagement.model.UserAccount;
import com.sweden.association.membermanagement.repository.UserAccountRepository;

@Service
public class UserAccountService implements UserDetailsService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    public Boolean login(String userName, String password) {
        try {
            UserAccount userAccount = userAccountRepository.findByUserNameAndPassword(userName, password);
            if (userAccount == null) {
                return false;
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            var userAccount = userAccountRepository.findByUserName(username);
            User user = new User(userAccount.getUserName(), userAccount.getPassword(), null);
            return user;
        } catch (Exception e) {
            return null;
        }
    }
}
