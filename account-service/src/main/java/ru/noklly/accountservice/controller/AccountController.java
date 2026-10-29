package ru.noklly.accountservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.noklly.accountservice.controller.dto.AccountResponse;
import ru.noklly.accountservice.controller.dto.TransactionResponse;
import ru.noklly.accountservice.controller.dto.TransferRequest;
import ru.noklly.accountservice.controller.dto.TransferResponse;
import ru.noklly.accountservice.service.AccountService;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public List<AccountResponse> getAllUserAccounts(@NotNull @Positive @AuthenticationPrincipal Long userId){
        return accountService.getAllUserAccounts(userId);
    }
    @GetMapping("/{accountId}")
    public AccountResponse getUserAccount(@PathVariable @NotNull @Positive Long accountId, @AuthenticationPrincipal Long userId) {
        return accountService.getUserAccount(accountId, userId);
    }
    @GetMapping("/{accountId}/transactions")
    public List<TransactionResponse> getAccountTransactions(@PathVariable @Positive Long accountId, @AuthenticationPrincipal Long userId) {
        return accountService.getAccountTransactions(accountId, userId);
    }
}
