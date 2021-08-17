package com.github.blablaprincess.arraycounting.service.integers;

import com.github.blablaprincess.arraycounting.common.utils.ArrayUtils;
import com.github.blablaprincess.arraycounting.service.ArrayCountingAlgorithm;
import com.github.blablaprincess.arraycounting.service.UnexpectedArrayCountingException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@ConditionalOnProperty(name = "count-alg.int.min.enabled", havingValue = "true")
public class IntegersMin implements ArrayCountingAlgorithm<Integer> {

    @Override
    public double count(Integer[] array) {
        ArrayUtils.validateArrayNotEmpty(array);
        return Arrays.stream(array)
                .min(Integer::compareTo)
                .orElseThrow(UnexpectedArrayCountingException::new);
    }

}
