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

class MeaningOfLifeValidatorUT {

    private static final MeaningOfLifeValidator validator = new MeaningOfLifeValidator();

    private interface TestScenario {
        void test(Integer value);
    }

    private static final TestScenario toDoesNotThrows = (value) -> {
        // Act + Assert
        assertDoesNotThrow(() -> validator.validate(value));
    };

    private static final TestScenario toThrows = (value) -> {
        // Act + Assert
        assertThrows(ValidationException.class, () -> validator.validate(value));
    };

    @DisplayName("validate")
    @ParameterizedTest(name = "with {0}")
    @MethodSource("getValidateCases")
    void validateArrayNotEmpty(Integer value, TestScenario testScenario) {
        testScenario.test(value);
    }

    static Stream<Arguments> getValidateCases() {
        return Stream.of(
                arguments(99996, toThrows),
                arguments(99997, toDoesNotThrows),
                arguments(42,    toDoesNotThrows)
        );
    }

}