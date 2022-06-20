package com.sweden.association.membermanagement.controller;

import com.sweden.association.membermanagement.model.Member;
import com.sweden.association.membermanagement.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private MemberService memberService;

    @GetMapping("/members")
    public ResponseEntity<List<Member>> getAllMembers() {
        return new ResponseEntity<>(memberService.getAllMembers(), HttpStatus.OK);
    }

    @PostMapping("/members")
    public void addMember(@RequestBody Member member) {
        memberService.addMember(member);
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        return new ResponseEntity<>(memberService.getMemberById(id), HttpStatus.OK);

    }
}
