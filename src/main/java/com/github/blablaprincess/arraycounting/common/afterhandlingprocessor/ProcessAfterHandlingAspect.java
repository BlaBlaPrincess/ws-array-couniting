package com.github.blablaprincess.arraycounting.common.afterhandlingprocessor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ProcessAfterHandlingAspect {

    private final ApplicationContext context;
    private final Set<Class<? extends AfterHandlingProcessor>> missedBeans = new HashSet<>();

    @Pointcut("@annotation(ProcessAfterHandlingWith)")
    public void annotatedMethod() {}

    @Pointcut("within(@ProcessAfterHandlingWith *)")
    public void annotatedBean() {}

    @Pointcut("execution(* *(..))")
    public void method() {}

    @Around("(method() && annotatedBean()) || annotatedMethod()")
    public Object aroundCallAt(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        Object result = null;
        Throwable throwable = null;

        Method method = ((MethodSignature) signature).getMethod();
        ProcessAfterHandlingWith annotation = method.getAnnotation(ProcessAfterHandlingWith.class);
        if (annotation == null) {
            Class<?> declaringClass = method.getDeclaringClass();
            annotation = declaringClass.getAnnotation(ProcessAfterHandlingWith.class);
        }

        try {
            result = joinPoint.proceed(args);
            return result;
        } catch (Throwable e) {
            throwable = e;
            throw e;
        } finally {
            for (Class<? extends AfterHandlingProcessor> processor : annotation.value()) {
                try {
                    AfterHandlingProcessor processorBean = context.getBean(processor);
                    if (throwable == null) {
                        processorBean.processOnSuccess(args, signature, result);
                    } else {
                        processorBean.processOnThrows(args, signature, throwable);
                    }
                } catch (NoSuchBeanDefinitionException exception) {
                    if (!missedBeans.contains(processor)) {
                        log.warn("No such after dispatching processor bean for " + processor.getSimpleName());
                        missedBeans.add(processor);
                    }
                }
            }
        }
    }

}
