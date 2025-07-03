package uz.bax.bankatmcontrolsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.bek.bankatmcontrolsystem.entity.AtmBills;
import uz.bek.bankatmcontrolsystem.entity.Bank;

import java.util.List;
import java.util.UUID;

public interface BankRepository extends JpaRepository<Bank, UUID> {

}
