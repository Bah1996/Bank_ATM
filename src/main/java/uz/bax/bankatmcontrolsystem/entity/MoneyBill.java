package uz.bax.bankatmcontrolsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MoneyBill {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private Integer billQuantity;

    @ManyToOne
    private Currency currency;

    @ManyToOne
    private AtmBills atmBills;
}
