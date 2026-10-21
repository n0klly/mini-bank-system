package ru.noklly.accountservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.noklly.accountservice.controller.dto.AccountResponse;
import ru.noklly.accountservice.controller.dto.BalanceResponse;
import ru.noklly.accountservice.service.AccountService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public List<AccountResponse> getAllUserAccounts(@RequestParam Long userId){
        return accountService.getAllUserAccounts(userId);
    }

}
