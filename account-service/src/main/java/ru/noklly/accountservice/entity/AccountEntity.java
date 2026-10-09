package ru.noklly.accountservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "accounts")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id", nullable = false)
    private Long userID;
    @Column(name = "account_number", nullable = false)
    private String accountNumber;
    @Column(name = "balance", nullable = false)
    private BigDecimal balance;
    @Column(name = "currency", nullable = false)
    private String currency;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    public AccountEntity(AccountStatus status, String currency, BigDecimal balance, String accountNumber, Long userID) {
        this.status = status;
        this.currency = currency;
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.userID = userID;
    }
}
