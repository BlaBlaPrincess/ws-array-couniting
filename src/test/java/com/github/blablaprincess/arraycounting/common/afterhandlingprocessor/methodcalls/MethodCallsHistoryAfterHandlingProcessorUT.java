package com.github.blablaprincess.arraycounting.common.afterhandlingprocessor.methodcalls;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class MethodCallsHistoryAfterHandlingProcessorUT {

    private final MethodCallsHistoryFeignClient client = mock(MethodCallsHistoryFeignClient.class);
    private final ObjectMapper mapper = mock(ObjectMapper.class);

    private final MethodCallsHistoryAfterHandlingProcessor methodCallsHistoryAfterDispatchingProcessor
            = new MethodCallsHistoryAfterHandlingProcessor(client, mapper);

    private final Object[] args = new Object[]{1, 2};
    private final String sArgs = "1, 2";
    private final String method = "method";
    private final String response = "response";
    private final Signature signature = mock(Signature.class);

    @BeforeEach
    void setup() throws JsonProcessingException {
        when(signature.toShortString()).thenReturn(method);
        when(mapper.writeValueAsString(any(Object.class))).thenReturn(response);
    }

    @Test
    void processOnSuccess() {
        // Act
        methodCallsHistoryAfterDispatchingProcessor.processOnSuccess(args, signature, "");

        // Assert
        verify(client).saveMethodCall(eq(sArgs), eq(method), eq(response), eq(true), any(LocalDateTime.class));
    }

    @Test
    void processOnThrows() {
        // Act
        methodCallsHistoryAfterDispatchingProcessor.processOnThrows(args, signature, new Throwable());

        // Assert
        verify(client).saveMethodCall(eq(sArgs), eq(method), eq(response), eq(false), any(LocalDateTime.class));
    }

}