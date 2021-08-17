package com.github.blablaprincess.arraycounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ArrayCountingAlgorithmsPresenterService<T>
        implements ArrayCountingAlgorithmsPresenter<T> {

    private final List<ArrayCountingAlgorithm<T>> algorithms;

    public ArrayCountingAlgorithmsPresenterDto getAlgorithmsCounts(T[] array) {
        Map<String, Double> counts = new HashMap<>();
        for (ArrayCountingAlgorithm<T> alg : algorithms) {
            counts.put(alg.getClass()
                          .getSimpleName(),
                       alg.count(array));
        }

        return new ArrayCountingAlgorithmsPresenterDto(counts);
    }

    public String[] getAlgorithms() {
        String[] result = new String[algorithms.size()];
        for (int i = 0; i < algorithms.size(); i++) {
            result[i] = algorithms.get(i).getClass().getSimpleName();
        }
        return result;
    }

}
