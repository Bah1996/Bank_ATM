package uz.bax.bankatmcontrolsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.bek.bankatmcontrolsystem.entity.enums.RoleName;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(value = EnumType.STRING)
    private RoleName roleName;

}
