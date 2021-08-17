package com.github.blablaprincess.arraycounting.validation;

public interface Validator<T> {
    void validate(T value) throws ValidationException;
}
