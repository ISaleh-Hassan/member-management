package com.sweden.association.membermanagement.service;

import com.sweden.association.membermanagement.helper.SwishTransaction;
import com.sweden.association.membermanagement.repository.PaymentRepository;
import com.sweden.association.membermanagement.validtor.CsvHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.sweden.association.membermanagement.validtor.CsvHelper.VALID_HEADER;
import static com.sweden.association.membermanagement.validtor.CsvHelper.tryReadCsvFile;
import static com.sweden.association.membermanagement.validtor.CsvHelper.validateAndConvertAmount;
import static com.sweden.association.membermanagement.validtor.CsvHelper.validateAndGetDate;
import static com.sweden.association.membermanagement.validtor.CsvHelper.validateAndGetMobileNumber;

@Service
public class PaymentService {
    private static final Logger LOGGER = Logger.getLogger(PaymentService.class.getName());

    @Autowired
    private PaymentRepository paymentRepository;

    public Map<String, List<SwishTransaction>> addPayment(MultipartFile selectedFile) {
        CsvHelper.isValidSwedbankCsvFile(tryReadCsvFile(selectedFile, 0));
        List<String[]> transactions = tryReadCsvFile(selectedFile, 2);
        Map<String, List<SwishTransaction>> swishTransactions =  getMapOfAllSwishTransactions(transactions);
        //TODO: Add all transactions to the members in question. As for now we only return the list
        return swishTransactions;
    }

    public static Map<String, List<SwishTransaction>> getMapOfAllSwishTransactions(List<String[]> transactions) {
        Map<String, List<SwishTransaction>> map = new HashMap<>();
        transactions.forEach(transaction -> {
            if (transaction.length != VALID_HEADER.length) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The format of the csv row is not valid");
            }
            String description = transaction[9];

            if (description.contains("Swish mottagen")) {
                String mobileNumber = validateAndGetMobileNumber(transaction[8]);
                String date = validateAndGetDate(transaction[7]);
                BigDecimal amount = validateAndConvertAmount(transaction[10]);
                // In case the member makes two payments in the same day, we add the total to the map
                map.computeIfAbsent(mobileNumber, k -> new ArrayList<>()).add(new SwishTransaction(date, amount));
            }
        });
        return map;
    }

}
