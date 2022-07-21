package com.sweden.association.membermanagement.service;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sweden.association.membermanagement.dto.MemberDto;
import com.sweden.association.membermanagement.model.Member;
import com.sweden.association.membermanagement.model.UserAccount;
import com.sweden.association.membermanagement.repository.MemberRepository;
import com.sweden.association.membermanagement.repository.UserAccountRepository;
import com.sweden.association.membermanagement.utility.JwtResponse;
import com.sweden.association.membermanagement.utility.JwtUtility;

@Service
public class UserAccountService {

    JwtResponse jwtResponse = new JwtResponse();
    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MailService mailService;

    public JwtResponse login(String userName, String password) {
        try {
            UserAccount userAccount = userAccountRepository.findByUsernameAndPassword(userName, password);
            if (userAccount != null) {
                JwtUtility jwtUtility = new JwtUtility();
                jwtResponse.setJwtToken(jwtUtility.generateToken(userAccount));
            }
            return jwtResponse;
        } catch (Exception ex) {
            return jwtResponse;
        }
    }

    public JwtResponse register(MemberDto memberDto) {

        try {
            var mobileNumberExists = memberRepository.findByMobileNumber(memberDto.getMobileNumber());
            var userExistsByUsername = userAccountRepository.findByUsername(memberDto.getUserAccount().getUsername());
            var userExistsByEmail = userAccountRepository.findByEmail(memberDto.getUserAccount().getEmail());

            if (mobileNumberExists != null)
                jwtResponse.setMobileNumberExists(true);

            if (userExistsByUsername)
                jwtResponse.setUsernameExists(true);

            if (userExistsByEmail)
                jwtResponse.setEmailExists(true);

            else if (mobileNumberExists == null && !userExistsByEmail && !userExistsByUsername) {
                var member = new Member();
                member.setFirstName(memberDto.getFirstName());
                member.setLastName(memberDto.getLastName());
                member.setMobileNumber(memberDto.getMobileNumber());

                var userAccount = new UserAccount();
                userAccount.setEmail(memberDto.getUserAccount().getEmail());
                userAccount.setUserName(memberDto.getUserAccount().getUsername());
                userAccount.setPassword(memberDto.getUserAccount().getPassword());
                userAccount.setIsAdmin(false);
                userAccount.setIsActivated(false);
                userAccount.setMemberUserAccount(member);

                String token = UUID.randomUUID().toString();
                userAccount.setVerificationToken(token);

                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                int duration = (60 * 60) * 24 * 1000;

                timestamp.setTime(timestamp.getTime() + duration);

                userAccount.setVerificationTokenExpiryDate(timestamp);

                member.setUserAccount(userAccount);

                memberRepository.save(member);

                // userAccountRepository.save(userAccount);

                // jwtResponse.setJwtToken(jwtUtility.generateToken(userAccount));

                mailService.sendConfirmRegistration(userAccount);
                jwtResponse.setUserRegisteredSuccess(true);
            }
            return jwtResponse;

        } catch (

        Exception e) {
            return null;
        }
    }

    public UserAccount getUserAccountByVerificationToken(String token) {
        var userAccount = userAccountRepository.findByVerificationToken(token);
        // var map = new HashMap<String, Date>();
        // map.put(user.getVerificationToken(), user.getVerificationTokenExpiryDate());
        return userAccount;
    }

    public UserAccount getUserAccountById(long id) {
        var userAccount = userAccountRepository.findById(id).get();
        return userAccount;
    }

    public void setUserAccountToAdmin(UserAccount userAccount) {
        userAccountRepository.save(userAccount);
    }

    public void setUserAccountToActive(UserAccount userAccount) {
        userAccountRepository.save(userAccount);
    }
}
