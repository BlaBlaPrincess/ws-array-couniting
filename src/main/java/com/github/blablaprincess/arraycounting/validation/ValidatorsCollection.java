package com.github.blablaprincess.arraycounting.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ValidatorsCollection<T> implements Validator<T> {

    private final List<Validator<T>> validators;

    @Override
    public void validate(T value) throws ValidationException {
        for (Validator<T> validator : validators) {
            validator.validate(value);
        }
    }

}
