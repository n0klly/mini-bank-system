package ru.noklly.accountservice.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferRequest(
        @NotNull
        @Positive
        BigDecimal amount,
        @NotNull
        @Positive
        Long senderAccountId,
        @NotNull
        @Positive
        Long receiverAccountId
) {
}
