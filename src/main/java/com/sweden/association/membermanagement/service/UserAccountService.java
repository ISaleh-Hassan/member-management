package com.sweden.association.membermanagement.service;

import java.net.http.HttpRequest;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sweden.association.membermanagement.dto.MemberDto;
import com.sweden.association.membermanagement.model.AuthenticationSession;
import com.sweden.association.membermanagement.model.Member;
import com.sweden.association.membermanagement.model.UserAccount;
import com.sweden.association.membermanagement.repository.AuthenticationSessionRepository;
import com.sweden.association.membermanagement.repository.MemberRepository;
import com.sweden.association.membermanagement.repository.UserAccountRepository;
import com.sweden.association.membermanagement.utility.JwtResponse;
import com.sweden.association.membermanagement.utility.JwtUtility;

@Service
@EnableScheduling
public class UserAccountService {

    JwtResponse jwtResponse = new JwtResponse();
    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AuthenticationSessionRepository authenticationSessionRepository;
    @Autowired
    private MailService mailService;

    public JwtResponse login(String userName, String password) {
        try {
            UserAccount userAccount = userAccountRepository.findByUsernameAndPassword(userName, password);

            if (userAccount != null) {
                JwtUtility jwtUtility = new JwtUtility();
                if (userAccount.getIsActivated()) {
                    String token = jwtUtility.generateToken(userAccount);
                    jwtResponse.setJwtToken(token);
                    handleUserAccountAuthenticationSession(userAccount, token);
                }
                jwtResponse.setInvalidCredentials(false);
                jwtResponse.setIsActivated(userAccount.getIsActivated());
                jwtResponse.setIsAdmin(userAccount.getIsAdmin());
            } else {
                jwtResponse.setInvalidCredentials(true);
            }
            return jwtResponse;
        } catch (Exception ex) {
            return jwtResponse;
        }
    }

    private void handleUserAccountAuthenticationSession(UserAccount userAccount, String token) {
        List<AuthenticationSession> authenticationSessions = authenticationSessionRepository
                .findAll()
                .stream()
                .filter(authenticationSession -> authenticationSession.getUserAccount().equals(userAccount))
                .collect(Collectors.toList());

        // We start by removing all tokens that are related to this userAccount
        authenticationSessions.forEach(authenticationSession -> authenticationSessionRepository
                .deleteById(authenticationSession.getAuthentication_session_id()));

        AuthenticationSession authenticationSession = new AuthenticationSession();
        authenticationSession.setUserAccount(userAccount);
        authenticationSession.setToken(token);
        authenticationSession.setTokenExpiryTimestamp(JwtUtility.generateExpirationDate(60L));
        authenticationSessionRepository.save(authenticationSession);
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
                member.setName(memberDto.getName());
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
                userAccount.setMemberUserAccount(member);
                userAccountRepository.save(userAccount);
            
                mailService.sendConfirmRegistration(userAccount);
                jwtResponse.setUserRegisteredSuccess(true);
            }
            return jwtResponse;

        } catch (

        Exception e) {
            return null;
        }
    }

    //the return type has to be changed to something better ... Carl
    public String updateUserAccount(UserAccount editUserAccount){
        var userAccount = userAccountRepository.findById(editUserAccount.getUserAccountId()).get();
        userAccount.setEmail(editUserAccount.getEmail());
        userAccount.setPassword(editUserAccount.getPassword());
        try{
        userAccountRepository.save(userAccount);
        }
        catch(Exception ex){
            return "error";
        }
        return "success";
    }
    public UserAccount getUserAccountByVerificationToken(String token) {
        var userAccount = userAccountRepository.findByVerificationToken(token);
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

    // executes every 60 second with a 1 second delay on application startup
    @Scheduled(initialDelay = 1000L, fixedRateString = "PT1H")
    private void deleteUserIfNotActivatedJob() throws InterruptedException {
        deleteUserIfNotActivated();
    }

    private void deleteUserIfNotActivated() {
        Calendar calendar = Calendar.getInstance();
        var userAccounts = userAccountRepository.findByAllIsNotActivated();
        try {
            for (UserAccount userAccount : userAccounts) {
                if (userAccount.getVerificationTokenExpiryDate().getTime() < calendar.getTime().getTime()) {
                    userAccountRepository.delete(userAccount);
                }
            }
        } catch (Exception ex) {
        }
    }
}
