package com.example.bikecustomservise.api.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApplicationErrorEnum {
    DEFAULT_EXCEPTION(1001, "Problems on the server.Try it another time"),
    USER_NOT_FOUND(2001,"User not found"),
    PREMIUM_CUSTOMER_NOT_FOUND(2003,"Customers with status premium not found"),
    USER_EMAIL_NOT_FOUND(2005, "Customer email when process creating of order should be specified"),
    ORDER_NOT_FOUND(2000,"Order not found in database"),
    ORDER_ALREADY_EXISTS(2006,"Order with this name is now exists."),
    VERIFICATION_FAIL(3002,"User's verification failed"),
    PASSWORD_INPUT_FAILS(3003,"Password inputs incorrect or empty"),
    ACCESS_DENIED(2003,"Access denied"),
    ILLEGAL_STATE(2004,"User's already exists"),
    TOKEN_TIME_EXPIRED(4008,"Access token expired "),
    INCORRECT_INPUT(4001,"The inputs data did not pass validation"),
    USER_PARAMS_INCORRECT(4002,"Request body are invalid or empty"),
    EMPTY_REQUEST(4004,"The request is empty"),
    EMPTY_CUSTOMER_NAME(4005,"A customer's name is empty"),
    NOT_FOUND_CUSTOMER(4006,"A customer is not found now !"),
    TRANSACTIONS_PERIOD_NOT_SET(4007,"Period customer's transaction should be set"),
    TRANSACTION_SUMMARY_NOT_SET(4010,"Customer transactions summaries not prepared");
    private final int code;
    private final String message;
}

