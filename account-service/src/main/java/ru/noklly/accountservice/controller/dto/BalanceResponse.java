package ru.noklly.accountservice.controller.dto;

import java.math.BigDecimal;

public record BalanceResponse(
        BigDecimal balance
) {
}
