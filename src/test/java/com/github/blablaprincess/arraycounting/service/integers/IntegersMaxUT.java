package com.github.blablaprincess.arraycounting.service.integers;

import com.github.blablaprincess.arraycounting.common.exceptions.EmptyArrayException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class IntegersMaxUT {

    private static final IntegersMax integersMax = new IntegersMax();

    private interface TestScenario {
        void test(Integer[] set, Object expected);
    }

    private static final TestScenario toEquals = (set, expected) -> {
        // Act
        double result = integersMax.count(set);

        // Assert
        assertEquals(expected, result);
    };

    @SuppressWarnings("unchecked")
    private static final TestScenario toThrows = (set, exception) -> {
        // Act + Assert
        assertThrows((Class<Exception>) exception, () -> integersMax.count(set));
    };

    @DisplayName("count")
    @ParameterizedTest(name = "with {0}")
    @MethodSource("countTestCases")
    void count(String description, Integer[] set, TestScenario testScenario, Object expected) {
        testScenario.test(set, expected);
    }

    static Stream<Arguments> countTestCases() {
        return Stream.of(
                arguments("positive set", new Integer[]{1, 2, 3, 4, 10}, toEquals, 10d),
                arguments("single digit", new Integer[]{1},              toEquals, 1d),
                arguments("mixed set",    new Integer[]{20, -10},        toEquals, 20d),
                arguments("empty set",    new Integer[]{},               toThrows, EmptyArrayException.class),
                arguments("zero",         new Integer[]{0},              toEquals, 0d)
                        );
    }

}