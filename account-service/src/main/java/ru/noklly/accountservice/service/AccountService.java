package ru.noklly.accountservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.noklly.accountservice.controller.dto.AccountResponse;
import ru.noklly.accountservice.controller.dto.BalanceResponse;
import ru.noklly.accountservice.controller.dto.TransferRequest;
import ru.noklly.accountservice.controller.dto.TransferResponse;
import ru.noklly.accountservice.entity.Account;
import ru.noklly.accountservice.entity.AccountStatus;
import ru.noklly.accountservice.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    //todo: rewrite transfer
    @Transactional
    public TransferResponse transfer(TransferRequest transferRequest){
        BigDecimal newBalance = withdraw(transferRequest.senderAccountId(), transferRequest.amount()).balance();
        deposit(transferRequest.receiverAccountId(),transferRequest.amount());
        return new TransferResponse(newBalance);
    }

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
    @Transactional
    public BalanceResponse deposit(Long accountId, BigDecimal amount){
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("account does not exist"));
        if(account.getStatus()!=AccountStatus.ACTIVE) throw new RuntimeException("account is not active");
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
        return new BalanceResponse(account.getBalance());
    }
    @Transactional
    public BalanceResponse withdraw(Long accountId, BigDecimal amount){
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("account does not exist"));
        if(account.getStatus()!=AccountStatus.ACTIVE) throw new RuntimeException("account is not active");
        if(amount.compareTo(account.getBalance())>0)throw new RuntimeException("incorrect amount");
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
        return new BalanceResponse(account.getBalance());
    }
}
