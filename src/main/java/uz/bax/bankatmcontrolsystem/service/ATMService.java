package uz.bax.bankatmcontrolsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.bek.bankatmcontrolsystem.entity.*;
import uz.bek.bankatmcontrolsystem.payload.ApiResponse;
import uz.bek.bankatmcontrolsystem.payload.AtmDto;
import uz.bek.bankatmcontrolsystem.payload.PaymentDto;
import uz.bek.bankatmcontrolsystem.payload.SetOfBill;
import uz.bek.bankatmcontrolsystem.repository.*;

import java.util.*;

@Service
public class ATMService {

    @Autowired
    ATMRepository atmRepository;
    @Autowired
    MoneyBillRepository moneyBillRepository;
    @Autowired
    AtmBillsRepository atmBillsRepository;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    AtmHistoryRepository atmHistoryRepository;
    @Autowired
    CardTypeRepository cardTypeRepository;
    @Autowired
    BankRepository bankRepository;

    public ApiResponse addAtm(AtmDto atmDto){
        Optional<Bank> optionalBank = bankRepository.findById(atmDto.getBankId());
        if(!optionalBank.isPresent())
            return new ApiResponse("Bunday idlik bank mavjud emas!",false);

        Set<CardType> cardTypeSet = new HashSet<>();
        for (Integer cardTypeId:atmDto.getCardTypeSetIds()) {
            Optional<CardType> optionalCardType = cardTypeRepository.findById(cardTypeId);
            if(!optionalCardType.isPresent())
                return new ApiResponse("Bunday idlik cardType mavjud emas!",false);
            cardTypeSet.add(optionalCardType.get());
        }
        ATM atm = new ATM();
        atm.setCardTypeSet(cardTypeSet);
        atm.setBank(optionalBank.get());
        atm.setCommissionIsBelongIncome(atmDto.getCommissionIsBelongIncome());
        atm.setCommissionIsBelongOutcome(atmDto.getCommissionIsBelongOutcome());
        atm.setCommissionIsNotBelongIncome(atmDto.getCommissionIsNotBelongIncome());
        atm.setCommissionIsNotBelongOutcome(atmDto.getCommissionIsNotBelongOutcome());
        atm.setMaxOutMoney(atmDto.getMaxOutMoney());
        atm.setMinBalance(atmDto.getMinBalance());
        atmRepository.save(atm);
        return new ApiResponse("Atm qo'shildi!",true);
    }

    public ApiResponse fillAtmBills(PaymentDto paymentDto){
        Optional<ATM> optionalATM = atmRepository.findById(paymentDto.getATMid());
        if(!optionalATM.isPresent()){
            return new ApiResponse("Bunday idlik ATM mavjud emas",false);
        }
        List<SetOfBill> setOfBillsCash = paymentDto.getSetOfBills();
        Collections.sort(setOfBillsCash,Collections.reverseOrder());
        for (SetOfBill billCash:setOfBillsCash) {
            Optional<MoneyBill> optionalMoneyBill = moneyBillRepository.findById(billCash.getMoneyBillId());
            if(!optionalATM.isPresent())
                return new ApiResponse("Kiritilgan kupyura tizimda mavjud emas!",false);
        }
        List<AtmBills> atmBills = atmBillsRepository.findAllByAtmIdAndCurrencyId(paymentDto.getATMid(), currencyRepository.findByName("UZS").get().getId());
        Collections.sort(atmBills,Collections.reverseOrder());
        int amount = 0;
        for (AtmBills atmBill:atmBills) {
            int i = 0;

            if(atmBill.getBillAmount().equals(setOfBillsCash.get(i))){
                atmBill.setQuantity(atmBill.getQuantity()+setOfBillsCash.get(i).getQuantity());
                atmBillsRepository.save(atmBill);
                i+=1;
                amount = amount + setOfBillsCash.get(i).getQuantity()*atmBill.getBillAmount();
            }
        }

        AtmHistory atmHistory = new AtmHistory();
        atmHistory.setAtm(optionalATM.get());
        atmHistory.setCurrency(currencyRepository.findByName("UZS").get());
        atmHistory.setPaymentAmount(amount);
        atmHistory.setPaymentType("Fill");
        atmHistoryRepository.save(atmHistory);

        return new ApiResponse("ATM ga kupyuralar to'dirildi!",true);
    }
}
