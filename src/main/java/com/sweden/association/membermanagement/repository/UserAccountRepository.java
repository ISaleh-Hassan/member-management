package com.sweden.association.membermanagement.repository;

import com.sweden.association.membermanagement.model.UserAccount;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    UserAccount findByUsernameAndPassword(@NonNull String username, @NonNull String password);

    @Query(value = "SELECT CASE WHEN COUNT(user_account) > 0 THEN true ELSE false END FROM user_account WHERE username = :username", nativeQuery = true)
    Boolean findByUsername(@Param("username") @NonNull String username);

    @Query(value = "SELECT CASE WHEN COUNT(user_account) > 0 THEN true ELSE false END FROM user_account WHERE email = :email", nativeQuery = true)
    Boolean findByEmail(@Param("email") @NonNull String email);

    UserAccount findByVerificationToken(@NonNull String verificationToken);

    @Query(value = "SELECT * FROM user_account WHERE is_activated = false", nativeQuery = true)
    List<UserAccount>findByAllIsNotActivated();
}
