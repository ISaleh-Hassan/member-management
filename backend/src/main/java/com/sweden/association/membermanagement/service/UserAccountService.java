package com.sweden.association.membermanagement.service;

import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sweden.association.membermanagement.model.UserAccount;
import com.sweden.association.membermanagement.repository.UserAccountRepository;

@Service
public class UserAccountService {

    @Autowired
    EntityManager entityManager;

    @Autowired
    UserAccountRepository userAccountRepository;

    public Boolean Login(String userName, String password) {
        try {
            var sql = "select * from user_account where user_name =:userName and password = :password";
            final var query = entityManager.createNativeQuery(sql, UserAccount.class);
            query.setParameter("userName", userName);
            query.setParameter("password", password);
            var result = query.getSingleResult();
            if (result == null) {
                return false;
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
