package com.github.blablaprincess.arraycounting.service;

import com.github.blablaprincess.arraycounting.common.exceptions.BusinessException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnexpectedArrayCountingException extends BusinessException {

    public UnexpectedArrayCountingException(String message) {
        super(message);
    }

}
