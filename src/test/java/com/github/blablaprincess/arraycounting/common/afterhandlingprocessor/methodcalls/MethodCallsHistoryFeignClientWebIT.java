package com.github.blablaprincess.arraycounting.common.afterhandlingprocessor.methodcalls;

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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Execution(ExecutionMode.SAME_THREAD)
@EnableEmbeddedWebServerTest
@RedirectRibbonToEmbeddedWebServer("method-calls")
class MethodCallsHistoryFeignClientWebIT {

    @Autowired
    private MethodCallsHistoryFeignClient feignClient;

    @Test
    void saveMethodCall() throws IOException {
        // Arrange
        String args = "args";
        String method = "method";
        String methodResponse = "response";

        // Act
        Response response = feignClient.saveMethodCall(args, methodResponse, method, true, LocalDateTime.now());

        // Assert
        String content = IOUtils.toString(response.body().asInputStream(), StandardCharsets.UTF_8);
        assertEquals("response", content);
    }

    @TestConfiguration
    static class TestConfig {
        @Getter
        @RestController
        public static class StorageController {

            @PostMapping("/save")
            void saveMethodCall(@RequestParam(required = false) String args,
                                    @RequestParam(required = false) String response,
                                    @RequestParam String method,
                                    @RequestParam Boolean successful,
                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime timestamp,
                                    HttpServletResponse httpResponse) throws IOException {
                IOUtils.write("response".getBytes(), httpResponse.getOutputStream());
            }
        }
    }
}