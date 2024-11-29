package com.example.Exception;


public class InvalidCouponConditionException extends RuntimeException {
    public InvalidCouponConditionException(String message) {
        super(message);
    }
}
