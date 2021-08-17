package com.github.blablaprincess.arraycounting.actions;

import com.github.blablaprincess.arraycounting.common.digitsrepresentation.DigitsRepresentation;
import com.github.blablaprincess.arraycounting.service.ArrayCountingAlgorithmsPresenter;
import com.github.blablaprincess.arraycounting.service.ArrayCountingAlgorithmsPresenterDto;
import com.github.blablaprincess.arraycounting.validation.ValidatorsCollection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IntegersCountingAlgorithmsPresenterAction {

    private final ArrayCountingAlgorithmsPresenter<Integer> integersCountingAlgorithmsPresenterService;
    private final ValidatorsCollection<Integer> validators;

    public String[] getAlgorithms() {
        return integersCountingAlgorithmsPresenterService.getAlgorithms();
    }

    public ArrayCountingAlgorithmsPresenterDto getAlgorithmsCounts(int integer) {
        validators.validate(integer);
        Integer[] array = DigitsRepresentation.getDigitsArray(integer);
        return integersCountingAlgorithmsPresenterService.getAlgorithmsCounts(array);
    }

}
