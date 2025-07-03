package uz.bax.bankatmcontrolsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.bek.bankatmcontrolsystem.entity.Role;
import uz.bek.bankatmcontrolsystem.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Role, UUID> {

    Optional<User> findByEmail(String username);

    Optional<User> findByEmailAndEmailCode(String email, String emailCode);
}
