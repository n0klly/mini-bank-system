package ru.noklly.accountservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.noklly.accountservice.controller.dto.AccountResponse;
import ru.noklly.accountservice.controller.dto.TransferRequest;
import ru.noklly.accountservice.controller.dto.TransferResponse;
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

    @PostMapping("/transfer")
    public TransferResponse transfer(@RequestBody TransferRequest transferRequest){
        return accountService.transfer(transferRequest);
    }

}
