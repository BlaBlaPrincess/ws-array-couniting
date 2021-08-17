package com.github.blablaprincess.arraycounting.common.digitsrepresentation;

import com.github.blablaprincess.arraycounting.common.utils.IntUtils;

public class DigitsRepresentation {

    public static Integer[] getDigitsArray(int number) {
        int abs = Math.abs(number);
        int length = IntUtils.getLength(abs);
        Integer[] array = new Integer[length];
        for (int i = 0; i < length; i++) {
            array[length - i - 1] = abs % 10;
            abs /= 10;
        }
        return array;
    }

}
