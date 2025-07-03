package uz.bax.bankatmcontrolsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.bek.bankatmcontrolsystem.entity.ATM;
import uz.bek.bankatmcontrolsystem.entity.AtmBills;

import java.util.List;
import java.util.UUID;

public interface AtmBillsRepository extends JpaRepository<AtmBills, UUID> {
    List<AtmBills> findAllByAtmIdAndCurrencyId(UUID atmId,UUID currencyId);

}
