package ru.noklly.accountservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.noklly.accountservice.controller.dto.ExternalDepositRequest;
import ru.noklly.accountservice.controller.dto.TransferResponse;
import ru.noklly.accountservice.service.AccountService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/deposits")
public class ExternalDepositController {

    private final AccountService accountService;
    @PostMapping("/external")
    public TransferResponse externalDeposit(@Valid @RequestBody ExternalDepositRequest request) {
        return accountService.externalDeposit(request);
    }
}