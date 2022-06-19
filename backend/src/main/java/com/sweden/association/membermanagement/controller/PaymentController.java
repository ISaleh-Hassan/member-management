package com.sweden.association.membermanagement.controller;


import com.sweden.association.membermanagement.helper.SwishTransaction;
import com.sweden.association.membermanagement.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @RequestMapping(path = "/payments", method = RequestMethod.POST, consumes = { "multipart/form-data" })
    @ResponseBody
    public Map<String, List<SwishTransaction>> addPayments(@RequestParam MultipartFile selectedFile) {
        return paymentService.addPayment(selectedFile);
     }
}
