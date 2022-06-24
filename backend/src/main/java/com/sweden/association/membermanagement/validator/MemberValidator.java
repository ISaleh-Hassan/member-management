package com.sweden.association.membermanagement.validator;

import com.sweden.association.membermanagement.model.Member;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

public class MemberValidator {
    public static void validateMember(Member member) {
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

        validateAndGetMobileNumber(member.getMobileNumber());
    }

    //TODO: Use regex to validate the number
    public static void validateAndGetMobileNumber(String strMobileNumber){
        if(!(strMobileNumber.length() == 12) || (!strMobileNumber.contains("+467"))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The mobile number is not valid. The number shall start with +467");
        }
    }
}
