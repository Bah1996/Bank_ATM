package uz.bax.bankatmcontrolsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.bek.bankatmcontrolsystem.entity.*;
import uz.bek.bankatmcontrolsystem.entity.enums.CardType;
import uz.bek.bankatmcontrolsystem.payload.ApiResponse;
import uz.bek.bankatmcontrolsystem.payload.CardDto;
import uz.bek.bankatmcontrolsystem.repository.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DirectorDashboardService {

    @Autowired
    AtmHistoryRepository atmHistoryRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ATMRepository atmRepository;

    public List<AtmHistory> getAllHistory(UUID atmId) {
        Optional<ATM> optionalATM = atmRepository.findById(atmId);
        if (!optionalATM.isPresent() && checkAtmId(atmId))
            return null;
        return atmHistoryRepository.findAllByAtm_Id(atmId);
    }

    public List<AtmHistory> getIncomeHistory(UUID atmId) {
        Optional<ATM> optionalATM = atmRepository.findById(atmId);
        if (!optionalATM.isPresent() && checkAtmId(atmId))
            return null;
        return atmHistoryRepository.getIncomeHistory(atmId);
    }

    public List<AtmHistory> getOutcomeHistory(UUID atmId) {
        Optional<ATM> optionalATM = atmRepository.findById(atmId);
        if (!optionalATM.isPresent() && checkAtmId(atmId))
            return null;
        return atmHistoryRepository.getOutcomeHistory(atmId);
    }

    public List<AtmHistory> getFillHistory(UUID atmId) {
        Optional<ATM> optionalATM = atmRepository.findById(atmId);
        if (!optionalATM.isPresent() && checkAtmId(atmId))
            return null;
        return atmHistoryRepository.getFillHistory(atmId);
    }

    public List<ATM> getAtms() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return atmRepository.findAllByBank_Id(user.getBank().getId());
    }

    public boolean checkAtmId(UUID atmId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return atmRepository.existsByBank_IdAndId(user.getBank().getId(), atmId);
    }
}
