package com.sweden.association.membermanagement.service;

import com.sweden.association.membermanagement.model.Member;
import com.sweden.association.membermanagement.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

import static com.sweden.association.membermanagement.service.PaymentService.UNKONWN;
import static com.sweden.association.membermanagement.validator.MemberValidator.validateMember;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public List<Member> getAllMembers() {
        List<Member> x = memberRepository.findAll();
        return memberRepository.findAll();
    }

    public void addMember(@RequestBody Member member) {
        validateMember(member);
        Member dbMember = memberRepository.findByMobileNumber(member.getMobileNumber());
        if(Objects.nonNull(dbMember)){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("A mobile number shall be unique. This number belong to %s %s", dbMember.getFirstName(), dbMember.getLastName()));
        }
        memberRepository.save(member);
    }

    public Member getMemberById(@PathVariable Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member with id " + id + " was not found"));
    }

    public Member getOrCreateDefaultMember(String mobileNumber) {
        Member member = memberRepository.findByMobileNumber(mobileNumber);
        if (Objects.isNull(member)) {
            // We create a default member and the admin can edit it in the future
            Member defaultMember = new Member();
            defaultMember.setFirstName(UNKONWN);
            defaultMember.setLastName(UNKONWN);
            defaultMember.setMobileNumber(mobileNumber);
            memberRepository.save(defaultMember);
            member = memberRepository.findByMobileNumber(mobileNumber);
        }
        return member;
    }
}
