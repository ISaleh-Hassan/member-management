package com.sweden.association.membermanagement.validtor;

import com.sweden.association.membermanagement.model.Member;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MemberValidator {
    public static void ValidateMember(Member member) {
        if(Objects.isNull(member)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user was not sent in the request");
        }
        if(Objects.isNull(member.getFirstName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "First name is missed");
        }

        if(Objects.isNull(member.getLastName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Last name is missed");
        }

        if(Objects.isNull(member.getMobileNumber()) ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mobile number is missed");
        }

        if(isValidSwedishMobileNumber(member.getMobileNumber()) ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mobile number is missed");
        }
    }

    private static boolean isValidSwedishMobileNumber(String mobileNumber){

        return false;
    }
}
