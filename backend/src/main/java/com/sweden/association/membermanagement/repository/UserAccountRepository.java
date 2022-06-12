package com.sweden.association.membermanagement.repository;

import com.sweden.association.membermanagement.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

}
