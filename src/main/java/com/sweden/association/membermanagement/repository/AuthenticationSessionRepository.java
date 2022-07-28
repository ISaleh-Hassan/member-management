package com.sweden.association.membermanagement.repository;

import com.sweden.association.membermanagement.model.AuthenticationSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface AuthenticationSessionRepository extends JpaRepository<AuthenticationSession, Long> {
    AuthenticationSession findAuthenticationSessionByToken(@NonNull String token);
}
