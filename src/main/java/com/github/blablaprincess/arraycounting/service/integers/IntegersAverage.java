package com.github.blablaprincess.arraycounting.service.integers;

import com.github.blablaprincess.arraycounting.common.utils.ArrayUtils;
import com.github.blablaprincess.arraycounting.service.ArrayCountingAlgorithm;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@ConditionalOnProperty(name = "count-alg.int.average.enabled", havingValue = "true")
public class IntegersAverage implements ArrayCountingAlgorithm<Integer> {

    @Override
    public double count(Integer[] array) {
        ArrayUtils.validateArrayNotEmpty(array);
        return (double) Arrays.stream(array)
                .mapToInt(Integer::intValue)
                .sum() / array.length;
    }

}
