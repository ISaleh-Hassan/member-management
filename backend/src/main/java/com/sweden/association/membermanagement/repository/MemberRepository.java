package com.sweden.association.membermanagement.repository;

import com.sweden.association.membermanagement.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Long> {

}
