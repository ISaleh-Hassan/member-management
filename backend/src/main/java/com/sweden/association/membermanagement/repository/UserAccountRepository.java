package com.sweden.association.membermanagement.repository;

import com.sweden.association.membermanagement.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;


public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    UserAccount findByUserNameAndPassword(@NonNull String userName, @NonNull String password);
}
