package uz.bax.bankatmcontrolsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.bek.bankatmcontrolsystem.entity.Card;
import uz.bek.bankatmcontrolsystem.entity.Currency;

import java.util.Optional;
import java.util.UUID;

public interface CurrencyRepository extends JpaRepository<Currency, UUID> {
    Optional<Currency> findByName(String name);
}
