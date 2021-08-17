package com.github.blablaprincess.arraycounting.common.afterhandlingprocessor.methodcalls;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.blablaprincess.arraycounting.common.afterhandlingprocessor.AfterHandlingProcessor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.Signature;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class MethodCallsHistoryAfterHandlingProcessor implements AfterHandlingProcessor {

    private final MethodCallsHistoryFeignClient client;
    private final ObjectMapper mapper;

    @SneakyThrows
    public void process(Object[] args, Signature signature, Object response, boolean successful) {

        String argsString = Arrays.stream(args).map(Object::toString).collect(Collectors.joining(", "));
        String responseBody = mapper.writeValueAsString(response);
        LocalDateTime timestamp = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);

        try {
            client.saveMethodCall(
                    argsString,
                    signature.toShortString(),
                    responseBody,
                    successful,
                    timestamp);
        } catch (Exception e) {
            log.error("An exception was thrown while saving the method call", e);
        }
    }

    @Override
    public void processOnSuccess(Object[] args, Signature signature, Object response) {
        process(args, signature, response, true);
    }

    @Override
    public void processOnThrows(Object[] args, Signature signature, Throwable throwable) {
        process(args, signature, throwable, false);
    }
}
