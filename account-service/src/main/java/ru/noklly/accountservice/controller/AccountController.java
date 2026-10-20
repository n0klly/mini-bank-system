package ru.noklly.accountservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.noklly.accountservice.controller.dto.AccountResponse;
import ru.noklly.accountservice.service.AccountService;

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
