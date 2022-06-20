package com.sweden.association.membermanagement.controller;


import com.sweden.association.membermanagement.model.Payment;
import com.sweden.association.membermanagement.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.logging.Logger;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class PaymentController {
    private static final Logger LOGGER = Logger.getLogger(PaymentController.class.getName());

    @Autowired
    private PaymentService paymentService;

    @RequestMapping(path = "/payments", method = RequestMethod.POST, consumes = { "multipart/form-data" })
    @ResponseBody
    public ResponseEntity<Void> addPayments(@RequestParam MultipartFile selectedFile) {
        try {
            paymentService.addPayments(selectedFile);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.warning("Something went wrong when trying to add payment  " +   e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
     }

    @GetMapping(path = "/payments")
    public ResponseEntity<List<Payment>> getAllPayments() {
        return new ResponseEntity<>(paymentService.getAllPayments(), HttpStatus.OK);
    }
}
