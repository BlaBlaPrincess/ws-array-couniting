package com.github.blablaprincess.arraycounting.service.integers;

import com.github.blablaprincess.arraycounting.common.utils.ArrayUtils;
import com.github.blablaprincess.arraycounting.service.ArrayCountingAlgorithm;
import com.github.blablaprincess.arraycounting.service.UnexpectedArrayCountingException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@ConditionalOnProperty(name = "count-alg.int.max.enabled", havingValue = "true")
public class IntegersMax implements ArrayCountingAlgorithm<Integer> {

    @Override
    public double count(Integer[] array) {
        ArrayUtils.validateArrayNotEmpty(array);
        return Arrays.stream(array)
                .max(Integer::compareTo)
                .orElseThrow(UnexpectedArrayCountingException::new);
    }

}
