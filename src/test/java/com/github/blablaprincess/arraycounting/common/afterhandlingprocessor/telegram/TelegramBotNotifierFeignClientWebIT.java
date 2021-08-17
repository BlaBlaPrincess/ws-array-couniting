package com.github.blablaprincess.arraycounting.common.afterhandlingprocessor.telegram;

import com.jupiter.tools.spring.test.web.annotation.EnableEmbeddedWebServerTest;
import com.jupiter.tools.spring.test.web.extension.ribbon.RedirectRibbonToEmbeddedWebServer;
import feign.Response;
import lombok.Getter;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Execution(ExecutionMode.SAME_THREAD)
@EnableEmbeddedWebServerTest
@RedirectRibbonToEmbeddedWebServer("tg-notifier")
class TelegramBotNotifierFeignClientWebIT {

    @Autowired
    private TelegramBotNotifierFeignClient feignClient;

    @Test
    void sendNotification() throws IOException {
        // Arrange
        String message = "message";

        // Act
        Response response = feignClient.sendNotification(message);

        // Assert
        String content = IOUtils.toString(response.body().asInputStream(), StandardCharsets.UTF_8);
        assertEquals("response", content);
    }

    @TestConfiguration
    static class TestConfig {
        @Getter
        @RestController
        public static class StorageController {

            @PostMapping("/{message}")
            public void sendNotification(@PathVariable String message,
                                         HttpServletResponse response) throws IOException {
                IOUtils.write("response".getBytes(), response.getOutputStream());
            }
        }
    }

}