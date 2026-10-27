package ru.noklly.userservice.exception;

import org.springframework.http.HttpStatus;

public record ExceptionResponse(
        HttpStatus httpStatus,
      String message
){}
