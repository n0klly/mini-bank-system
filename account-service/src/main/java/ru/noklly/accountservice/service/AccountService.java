package ru.noklly.accountservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.noklly.accountservice.controller.dto.AccountResponse;
import ru.noklly.accountservice.controller.dto.TransferRequest;
import ru.noklly.accountservice.controller.dto.TransferResponse;
import ru.noklly.accountservice.entity.Account;
import ru.noklly.accountservice.entity.AccountStatus;
import ru.noklly.accountservice.exception.*;
import ru.noklly.accountservice.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    @Transactional
    public TransferResponse transfer(TransferRequest transferRequest, Long userId) {
        if (transferRequest==null) throw new IllegalArgumentException("Transfer request must not be null");
        if (userId==null) throw new AccountAccessDeniedException("Authenticated user could not be identified");
        Long senderAccountId = transferRequest.senderAccountId();
        Long receiverAccountId = transferRequest.receiverAccountId();
        BigDecimal amount = transferRequest.amount();
        if (senderAccountId.equals(receiverAccountId)) throw new SelfTransferException("Transfer between the same account is not allowed");
        Account sender = getActiveAccount(senderAccountId);
        if (!Objects.equals(sender.getUserID(), userId)) throw new AccountAccessDeniedException("You do not have permission to transfer funds from this account");
        Account receiver = getActiveAccount(receiverAccountId);
        if (!sender.getCurrency().equals(receiver.getCurrency())) throw new CurrencyMismatchException("Invalid currency");
        if (sender.getBalance().compareTo(amount)<0) throw new NotEnoughBalanceException("Not enough funds to complete the transfer");
        BigDecimal newBalance = withdraw(sender, amount);
        String last4 = deposit(receiver, amount);
        return new TransferResponse(newBalance, last4);
    }

    private Account getActiveAccount(Long accountId){
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException("Account with ID "+accountId+" was not found"));
        if(account.getStatus()!=AccountStatus.ACTIVE) throw new AccountInactiveException("Account with ID "+accountId+" is not active");
        return account;
    }
    public void save(Account account){
        accountRepository.save(account);

    }
    public List<AccountResponse> getAllUserAccounts(Long userId){
        if (userId==null) throw new AccountAccessDeniedException("Authenticated user could not be identified");
        List<Account> accounts = accountRepository.findAccountsByUserID(userId);
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


    protected String deposit(Account account, BigDecimal amount){
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
        return "****"+account.getAccountNumber().substring(account.getAccountNumber().length()-4);
    }

    protected BigDecimal withdraw(Account account, BigDecimal amount){
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
        return account.getBalance();
    }
}
