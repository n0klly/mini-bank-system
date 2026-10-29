package ru.noklly.accountservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.noklly.accountservice.controller.dto.TransferRequest;
import ru.noklly.accountservice.controller.dto.TransferResponse;
import ru.noklly.accountservice.entity.Account;
import ru.noklly.accountservice.entity.AccountStatus;
import ru.noklly.accountservice.exception.NotEnoughBalanceException;
import ru.noklly.accountservice.repository.AccountRepository;
import ru.noklly.accountservice.repository.TransactionRepository;
import ru.noklly.accountservice.service.AccountService;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private TransactionRepository transactionRepository;
    @InjectMocks
    private AccountService accountService;
    private Account sender;
    private Account receiver;
    @BeforeEach
    void setUp() {
        sender = new Account(AccountStatus.ACTIVE, "RUB", new BigDecimal("5000.00"), "1111222233334444", 1L);
        sender.setId(1L);
        receiver = new Account(AccountStatus.ACTIVE, "RUB", new BigDecimal("1000.00"), "5555666677778888", 2L);
        receiver.setId(2L);
    }

    @Test
    void internalTransfer_shouldTransferMoneySuccessfully() {
        TransferRequest request = new TransferRequest(new BigDecimal("500.00"), 1L, 2L);
        when(accountRepository.findByIdWithLock(1L)).thenReturn(Optional.of(sender));
        when(accountRepository.findByIdWithLock(2L)).thenReturn(Optional.of(receiver));

        TransferResponse response = accountService.internalTransfer(request, 1L);
        assertEquals(new BigDecimal("4500.00"), sender.getBalance());
        assertEquals(new BigDecimal("1500.00"), receiver.getBalance());
        assertEquals(new BigDecimal("4500.00"), response.balance());
        assertEquals("****8888", response.maskedAccountNumber());
        verify(accountRepository, times(2)).save(any(Account.class));
        verify(transactionRepository, times(1)).save(any());
    }

    @Test
    void internalTransfer_shouldThrowExceptionWhenInsufficientFunds() {
        TransferRequest request = new TransferRequest(new BigDecimal("6000.00"), 1L, 2L);
        when(accountRepository.findByIdWithLock(1L)).thenReturn(Optional.of(sender));
        when(accountRepository.findByIdWithLock(2L)).thenReturn(Optional.of(receiver));
        assertThrows(NotEnoughBalanceException.class, () -> accountService.internalTransfer(request, 1L));
        assertEquals(new BigDecimal("5000.00"), sender.getBalance());
        assertEquals(new BigDecimal("1000.00"), receiver.getBalance());
        verify(accountRepository, never()).save(any(Account.class));
        verify(transactionRepository, never()).save(any());
    }
}