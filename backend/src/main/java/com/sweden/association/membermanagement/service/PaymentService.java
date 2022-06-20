package com.sweden.association.membermanagement.service;

import com.sweden.association.membermanagement.model.Member;
import com.sweden.association.membermanagement.model.Payment;
import com.sweden.association.membermanagement.repository.PaymentRepository;
import com.sweden.association.membermanagement.validtor.CsvHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static com.sweden.association.membermanagement.validtor.CsvHelper.VALID_HEADER;
import static com.sweden.association.membermanagement.validtor.CsvHelper.tryReadCsvFile;
import static com.sweden.association.membermanagement.validtor.CsvHelper.validateAndConvertAmount;
import static com.sweden.association.membermanagement.validtor.CsvHelper.validateAndGetDate;
import static com.sweden.association.membermanagement.validtor.CsvHelper.validateAndGetMobileNumber;

@Service
public class PaymentService {
    private static final Logger LOGGER = Logger.getLogger(PaymentService.class.getName());
    public final static String UNKONWN = "UNKONWN";

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private MemberService memberService;


    public void addPayments(MultipartFile selectedFile) {
        CsvHelper.isValidSwedbankCsvFile(tryReadCsvFile(selectedFile, 0));
        List<String[]> transactions = tryReadCsvFile(selectedFile, 2);
        List<Payment> swishTransactions = getMapOfAllSwishTransactions(transactions);
        swishTransactions.forEach(payment -> paymentRepository.save(payment));
    }

    public List<Payment> getAllPayments(){
        return paymentRepository.findAll();
    }


    private List<Payment> getMapOfAllSwishTransactions(List<String[]> transactions) {
        List<Payment> payments = new ArrayList<>();
        transactions.forEach(transaction -> {
            if (transaction.length != VALID_HEADER.length) {
                LOGGER.warning(Arrays.toString(transaction));
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The format of the transaction row is not valid");
            }
            String description = transaction[9];

            if (description.contains("Swish mottagen")) {
                String mobileNumber = validateAndGetMobileNumber(transaction[8]);
                String date = validateAndGetDate(transaction[7]);
                BigDecimal amount = validateAndConvertAmount(transaction[10]);
                Member member = memberService.getOrCreateDefaultMember(mobileNumber);
                Payment payment = new Payment();
                payment.setAmount(amount);
                payment.setTransactionDate(LocalDate.parse(date));
                payment.setPayer(member);
                payments.add(payment);
            }
        });
        return payments;
    }

}
