package com.github.blablaprincess.arraycounting.common.afterhandlingprocessor.telegram;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "tg-notifier")
public interface TelegramBotNotifierFeignClient {

    @PostMapping("/{message}")
    Response sendNotification(@PathVariable String message);

}
