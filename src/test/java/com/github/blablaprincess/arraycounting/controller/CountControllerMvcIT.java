package com.github.blablaprincess.arraycounting.controller;

import com.github.blablaprincess.arraycounting.actions.IntegersCountingAlgorithmsPresenterAction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.stream.Stream;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Execution(ExecutionMode.SAME_THREAD)
@WebMvcTest(CountsController.class)
class CountControllerMvcIT {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IntegersCountingAlgorithmsPresenterAction integersCountingAlgorithmsPresenterAction;

    @DisplayName("GET /int")
    @Test
    void getAlgorithms() throws Exception {
        // Arrange
        String url = "/int";
        Mockito.when(integersCountingAlgorithmsPresenterAction.getAlgorithms()).thenReturn(null);

        // Act
        mvc.perform(MockMvcRequestBuilders.get(url))
                // Assert
                .andExpect(status().isOk());

        verify(integersCountingAlgorithmsPresenterAction).getAlgorithms();
    }

    @DisplayName("GET algorithms counts")
    @ParameterizedTest(name = "/int/{0}")
    @MethodSource("getAlgorithmsCountsCases")
    void getAlgorithmsCounts(Integer param) throws Exception {
        // Arrange
        String url = "/int/" + param;
        Mockito.when(integersCountingAlgorithmsPresenterAction.getAlgorithmsCounts(param)).thenReturn(null);

        // Act
        mvc.perform(MockMvcRequestBuilders.get(url))
                // Assert
                .andExpect(status().isOk());

        verify(integersCountingAlgorithmsPresenterAction).getAlgorithmsCounts(param);
    }

    private static Stream<Integer> getAlgorithmsCountsCases() {
        return Stream.of(100, -10);
    }

}
