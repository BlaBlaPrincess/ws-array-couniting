package com.github.blablaprincess.arraycounting.validation.integers;

import com.github.blablaprincess.arraycounting.common.digitsrepresentation.DigitsRepresentation;
import com.github.blablaprincess.arraycounting.service.integers.IntegersSum;
import com.github.blablaprincess.arraycounting.validation.ValidationException;
import com.github.blablaprincess.arraycounting.validation.Validator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "validation.int.meaning-of-life.enabled", havingValue = "true")
public class MeaningOfLifeValidator implements Validator<Integer> {

    @Override
    public void validate(Integer value) throws ValidationException {
        Integer[] array = DigitsRepresentation.getDigitsArray(value);
        if (new IntegersSum().count(array) == 42) {
            throw new ValidationException("The sum of the digits of the number cannot be equal to 42");
        }
    }

}
