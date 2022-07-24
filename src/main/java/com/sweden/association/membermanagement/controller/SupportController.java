package com.sweden.association.membermanagement.controller;


import com.sweden.association.membermanagement.service.PaymentService;
import com.sweden.association.membermanagement.validator.CsvHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.logging.Logger;

@CrossOrigin(origins = {"https://localhost:3000", "http://localhost:3000", "https://member-payments-management.herokuapp.com"})
@RestController
@RequestMapping("/api/v1")
public class SupportController {
    private static final Logger LOGGER = Logger.getLogger(SupportController.class.getName());

    @Autowired
    private PaymentService paymentService;

    @RequestMapping(path = "/support/validate-csv", method = RequestMethod.POST, consumes = { "multipart/form-data" })
    public ResponseEntity<String> validateCsvFile(@RequestParam MultipartFile selectedFile) {
        try {
            CsvHelper.isValidSwedBankCsvFile(CsvHelper.tryReadCsvFileWithCommaSeparator(selectedFile, 0));
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.warning("Something went wrong when trying to add payment  " +   e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
