package com.github.blablaprincess.arraycounting.common.afterhandlingprocessor.telegram.messagebuilder;

import org.aspectj.lang.Signature;

public interface TelegramBotNotifierAfterHandlingProcessorMessageBuilder {
    String buildOnSuccess(Object[] args, Signature signature, Object response);
    String buildOnThrows(Object[] args, Signature signature, Throwable throwable);
}
