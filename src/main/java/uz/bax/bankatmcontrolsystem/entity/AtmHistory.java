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
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AtmHistory {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private ATM atm;

    @Column
    private double paymentAmount;

    @ManyToOne
    private Currency currency;

    @Column(nullable = false)
    private String paymentType;

    @ManyToOne
    private CardType cardType;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updateAt;

    @CreatedBy
    private UUID createdBy;

    @LastModifiedBy
    private UUID updatedBy;
}
