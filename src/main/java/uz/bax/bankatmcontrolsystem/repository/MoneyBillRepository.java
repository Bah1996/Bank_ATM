package uz.bax.bankatmcontrolsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.bek.bankatmcontrolsystem.entity.Currency;
import uz.bek.bankatmcontrolsystem.entity.MoneyBill;

import java.util.Optional;
import java.util.UUID;

public interface MoneyBillRepository extends JpaRepository<MoneyBill, UUID> {

}
