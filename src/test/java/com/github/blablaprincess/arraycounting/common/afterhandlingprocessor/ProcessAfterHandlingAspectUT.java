package com.github.blablaprincess.arraycounting.common.afterhandlingprocessor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProcessAfterHandlingAspectUT {

    private final ApplicationContext context = mock(ApplicationContext.class);
    private final ProcessAfterHandlingAspect aspect = new ProcessAfterHandlingAspect(context);

    private final ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
    private final Object[] args = new Object[0];
    private final MethodSignature signature = mock(MethodSignature.class);
    private final ProcessAfterHandlingWith annotation = mock(ProcessAfterHandlingWith.class);
    private final Class<? extends AfterHandlingProcessor> processor = AfterHandlingProcessor.class;
    private final AfterHandlingProcessor processorBean = new AfterHandlingProcessor() {
        public void processOnSuccess(Object[] args, Signature signature, Object response) {
        }

        public void processOnThrows(Object[] args, Signature signature, Throwable throwable) {
        }
    };
    private final AfterHandlingProcessor spyProcessorBean = spy(processorBean);
    private final Method method = mock(Method.class);

    @BeforeEach
    void setup() {
        when(joinPoint.getArgs()).thenReturn(args);
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getMethod()).thenReturn(method);
        when(method.getAnnotation(ProcessAfterHandlingWith.class)).thenReturn(annotation);
        doReturn(new Class[]{processor}).when(annotation).value();
        doReturn(spyProcessorBean).when(context).getBean(processor);
    }

    @Test
    void aroundCallAtDoesNotThrows() throws Throwable {
        // Arrange
        Object response = "value";
        when(joinPoint.proceed(args)).thenReturn(response);

        // Act
        Object result = aspect.aroundCallAt(joinPoint);

        // Assert
        assertEquals(response, result);
        verify(spyProcessorBean).processOnSuccess(args, signature, response);
    }

    @Test
    void aroundCallThrows() throws Throwable {
        // Arrange
        Throwable throwable = new Throwable();
        when(joinPoint.proceed(args)).thenThrow(throwable);

        // Act + Assert
        assertThrows(throwable.getClass(), () -> aspect.aroundCallAt(joinPoint));
        verify(spyProcessorBean).processOnThrows(args, signature, throwable);
    }

}