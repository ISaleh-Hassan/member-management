package com.sweden.association.membermanagement.validator;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.regex.Pattern;
import com.sweden.association.membermanagement.model.UserAccount;

public class UserAccountValidator {
    public static void validateUserAccount(UserAccount userAccount) {
        if (Objects.isNull(userAccount)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user account was not sent in the request");
        }
        if (Objects.isNull(userAccount.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is missing");
        }

        if (Objects.isNull(userAccount.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is missing");
        }
        validatePassword(userAccount.getPassword());
    }

    private static void validatePassword(String password) {
        var regex = "(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{8,}";
        if (!Pattern.matches(regex, password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The password is not valid. The password must be at least 8 characters" +
                            "long and contain at least one letter and one digit");
        }
    }

}
