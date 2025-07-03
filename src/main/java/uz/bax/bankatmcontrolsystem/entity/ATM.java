package uz.bax.bankatmcontrolsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ATM {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Bank bank;

    @ManyToMany
    private Set<CardType> cardTypeSet;

    @OneToMany
    private Set<AtmBills> atmBills;

    @Column(nullable = false)
    private double commissionIsBelongIncome;

    @Column(nullable = false)
    private double commissionIsBelongOutcome;

    @Column(nullable = false)
    private double commissionIsNotBelongIncome;

    @Column(nullable = false)
    private double commissionIsNotBelongOutcome;

    @Column(nullable = false)
    private double maxOutMoney;

    @Column(nullable = false)
    private double minBalance;

    @ManyToOne
    private Address address;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updateAt;

    @CreatedBy
    private UUID createdBy;

    @LastModifiedBy
    private UUID updatedBy;
}
