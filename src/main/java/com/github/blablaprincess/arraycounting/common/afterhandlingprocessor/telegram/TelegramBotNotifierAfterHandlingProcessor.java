package com.github.blablaprincess.arraycounting.common.afterhandlingprocessor.telegram;

import com.github.blablaprincess.arraycounting.common.afterhandlingprocessor.AfterHandlingProcessor;
import com.github.blablaprincess.arraycounting.common.afterhandlingprocessor.telegram.messagebuilder.TelegramBotNotifierAfterHandlingProcessorMessageBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.Signature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "telegram-bot.ahp.enabled", havingValue = "true")
public class TelegramBotNotifierAfterHandlingProcessor implements AfterHandlingProcessor {

    private final TelegramBotNotifierFeignClient client;
    private final TelegramBotNotifierAfterHandlingProcessorMessageBuilder messageBuilder;

    @Override
    public void processOnSuccess(Object[] args, Signature signature, Object response) {
        String message = messageBuilder.buildOnSuccess(args, signature, response);
        sendNotification(message);
    }

    @Override
    public void processOnThrows(Object[] args, Signature signature, Throwable throwable) {
        String message = messageBuilder.buildOnThrows(args, signature, throwable);
        sendNotification(message);
    }

    private void sendNotification(String message) {
        try {
            client.sendNotification(message);
        } catch (Exception e) {
            log.error("An exception was thrown while sending the message", e);
        }
    }

}
