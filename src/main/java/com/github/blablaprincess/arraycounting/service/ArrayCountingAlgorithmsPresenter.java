package com.github.blablaprincess.arraycounting.service;

public interface ArrayCountingAlgorithmsPresenter<T> {
    ArrayCountingAlgorithmsPresenterDto getAlgorithmsCounts(T[] array);
    String[] getAlgorithms();
}
