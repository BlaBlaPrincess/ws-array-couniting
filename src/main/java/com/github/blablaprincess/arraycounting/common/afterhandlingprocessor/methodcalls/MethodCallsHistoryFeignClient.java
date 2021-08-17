package com.github.blablaprincess.arraycounting.common.afterhandlingprocessor.methodcalls;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@FeignClient(name = "method-calls")
public interface MethodCallsHistoryFeignClient {

    @PostMapping("/save")
    Response saveMethodCall(@RequestParam(required = false) String args,
                            @RequestParam(required = false) String response,
                            @RequestParam String method,
                            @RequestParam Boolean successful,
                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime timestamp);

}
