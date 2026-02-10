package com.example.bikecustomservise.api.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ServiceProccessingException extends Exception {
    private final int code;
    private final String message;

    public ServiceProccessingException(ApplicationErrorEnum applicationErrorEnum) {
        this.code = applicationErrorEnum.getCode();
        this.message = applicationErrorEnum.getMessage();
    }


}
