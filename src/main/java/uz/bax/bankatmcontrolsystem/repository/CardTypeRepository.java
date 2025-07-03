package uz.bax.bankatmcontrolsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.bek.bankatmcontrolsystem.entity.Card;
import uz.bek.bankatmcontrolsystem.entity.CardType;

import java.util.Optional;
import java.util.UUID;

public interface CardTypeRepository extends JpaRepository<CardType, UUID> {
    CardType findByCardTypeName(CardType cardTypeName);
}
