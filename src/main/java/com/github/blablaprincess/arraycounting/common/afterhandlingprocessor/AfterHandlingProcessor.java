package com.github.blablaprincess.arraycounting.common.afterhandlingprocessor;

import org.aspectj.lang.Signature;

public interface AfterHandlingProcessor {
    void processOnSuccess(Object[] args, Signature signature, Object response);
    void processOnThrows(Object[] args, Signature signature, Throwable throwable);
}
