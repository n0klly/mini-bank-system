package ru.noklly.accountservice.kafka.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.noklly.accountservice.entity.AccountEntity;
import ru.noklly.accountservice.entity.AccountStatus;
import ru.noklly.accountservice.kafka.event.UserCreatedEvent;
import ru.noklly.accountservice.service.AccountService;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCreatedListener {
    private final AccountService accountService;

    @KafkaListener(topics = "user-created", groupId = "account-group")
    public void handleUserCreated(UserCreatedEvent event) {
        log.info("Received event for userId: {}", event.userId());
        String accountNumber = "ACCNUMB" + UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 12);
        AccountEntity account = new AccountEntity(
                AccountStatus.ACTIVE,
                "RUB",
                BigDecimal.ZERO,
                accountNumber,
                event.userId()
        );
        accountService.save(account);
        log.info("Successfully created account for userId: {}", event.userId());
    }
}
