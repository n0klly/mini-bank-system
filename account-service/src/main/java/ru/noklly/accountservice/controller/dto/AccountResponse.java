package ru.noklly.accountservice.controller.dto;

import ru.noklly.accountservice.entity.AccountStatus;

import java.math.BigDecimal;

public record AccountResponse(
        Long id,
        String accountNumber,
        BigDecimal balance,
        String currency,
        AccountStatus status
) {
}
