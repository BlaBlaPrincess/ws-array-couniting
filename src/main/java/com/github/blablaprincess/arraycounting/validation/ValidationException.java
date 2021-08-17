package com.github.blablaprincess.arraycounting.validation;

import com.github.blablaprincess.arraycounting.common.exceptions.BusinessException;

public class ValidationException extends BusinessException {

    public ValidationException() {
        super("Validation error");
    }

    public ValidationException(String message) {
        super(message);
    }

}
