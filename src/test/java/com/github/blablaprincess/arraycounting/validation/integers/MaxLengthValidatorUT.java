package com.github.blablaprincess.arraycounting.validation.integers;

import com.github.blablaprincess.arraycounting.validation.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class MaxLengthValidatorUT {

    private interface TestScenario {
        void test(Integer value, int maxLength);
    }

    private static final TestScenario toDoesNotThrows = (value, maxLength) -> {
        // Act + Assert
        assertDoesNotThrow(() -> new MaxLengthValidator(maxLength).validate(value));
    };

    private static final TestScenario toThrows = (value, maxLength) -> {
        // Act + Assert
        assertThrows(ValidationException.class, () -> new MaxLengthValidator(maxLength).validate(value));
    };

    @DisplayName("validate")
    @ParameterizedTest(name = "with {0}, max length: {1}")
    @MethodSource("getValidateCases")
    void validateArrayNotEmpty(Integer value, int maxLength, TestScenario testScenario) {
        testScenario.test(value, maxLength);
    }

    static Stream<Arguments> getValidateCases() {
        return Stream.of(
                arguments(12345,  4, toThrows),
                arguments(1234,   4, toDoesNotThrows),
                arguments(1234,   3, toThrows),
                arguments(123,    3, toDoesNotThrows),
                arguments(0,     -1, toThrows)
        );
    }

}