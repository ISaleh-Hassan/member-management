package com.sweden.association.membermanagement.repository;

import com.sweden.association.membermanagement.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    UserAccount findByUsernameAndPassword(@NonNull String username, @NonNull String password);

    UserAccount findByUsername(@NonNull String username);

    UserAccount findByEmail(@NonNull String email);

    UserAccount findByVerificationToken(@NonNull String verificationToken);
}
