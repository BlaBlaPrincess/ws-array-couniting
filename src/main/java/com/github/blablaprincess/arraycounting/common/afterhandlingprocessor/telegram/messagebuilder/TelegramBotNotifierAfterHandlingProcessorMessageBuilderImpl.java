package com.github.blablaprincess.arraycounting.common.afterhandlingprocessor.telegram.messagebuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.Signature;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TelegramBotNotifierAfterHandlingProcessorMessageBuilderImpl implements
        TelegramBotNotifierAfterHandlingProcessorMessageBuilder {

    private final ObjectMapper mapper;

    @SneakyThrows
    public String build(Object[] args, Signature signature, Object response, boolean successful) {
        String responseBody = mapper.writeValueAsString(response);
        String message = "App has handled the method:\n" +
                "▫️ Signature: " + signature.toShortString() + "\n" +
                "▫️ " + (args.length == 0 ?
                "Without args" :
                "Args: " + Arrays.stream(args).map(Object::toString).collect(Collectors.joining(", "))) + "\n" +
                "▫️ Ok: " + successful;
        if (successful) {
            JsonNode responseBodyJsonNode = mapper.readTree(responseBody);
            message += " \uD83D\uDFE2\n" +
                    (responseBodyJsonNode.isEmpty() ?
                            "Without response" :
                            "With a response:\n" +
                                    mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseBodyJsonNode));
        } else {
            message += " \uD83D\uDD34\n" +
                    "With exception: " + response.toString();
        }
        return message;

    }

    @Override
    public String buildOnSuccess(Object[] args, Signature signature, Object response) {
        return build(args, signature, response, true);
    }

    @Override
    public String buildOnThrows(Object[] args, Signature signature, Throwable throwable) {
        return build(args, signature, throwable, false);
    }
}
