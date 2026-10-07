package ru.noklly.userservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.noklly.userservice.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
     boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
