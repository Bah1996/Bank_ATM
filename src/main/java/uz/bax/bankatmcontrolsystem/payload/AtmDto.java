package uz.bax.bankatmcontrolsystem.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtmDto {

    private UUID bankId;

    private Set<Integer> cardTypeSetIds;

    private double commissionIsBelongIncome;

    private double commissionIsBelongOutcome;

    private double commissionIsNotBelongIncome;

    private double commissionIsNotBelongOutcome;

    private double maxOutMoney;

    private double minBalance;

    private UUID addressId;
}
