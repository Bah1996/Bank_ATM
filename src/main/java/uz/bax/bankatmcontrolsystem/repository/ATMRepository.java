package uz.bax.bankatmcontrolsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.bek.bankatmcontrolsystem.entity.ATM;

import java.util.List;
import java.util.UUID;

public interface ATMRepository extends JpaRepository<ATM, UUID> {
    List<ATM> findAllByBank_Id(UUID bankId);
    boolean existsByBank_IdAndId(UUID bankId,UUID atmId);

}
