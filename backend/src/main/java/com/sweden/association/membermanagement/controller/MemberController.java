package com.sweden.association.membermanagement.controller;

import com.sweden.association.membermanagement.model.Member;
import com.sweden.association.membermanagement.repository.MemberRepository;
import com.sweden.association.membermanagement.util.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class MemberController {
    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/members")
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @PostMapping("/members")
    public void newEmployee(@RequestBody Member member) {
        memberRepository.save(member);
    }

    @GetMapping("/members/{id}")
    public Member getMemberById(@PathVariable Long id) throws Exception {
        return memberRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Member with id " + id + " was not found"));
    }
}
