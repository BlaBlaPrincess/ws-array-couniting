package com.github.blablaprincess.arraycounting.exceptioncoderesolver;

import com.github.blablaprincess.arraycounting.common.exceptions.BusinessException;
import com.github.blablaprincess.arraycounting.validation.ValidationException;
import com.github.blablaprincess.simpleexh.exceptioncoderesolver.DefaultExceptionCodeResolver;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ExceptionCodeResolver extends DefaultExceptionCodeResolver {

    private final Map<Class<? extends Throwable>, Integer> exceptionCodeMap
            = new HashMap<Class<? extends Throwable>, Integer>() {{
        put(BusinessException.class, 500);
        put(ValidationException.class, 400);
    }};

    @Override
    public int resolve(Throwable throwable) {
        return exceptionCodeMap.getOrDefault(throwable.getClass(), super.resolve(throwable));
    }
}
