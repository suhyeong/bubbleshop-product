package com.bubbleshop.exception;

import com.bubbleshop.constants.ResponseCode;

public class InvalidTypeException extends ApiException {
    public InvalidTypeException(ResponseCode responseCode) {
        super(responseCode);
    }
}
