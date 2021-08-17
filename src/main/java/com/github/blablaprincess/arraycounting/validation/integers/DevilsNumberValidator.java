package com.github.blablaprincess.arraycounting.validation.integers;

import com.github.blablaprincess.arraycounting.validation.ValidationException;
import com.github.blablaprincess.arraycounting.validation.Validator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "validation.int.devils-number.enabled", havingValue = "true")
public class DevilsNumberValidator implements Validator<Integer> {

    @Override
    public void validate(Integer value) throws ValidationException {
        if (value == 666) {
            throw new ValidationException("The number cannot be 666");
        }
    }

}
