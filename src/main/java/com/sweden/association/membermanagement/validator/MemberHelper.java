package com.sweden.association.membermanagement.validator;

import com.sweden.association.membermanagement.model.Member;
import org.apache.commons.text.WordUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MemberHelper {
    public static List<Member> createMembersToMigrate(MultipartFile selectedFile) throws IOException {
        return CsvHelper.tryReadCsvFile(selectedFile, 0, ';')
                .stream()
                .map(MemberHelper::createMember)
                .collect(Collectors.toList());
    }

    private static Member createMember(String[] member) {
        String memberName = WordUtils.capitalizeFully(member[0].trim());
        String memberMobileNumber = correctMobileNumber(member[1].trim().replaceAll("\\s+",""));
        Member newMember = new Member();
        newMember.setName(memberName);
        newMember.setMobileNumber(memberMobileNumber);
        return newMember;
    }

    private static String correctMobileNumber(String mobileNumber) {
        final String swedishKey = "+46";
        if (!isNumeric(mobileNumber)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The mobile number is not numeric");
        }
        if (mobileNumber.startsWith("7")) {
            return swedishKey + mobileNumber;
        }
        if (mobileNumber.startsWith("07")) {
            return swedishKey + Integer.parseInt(mobileNumber);
        }
        if (mobileNumber.startsWith("46")) {
            return "+" + mobileNumber;
        }
        return mobileNumber;
    }

    public static boolean isNumeric(String string) {
        if (string == null || string.equals("")) {
            return false;
        }
        try {
            Long.parseLong(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void validateMember(Member member) {
        if (Objects.isNull(member)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user was not sent in the request");
        }
        if (Objects.isNull(member.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is missed");
        }

        if (Objects.isNull(member.getMobileNumber())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mobile number is missed");
        }

        validateAndGetMobileNumber(member.getMobileNumber());
    }

    // TODO: Use regex to validate the number
    public static void validateAndGetMobileNumber(String strMobileNumber) {
        // var mobileRegex = new RegExp("^(7[02369])*([0-9]{4})*([0-9]{3})$");
        if (!strMobileNumber.contains("+467")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The mobile number is not valid. The number shall start with +467");
        }
    }
}
