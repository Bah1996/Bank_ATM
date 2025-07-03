package uz.bax.bankatmcontrolsystem.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.bek.bankatmcontrolsystem.payload.*;
import uz.bek.bankatmcontrolsystem.service.ATMService;
import uz.bek.bankatmcontrolsystem.service.EmployeeService;
import uz.bek.bankatmcontrolsystem.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping("/getCash")
    public HttpEntity<?> getCash(@RequestBody PaymentDto paymentDto){
        ApiResponse apiResponse = paymentService.cashWithdrawal(paymentDto);
        return ResponseEntity.status(apiResponse.isStatus()?200:409).body(apiResponse);
    }

    @PostMapping("/fillBalance")
    public HttpEntity<?> fillBalnce(@RequestBody PaymentDto paymentDto){
        ApiResponse apiResponse = paymentService.fillBalanceCard(paymentDto);
        return ResponseEntity.status(apiResponse.isStatus()?200:409).body(apiResponse);
    }
}
