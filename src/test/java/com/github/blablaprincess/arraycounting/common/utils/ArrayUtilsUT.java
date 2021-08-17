package com.github.blablaprincess.arraycounting.common.utils;

import com.github.blablaprincess.arraycounting.common.exceptions.EmptyArrayException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ArrayUtilsUT {

    private interface TestScenario {
        void test(Object[] array, Object expected);
    }

    private static final TestScenario toDoesNotThrows = (array, expected) -> {
        // Act + Assert
        assertDoesNotThrow(() -> ArrayUtils.validateArrayNotEmpty(array));
    };

    @SuppressWarnings("unchecked")
    private static final TestScenario toThrows = (array, exception) -> {
        // Act + Assert
        assertThrows((Class<Exception>) exception, () -> ArrayUtils.validateArrayNotEmpty(array));
    };

    @DisplayName("validateArrayNotEmpty")
    @ParameterizedTest(name = "with {0}")
    @MethodSource("getValidateArrayNotEmptyCases")
    <T> void validateArrayNotEmpty(String description, Object[] array, TestScenario testScenario, Object expected) {
        testScenario.test(array, expected);
    }

    static Stream<Arguments> getValidateArrayNotEmptyCases() {
        return Stream.of(
                arguments("empty array", new Integer[]   {},              toThrows,        EmptyArrayException.class),
                arguments("char array",  new Character[] {'1', '2', '3'}, toDoesNotThrows, null),
                arguments("num array",   new Integer[]   {1, 2, 3},       toDoesNotThrows, null)
                        );
    }

}