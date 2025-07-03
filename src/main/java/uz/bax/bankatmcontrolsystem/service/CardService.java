package uz.bax.bankatmcontrolsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.bek.bankatmcontrolsystem.entity.*;
import uz.bek.bankatmcontrolsystem.entity.enums.CardType;
import uz.bek.bankatmcontrolsystem.payload.*;
import uz.bek.bankatmcontrolsystem.repository.*;

import java.util.*;

@Service
public class CardService implements UserDetailsService {

    @Autowired
    CardRepository cardRepository;
    @Autowired
    BankRepository bankRepository;
    @Autowired
    CardTypeRepository cardTypeRepository;

    private static final long EXPIRE_DATE = 126_227_808_000L; // 4 years
    private static final double INITIAL_BALANCE = 100.0;

    private ApiResponse addCard(CardDto cardDto, CardType cardType){
        Optional<Card> optionalCard = cardRepository.findByCardNumber(cardDto.getCardNumber());
        if(!optionalCard.isPresent())
            return new ApiResponse("Bunday CardNumber tizimda mavjud",false);
        Optional<Bank> optionalBank = bankRepository.findById(cardDto.getBankId());
        if(!optionalBank.isPresent())
            return new ApiResponse("Bunday bank tizimda mavjud emas",false);

        Card card = new Card();
        card.setCardNumber(card.getCardNumber());
        card.setBank(optionalBank.get());
        card.setCvvCode(card.getCvvCode());
        card.setFullName(card.getFullName());
        card.setBalance(INITIAL_BALANCE);
        card.setExpireDate(new Date(System.currentTimeMillis()+EXPIRE_DATE));
        card.setPinCode(card.getPinCode());
        card.setCardType(cardTypeRepository.findByCardTypeName(cardType));
        cardRepository.save(card);
        return new ApiResponse(cardType.name()+" qushildi",true);

    }

    public ApiResponse addUzCard(CardDto cardDto){
        return addCard(cardDto,CardType.UZCARD);
    }
    public ApiResponse addHumo(CardDto cardDto){
        return addCard(cardDto,CardType.HUMO);
    }
    public ApiResponse addVisa(CardDto cardDto){
        return addCard(cardDto,CardType.VISA);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return cardRepository.findByCardNumber(username).orElseThrow(()-> new UsernameNotFoundException(username + " topilmadi"));
    }
}
