package ru.noklly.accountservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.noklly.accountservice.controller.dto.AccountResponse;
import ru.noklly.accountservice.entity.Account;
import ru.noklly.accountservice.repository.AccountRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    public void save(Account account){
        accountRepository.save(account);

    }
    public List<AccountResponse> getAllUserAccounts(Long userId){
        List<Account> accounts = accountRepository.findAccountByUserID(userId);
        List<AccountResponse> accountResponses = new ArrayList<>();
        for(Account account: accounts){
            accountResponses.add(new AccountResponse(
                    account.getId(),
                    account.getAccountNumber(),
                    account.getBalance(),
                    account.getCurrency(),
                    account.getStatus()));
        }
        return accountResponses;
    }
}
