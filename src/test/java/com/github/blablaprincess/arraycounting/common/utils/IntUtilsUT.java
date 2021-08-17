package com.github.blablaprincess.arraycounting.common.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class IntUtilsUT {

    @DisplayName("getLength")
    @ParameterizedTest(name = "with {0}")
    @MethodSource("getLengthTestCases")
    void getLength(String description, Integer num, Integer expected) {
        // Act
        int result = IntUtils.getLength(num);

        // Assert
        assertEquals(expected, result);
    }

    static Stream<Arguments> getLengthTestCases() {
        return Stream.of(
                arguments("positive num",  12345, 5),
                arguments("negative num", -12345, 5),
                arguments("zero",          0,     1)
                        );
    }

}