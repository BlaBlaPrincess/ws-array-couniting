package com.github.blablaprincess.arraycounting.common.afterhandlingprocessor.telegram;

import com.github.blablaprincess.arraycounting.common.afterhandlingprocessor.AfterHandlingProcessor;
import com.github.blablaprincess.arraycounting.common.afterhandlingprocessor.telegram.messagebuilder.TelegramBotNotifierAfterHandlingProcessorMessageBuilder;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.Signature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "telegram-bot.ahp.enabled", havingValue = "true")
public class TelegramBotNotifierAfterHandlingProcessor implements AfterHandlingProcessor {

    private final TelegramBotNotifierFeignClient client;
    private final TelegramBotNotifierAfterHandlingProcessorMessageBuilder messageBuilder;

    @Override
    public void processOnSuccess(Object[] args, Signature signature, Object response) {
        String message = messageBuilder.buildOnSuccess(args, signature, response);
        client.sendNotification(message);
    }

    @Override
    public void processOnThrows(Object[] args, Signature signature, Throwable throwable) {
        String message = messageBuilder.buildOnThrows(args, signature, throwable);
        client.sendNotification(message);
    }
}
