package ru.noklly.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.noklly.accountservice.entity.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAccountsByUserID(Long userId);
    Optional<Account> findById(Long id);
    Optional<Account> findAccountById(Long id);
}
