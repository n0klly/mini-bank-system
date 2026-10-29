package ru.noklly.accountservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.noklly.accountservice.controller.dto.TransferRequest;
import ru.noklly.accountservice.controller.dto.TransferResponse;
import ru.noklly.accountservice.service.AccountService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transfer")
public class TransferController {
    private final AccountService accountService;

    @PostMapping("/internal")
    public TransferResponse internalTransfer(@Valid @RequestBody TransferRequest transferRequest, @AuthenticationPrincipal Long userId){
        return accountService.internalTransfer(transferRequest, userId);
    }
}
