package uz.bax.bankatmcontrolsystem.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.bek.bankatmcontrolsystem.entity.ATM;
import uz.bek.bankatmcontrolsystem.entity.AtmHistory;
import uz.bek.bankatmcontrolsystem.payload.*;
import uz.bek.bankatmcontrolsystem.service.ATMService;
import uz.bek.bankatmcontrolsystem.service.DirectorDashboardService;
import uz.bek.bankatmcontrolsystem.service.EmployeeService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    ATMService atmService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addDirector")
    public HttpEntity<?> addDirector(@Valid @RequestBody EmployeeDto employeeDto){
        ApiResponse apiResponse = employeeService.addDirector(employeeDto);
        return ResponseEntity.status(apiResponse.isStatus()?201:409).body(apiResponse);
    }

    @PreAuthorize("hasRole('BANK_DIRECTOR')")
    @PostMapping("/addManager")
    public HttpEntity<?> addManager(@Valid @RequestBody EmployeeDto employeeDto){
        ApiResponse apiResponse = employeeService.addEmployee(employeeDto);
        return ResponseEntity.status(apiResponse.isStatus()?201:409).body(apiResponse);
    }

    @PreAuthorize("hasRole('BANK_DIRECTOR')")
    @PostMapping("/addATM")
    public HttpEntity<?> addAtm(@Valid @RequestBody AtmDto atmDto){
        ApiResponse apiResponse = atmService.addAtm(atmDto);
        return ResponseEntity.status(apiResponse.isStatus()?201:409).body(apiResponse);
    }

    @PreAuthorize("hasRole('BANK_EMPLOYEE')")
    @PostMapping("/fillAtmBalance")
    public HttpEntity<?> addAtm(@Valid @RequestBody PaymentDto paymentDto){
        ApiResponse apiResponse = atmService.fillAtmBills(paymentDto);
        return ResponseEntity.status(apiResponse.isStatus()?201:409).body(apiResponse);
    }

    @GetMapping("/verifyEmail")
    public HttpEntity<?> verifyEmail(@RequestParam String emailCode,@RequestParam String email){
        ApiResponse apiResponse = employeeService.verifyEmail(emailCode,email);
        return ResponseEntity.status(apiResponse.isStatus()?200:409).body(apiResponse);
    }

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto loginDto){
        ApiResponse apiResponse = employeeService.login(loginDto);
        return ResponseEntity.status(apiResponse.isStatus()?200:409).body(apiResponse);
    }
}
