package ru.noklly.accountservice.controller.dto;

import ru.noklly.accountservice.entity.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
        Long id,
        TransactionType type,
        Long senderAccountId,
        Long receiverAccountId,
        BigDecimal amount,
        String currency,
        String externalReference,
        LocalDateTime createdAt
) {
}