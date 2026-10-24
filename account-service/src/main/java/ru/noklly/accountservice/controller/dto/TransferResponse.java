package ru.noklly.accountservice.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record TransferResponse(
        @NotNull
        @PositiveOrZero
        BigDecimal balance,
        @NotNull
        String maskedAccountNumber

) {
}
