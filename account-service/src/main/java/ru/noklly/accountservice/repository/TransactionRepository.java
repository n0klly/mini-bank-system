package ru.noklly.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.noklly.accountservice.entity.Transaction;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    boolean existsByExternalReference(String externalReference);
    List<Transaction> findByReceiverAccount_IdOrSenderAccount_IdOrderByCreatedAtDesc(Long receiverAccountId, Long senderAccountId);
}
