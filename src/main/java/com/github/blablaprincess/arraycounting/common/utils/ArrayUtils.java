package com.github.blablaprincess.arraycounting.common.utils;

import com.github.blablaprincess.arraycounting.common.exceptions.EmptyArrayException;

public class ArrayUtils {

    public static <T> void validateArrayNotEmpty(T[] array) {
        if (array.length == 0) {
            throw new EmptyArrayException();
        }
    }

}
