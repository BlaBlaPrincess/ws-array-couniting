package com.github.blablaprincess.arraycounting.controller;

import com.github.blablaprincess.arraycounting.actions.IntegersCountingAlgorithmsPresenterAction;
import com.github.blablaprincess.arraycounting.common.afterhandlingprocessor.ProcessAfterHandlingWith;
import com.github.blablaprincess.arraycounting.common.afterhandlingprocessor.methodcalls.MethodCallsHistoryAfterHandlingProcessor;
import com.github.blablaprincess.arraycounting.common.afterhandlingprocessor.telegram.TelegramBotNotifierAfterHandlingProcessor;
import com.github.blablaprincess.arraycounting.service.ArrayCountingAlgorithmsPresenterDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@ProcessAfterHandlingWith({
        MethodCallsHistoryAfterHandlingProcessor.class,
        TelegramBotNotifierAfterHandlingProcessor.class
})
public class CountsController {

    private final IntegersCountingAlgorithmsPresenterAction integersCountingAlgorithmsPresenterAction;

    @GetMapping("/int")
    @Operation(summary = "get list of all counting algorithms for array of digits")
    public String[] getCountsForInteger() {
        return integersCountingAlgorithmsPresenterAction.getAlgorithms();
    }

    @GetMapping("/int/{integer}")
    @Operation(summary = "get algorithm calculations for an array of digits")
    public ArrayCountingAlgorithmsPresenterDto getCountsForInteger(@PathVariable int integer) {
        return integersCountingAlgorithmsPresenterAction.getAlgorithmsCounts(integer);
    }

}
