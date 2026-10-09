package ru.noklly.accountservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.noklly.accountservice.entity.AccountEntity;
import ru.noklly.accountservice.repository.AccountRepository;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
//MVP
    public void save(AccountEntity accountEntity){
        accountRepository.save(accountEntity);
    }
}
