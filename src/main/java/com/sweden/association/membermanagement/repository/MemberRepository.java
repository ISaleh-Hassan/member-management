package com.sweden.association.membermanagement.repository;

import com.sweden.association.membermanagement.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;


public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByMobileNumber(@NonNull String mobileNumber);
    }
