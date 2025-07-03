package uz.bax.bankatmcontrolsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.bek.bankatmcontrolsystem.payload.ApiResponse;
import uz.bek.bankatmcontrolsystem.payload.CardDto;
import uz.bek.bankatmcontrolsystem.service.CardService;

@RestController
@RequestMapping("/api/card")
public class CardController {

    @Autowired
    CardService cardService;

    @PreAuthorize("hasAnyRole('BANK_DIRECTOR', 'BANK_EMPLOYEE')")
    @PostMapping("/addUzCard")
    public HttpEntity<?> addUzCard(@RequestBody CardDto cardDto) {
        ApiResponse apiResponse = cardService.addUzCard(cardDto);
        return ResponseEntity.status(apiResponse.isStatus()?200:409).body(apiResponse);
    }

    @PreAuthorize("hasAnyRole('BANK_DIRECTOR', 'BANK_EMPLOYEE')")
    @PostMapping("/addHumoCard")
    public HttpEntity<?> addHumoCard(@RequestBody CardDto cardDto) {
        ApiResponse apiResponse = cardService.addHumo(cardDto);
        return ResponseEntity.status(apiResponse.isStatus()?200:409).body(apiResponse);
    }

    @PreAuthorize("hasAnyRole('BANK_DIRECTOR', 'BANK_EMPLOYEE')")
    @PostMapping("/AddVisaCard")
    public HttpEntity<?> addVisaCard(@RequestBody CardDto cardDto) {
        ApiResponse apiResponse = cardService.addVisa(cardDto);
        return ResponseEntity.status(apiResponse.isStatus()?200:409).body(apiResponse);
    }
}
