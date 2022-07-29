package com.sweden.association.membermanagement.controller;

import com.sweden.association.membermanagement.dto.PaymentDto;
import com.sweden.association.membermanagement.model.Member;
import com.sweden.association.membermanagement.service.MemberService;
import com.sweden.association.membermanagement.validator.MemberHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = {"https://localhost:3000", "http://localhost:3000", "https://member-payments-management.herokuapp.com"})
@RestController
@RequestMapping("/api/v1")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/members")
    public ResponseEntity<List<Member>> getAllMembers() {
        try {
            return new ResponseEntity<>(memberService.getAllMembers(), HttpStatus.OK);
        } catch (Exception ex) {
             return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/members")
    public ResponseEntity<String> addMember(@RequestBody Member member) {
        try {
            memberService.addMember(member);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping(path = "/members/all-payments")
    @ResponseBody
    public ResponseEntity<List<PaymentDto>> getAllMemberPayments() {
        try {
            return new ResponseEntity<>(memberService.getPaymentsOfAllMembers(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/members")
    public ResponseEntity<String> deleteAllMembers() {
        try {
            memberService.deleteAllMembers();
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        return new ResponseEntity<>(memberService.getMemberById(id), HttpStatus.OK);
    }
}
