package uz.bax.bankatmcontrolsystem.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetOfBill {

    private UUID moneyBillId;
    private Integer quantity;
}
