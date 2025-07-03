package uz.bax.bankatmcontrolsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.bek.bankatmcontrolsystem.entity.ATM;
import uz.bek.bankatmcontrolsystem.entity.AtmHistory;
import uz.bek.bankatmcontrolsystem.payload.ApiResponse;
import uz.bek.bankatmcontrolsystem.payload.CardDto;
import uz.bek.bankatmcontrolsystem.service.CardService;
import uz.bek.bankatmcontrolsystem.service.DirectorDashboardService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/dashboard")
public class DirectorDashbordController {

    @Autowired
    DirectorDashboardService directorDashboardService;

    @PreAuthorize("hasRole('BANK_DIRECTOR')")
    @GetMapping("/atmHistory")
    public List<ATM> getAtms(){
        return directorDashboardService.getAtms();
    }

    @PreAuthorize("hasRole('BANK_DIRECTOR')")
    @GetMapping("/atmHistory")
    public List<AtmHistory> getHistory(@RequestParam UUID atmId){
        return directorDashboardService.getAllHistory(atmId);
    }

    @PreAuthorize("hasRole('BANK_DIRECTOR')")
    @GetMapping("/atmHistoryIn")
    public List<AtmHistory> getIncomeHistory(@RequestParam UUID atmId){
        return directorDashboardService.getIncomeHistory(atmId);
    }

    @PreAuthorize("hasRole('BANK_DIRECTOR')")
    @GetMapping("/atmHistoryOut")
    public List<AtmHistory> getOutcomeHistory(@RequestParam UUID atmId){
        return directorDashboardService.getOutcomeHistory(atmId);
    }

    @PreAuthorize("hasRole('BANK_DIRECTOR')")
    @GetMapping("/atmHistoryFill")
    public List<AtmHistory> getFillHistory(@RequestParam UUID atmId){
        return directorDashboardService.getFillHistory(atmId);
    }
}
