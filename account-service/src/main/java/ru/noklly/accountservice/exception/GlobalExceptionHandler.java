package ru.noklly.accountservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse accountNotFoundException(AccountNotFoundException exception){
        return new ExceptionResponse(HttpStatus.NOT_FOUND,exception.getMessage());
    }
    @ExceptionHandler(NotEnoughBalanceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse notEnoughBalanceException(NotEnoughBalanceException exception){
        return new ExceptionResponse(HttpStatus.BAD_REQUEST,exception.getMessage());
    }
    @ExceptionHandler(SelfTransferException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse selfTransferException(SelfTransferException exception){
        return new ExceptionResponse(HttpStatus.BAD_REQUEST,exception.getMessage());
    }
    @ExceptionHandler(CurrencyMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse currencyMismatchException(CurrencyMismatchException exception){
        return new ExceptionResponse(HttpStatus.BAD_REQUEST,exception.getMessage());
    }
    @ExceptionHandler(AccountInactiveException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse accountInactiveException(AccountInactiveException exception){
        return new ExceptionResponse(HttpStatus.FORBIDDEN,exception.getMessage());
    }
    @ExceptionHandler(AccountAccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse accountAccessDeniedException(AccountAccessDeniedException exception){
        return new ExceptionResponse(HttpStatus.FORBIDDEN,exception.getMessage());
    }
    @ExceptionHandler(InvalidTransferAmountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse invalidTransferAmountException(InvalidTransferAmountException exception){
        return new ExceptionResponse(HttpStatus.BAD_REQUEST,exception.getMessage());
    }
}
