package uz.bax.bankatmcontrolsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.bek.bankatmcontrolsystem.entity.*;
import uz.bek.bankatmcontrolsystem.entity.enums.CardType;
import uz.bek.bankatmcontrolsystem.payload.ApiResponse;
import uz.bek.bankatmcontrolsystem.payload.CardDto;
import uz.bek.bankatmcontrolsystem.payload.PaymentDto;
import uz.bek.bankatmcontrolsystem.payload.SetOfBill;
import uz.bek.bankatmcontrolsystem.repository.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    CardRepository cardRepo;
    @Autowired
    ATMRepository atmRepo;
    @Autowired
    AtmBillsRepository atmBillsRepo;
    @Autowired
    CurrencyRepository currencyRepo;
    @Autowired
    MoneyBillRepository moneyBillRepo;
    @Autowired
    AtmHistoryRepository atmHistoryRepo;

    //Naqd pul yechib olish
    public ApiResponse cashWithdrawal(PaymentDto paymentDto){
        //Tizimga kirgan cardni olish
        Card card = (Card) SecurityContextHolder.getContext().getAuthentication();

        //ATM texnikasi o'zining id raqamini paymentDto ga qushib yuboradi
        //Ushbu id orqali ATM ni bazadan chaqirish
        ATM atm = atmRepo.findById(paymentDto.getATMid()).get();

        //Ushbu Atm banki cardning bankiga tegishli bulmagan holdagi commission miqdori
        double commission = atm.getCommissionIsNotBelongOutcome();
        //ATm bankini cardning banki bilan taqqoslash
        if(card.getBank().equals(atm.getBank())){
            commission = atm.getCommissionIsBelongOutcome();
        }
        //Card dan yechib olish kerak bo'lgan umumiy summa
        double withDrawAmount = paymentDto.getAmount() * (1 + commission);

        //Card ga yechib olinadigan summa miqdorda pul borligini tekshiramiz
        if(card.getBalance()< withDrawAmount)
            return new ApiResponse("Mablag' yetarli emas!",false);

        //Ushbu ATM dagi barcha kupyuralar Listini olamiz
        List<AtmBills> atmBills = atmBillsRepo.findAllByAtmIdAndCurrencyId(atm.getId(),currencyRepo.findByName("UZS").get().getId());
        //Kamayish tartibida saralaymiz (100_000,50_000,10_000,5_000,1_000) -- Kupyuralarni tekshirish uchun
        Collections.sort(atmBills,Collections.reverseOrder());
        double cashAmount = paymentDto.getAmount();

        //UZS kupyuralar arrayi
        int bills[] = {100_000,50_000,10_000,5_000,1_000};

        //Har bir kupyuradan nechta kerakligini bilish uchun yaratilgan array
        int howMany[] = new int[5];

        //ATM dagi barcha kupyuralar buyicha kiritilgan summaga mos kupyuralar sonini tekshirish
        for (AtmBills atmBill:atmBills) {
            int i = 0;
            if( cashAmount > bills[i]){
                int n = (int) (cashAmount/bills[i]);
                if(atmBill.getQuantity()>=n){
                    cashAmount = cashAmount%bills[i];
                    howMany[i]=n;
                }
            }
            i+=1;
        }

        //Cash Amount har bir siklda kupyuralarga mos ravishda oxirgi qoldiq ga teglashib boradi
        //Agar Cash Amount 0 ga teng bo'lsa ATM da kiritilgan summaga mos kupyuralar mavjud bo'ladi
        if(cashAmount>0){
            return new ApiResponse("Amaliyot amalga oshirilmadi! (Bankomatda pul yo'q ;)",false);
        }

        //CashAmount 0 ga teng. Barcha kupyuralardan nechta kerakligiga qarab ularni
        //sonini ayirib tizimga kamaygan holda saqlaymiz
        for (AtmBills atmBill:atmBills) {
            int i = 0;
            atmBill.setQuantity(atmBill.getQuantity()-howMany[i]);
            atmBillsRepo.save(atmBill);
            i+=1;
        }
        //Card hisobidan yechilgan summani ayirib saqlaymiz
        card.setBalance(card.getBalance()-withDrawAmount);
        cardRepo.save(card);

        //Tizim tarixiga saqlash
        AtmHistory atmHistory = new AtmHistory();
        atmHistory.setAtm(atm);
        atmHistory.setPaymentAmount(withDrawAmount);
        atmHistory.setCardType(card.getCardType());
        atmHistory.setPaymentType("OutCome");
        atmHistory.setCurrency(currencyRepo.findByName("UZS").get());
        atmHistoryRepo.save(atmHistory);

        return new ApiResponse("Naqd pulingizni oling! :)",true);
    }

    //Card balansini to'ldirish
    public ApiResponse fillBalanceCard(PaymentDto paymentDto){
        //Tizimga kirgan cardni olish
        Card card = (Card) SecurityContextHolder.getContext().getAuthentication();

        //ATM texnikasi o'zining id raqamini paymentDto ga qushib yuboradi
        //Ushbu id orqali ATM ni bazadan chaqirish
        Optional<ATM> optionalATM = atmRepo.findById(paymentDto.getATMid());
        if(!optionalATM.isPresent()){
            return new ApiResponse("Bunday idlik ATM mavjud emas",false);
        }
        List<SetOfBill> setOfBillsCash = paymentDto.getSetOfBills();
        Collections.sort(setOfBillsCash,Collections.reverseOrder());
        for (SetOfBill billCash:setOfBillsCash) {
            Optional<MoneyBill> optionalMoneyBill = moneyBillRepo.findById(billCash.getMoneyBillId());
            if(!optionalATM.isPresent())
                return new ApiResponse("Kiritilgan kupyura tizimda mavjud emas!",false);
        }
        List<AtmBills> atmBills = atmBillsRepo.findAllByAtmIdAndCurrencyId(paymentDto.getATMid(), currencyRepo.findByName("UZS").get().getId());
        Collections.sort(atmBills,Collections.reverseOrder());
        int amount = 0;
        for (AtmBills atmBill:atmBills) {
            int i = 0;

            if(atmBill.getBillAmount().equals(setOfBillsCash.get(i))){
                atmBill.setQuantity(atmBill.getQuantity()+setOfBillsCash.get(i).getQuantity());
                atmBillsRepo.save(atmBill);
                i+=1;
                amount = amount + setOfBillsCash.get(i).getQuantity()*atmBill.getBillAmount();
            }
        }
        card.setBalance(card.getBalance()+amount);
        cardRepo.save(card);
        //Tizim tarixiga saqlash
        AtmHistory atmHistory = new AtmHistory();
        atmHistory.setAtm(optionalATM.get());
        atmHistory.setPaymentAmount(amount);
        atmHistory.setCardType(card.getCardType());
        atmHistory.setPaymentType("InCome");
        atmHistory.setCurrency(currencyRepo.findByName("UZS").get());
        atmHistoryRepo.save(atmHistory);
        return new ApiResponse("Balans to'dirildi!",true);
    }
}
