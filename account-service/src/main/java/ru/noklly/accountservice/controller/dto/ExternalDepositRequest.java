package ru.noklly.accountservice.controller.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record ExternalDepositRequest(
        @NotNull
        @Positive
        Long accountId,
        @NotNull
        @Positive
        BigDecimal amount,
        @NotBlank
        String externalReference
) {
}