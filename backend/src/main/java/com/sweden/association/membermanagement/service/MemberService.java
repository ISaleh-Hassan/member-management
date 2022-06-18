package com.sweden.association.membermanagement.service;

import com.sweden.association.membermanagement.model.Member;
import com.sweden.association.membermanagement.repository.MemberRepository;
import com.sweden.association.membermanagement.util.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public void addMember(@RequestBody Member member) {
        memberRepository.save(member);
    }

    public Member getMemberById(@PathVariable Long id) throws Exception {
        return memberRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Member with id " + id + " was not found"));
    }
}
