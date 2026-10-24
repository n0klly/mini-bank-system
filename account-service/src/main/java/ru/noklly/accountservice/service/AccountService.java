package ru.noklly.accountservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.noklly.accountservice.controller.dto.AccountResponse;
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
    @Transactional
    public TransferResponse transfer(TransferRequest transferRequest, Long userId){
        if(transferRequest.senderAccountId().compareTo(transferRequest.receiverAccountId())==0) throw new RuntimeException("incorrect receiver");
        Account sender = getActiveAccount(transferRequest.senderAccountId());
        if(sender.getUserID().compareTo(userId)!=0)throw new RuntimeException("jwt eror");
        Account receiver = getActiveAccount(transferRequest.receiverAccountId());
        if(!sender.getCurrency().equals(receiver.getCurrency())) throw new RuntimeException("receiver has different currency");
        if(sender.getBalance().compareTo(transferRequest.amount())<0) throw new RuntimeException("incorrect amount");
        BigDecimal newBalance = withdraw(sender, transferRequest.amount());
        String last4 = deposit(receiver, transferRequest.amount());
        return new TransferResponse(newBalance, last4);
    }
    private Account getActiveAccount(Long accountId){
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("account does not exist"));
        if(account.getStatus()!=AccountStatus.ACTIVE) throw new RuntimeException("account is not active");
        return account;
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
    protected String deposit(Account account, BigDecimal amount)/*add custom exception*/{
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
        return "****"+account.getAccountNumber().substring(account.getAccountNumber().length()-4);
    }
    @Transactional
    protected BigDecimal withdraw(Account account, BigDecimal amount)/*add custom exception*/{
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
        return account.getBalance();
    }
}
