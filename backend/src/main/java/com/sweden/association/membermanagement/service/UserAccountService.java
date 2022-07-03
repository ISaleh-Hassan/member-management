package com.sweden.association.membermanagement.service;

import com.sweden.association.membermanagement.model.UserAccount;
import com.sweden.association.membermanagement.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService {
    @Autowired
    UserAccountRepository userAccountRepository;

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

    public void addUserAccount(UserAccount userAccount) {
        userAccountRepository.save(userAccount);
    }
}
