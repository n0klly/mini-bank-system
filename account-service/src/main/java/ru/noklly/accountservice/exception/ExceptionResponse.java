package ru.noklly.accountservice.exception;

import org.springframework.http.HttpStatus;

public record ExceptionResponse(
        HttpStatus httpStatus,
      String message
){}
