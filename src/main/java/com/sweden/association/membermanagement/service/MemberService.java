package com.sweden.association.membermanagement.service;

import com.sweden.association.membermanagement.dto.PaymentDto;
import com.sweden.association.membermanagement.model.Member;
import com.sweden.association.membermanagement.model.Payment;
import com.sweden.association.membermanagement.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.sweden.association.membermanagement.service.PaymentService.UNKONWN;
import static com.sweden.association.membermanagement.validator.MemberHelper.validateMember;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public List<PaymentDto> getPaymentsOfAllMembers() {
        List<PaymentDto> paymentDtos = new ArrayList<>();
        memberRepository.findAll()
                .forEach(member -> {
                    List<Payment> memberPayments = member.getPayments();
                    if (memberPayments.isEmpty()) {
                        paymentDtos.add(new PaymentDto(member.getName(), member.getMobileNumber(), new BigDecimal(0), "-"));
                    } else {
                        memberPayments
                                .forEach(payment -> {
                                    PaymentDto paymentDto = new PaymentDto(member.getName(),
                                            member.getMobileNumber(),
                                            payment.getAmount(),
                                            payment.getTransactionDate().toString());
                                    paymentDtos.add(paymentDto);
                                });
                    }
                });
        return paymentDtos;
    }

    public void addMember(@RequestBody Member member) {
        validateMember(member);
        Member dbMember = memberRepository.findByMobileNumber(member.getMobileNumber());
        if(Objects.nonNull(dbMember)){
            System.err.println(dbMember.getName());
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("A mobile number shall be unique. This number: %s belong to the member %s ", member.getMobileNumber(), dbMember.getName()));
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
            defaultMember.setName(UNKONWN);
            defaultMember.setMobileNumber(mobileNumber);
            memberRepository.save(defaultMember);
            member = memberRepository.findByMobileNumber(mobileNumber);
        }
        return member;
    }

    public void deleteAllMembers() {
        memberRepository.deleteAll();
    }
}
