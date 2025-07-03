package uz.bax.bankatmcontrolsystem.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDto {

    private String cardNumber;

    private UUID bankId;

    private String cvvCode;

    private String fullName;

    private String pinCode;
}
