package com.sweden.association.membermanagement.service;

import com.sweden.association.membermanagement.model.Member;
import com.sweden.association.membermanagement.model.Payment;
import com.sweden.association.membermanagement.model.PaymentType;
import com.sweden.association.membermanagement.repository.PaymentRepository;
import com.sweden.association.membermanagement.validator.CsvHelper;
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
import java.util.Map;
import java.util.logging.Logger;

import static com.sweden.association.membermanagement.validator.CsvHelper.VALID_HEADER;
import static com.sweden.association.membermanagement.validator.CsvHelper.validateAndConvertAmount;
import static com.sweden.association.membermanagement.validator.CsvHelper.validateAndGetDate;
import static com.sweden.association.membermanagement.validator.MemberHelper.validateAndGetMobileNumber;

@Service
public class PaymentService {
    private static final Logger LOGGER = Logger.getLogger(PaymentService.class.getName());
    public final static String UNKONWN = "UNKNOWN";

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private MemberService memberService;


    public void addPayments(MultipartFile selectedFile) {
        CsvHelper.isValidSwedBankCsvFile(CsvHelper.tryReadCsvFileWithCommaSeparator(selectedFile, 0));
        List<String[]> transactions = CsvHelper.tryReadCsvFileWithCommaSeparator(selectedFile, 2);
        List<Payment> swishTransactions = getPaymentsMap(transactions).get(PaymentType.SWISH);
        // TODO: As for now, we need to clear the db before adding new payments due to the limitation we have in the db.
        //  If the client will pay for the application, we will make it possible to save historical payments
        deleteAllPayments();
        swishTransactions.forEach(payment -> paymentRepository.save(payment));

    }

    public List<Payment> getAllPayments(){
        return paymentRepository.findAll();
    }

    public void deleteAllPayments(){
        paymentRepository.deleteAll();
    }

    private Map<PaymentType, List<Payment>> getPaymentsMap(List<String[]> transactions) {
        List<Payment> swishPayments = new ArrayList<>();
        List<Payment> bankGiroPayments = new ArrayList<>();
        List<Payment> transferPayments = new ArrayList<>();
        transactions.forEach(transaction -> {
            if (transaction.length != VALID_HEADER.length) {
                LOGGER.warning(Arrays.toString(transaction));
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The format of the transaction row is not valid");
            }
            String description = transaction[9];

            if (description.contains("Swish")) {
                String mobileNumber = description.replaceAll("Swish ", "").trim();
                validateAndGetMobileNumber(mobileNumber);
                String date = validateAndGetDate(transaction[7]);
                BigDecimal amount = validateAndConvertAmount(transaction[10]);
                Member member = memberService.getOrCreateDefaultMember(mobileNumber);
                Payment payment = new Payment();
                payment.setAmount(amount);
                payment.setTransactionDate(LocalDate.parse(date));
                payment.setPayer(member);
                swishPayments.add(payment);
            } else if(description.contains("Bank giro")){
                //TODO: This is will be handled when we start to support bankGiro payments
                bankGiroPayments.add(null);
            } else if(description.contains("Transfer")){
                //TODO: This is will be handled when we start to support Transfer payments
                transferPayments.add(null);
            }
        });
        return Map.of(PaymentType.SWISH, swishPayments, PaymentType.BANK_GIRO, bankGiroPayments, PaymentType.TRANSFER, transferPayments);
    }

}
