package com.github.blablaprincess.arraycounting.exceptioncoderesolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.blablaprincess.arraycounting.common.exceptions.BusinessException;
import com.github.blablaprincess.arraycounting.common.exceptions.EmptyArrayException;
import com.github.blablaprincess.arraycounting.service.UnexpectedArrayCountingException;
import com.github.blablaprincess.arraycounting.validation.ValidationException;
import com.github.blablaprincess.simpleexh.SimpleExhAutoConfiguration;
import com.github.blablaprincess.simpleexh.errordtobuilder.DefaultErrorDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.opentest4j.AssertionFailedError;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Execution(ExecutionMode.SAME_THREAD)
@WebMvcTest(ExceptionCodeResolverMvcIT.class)
@Import(ExceptionCodeResolver.class)
@ImportAutoConfiguration(SimpleExhAutoConfiguration.class)
class ExceptionCodeResolverMvcIT {

    private static final String TARGET_PACKAGE = "com.github.blablaprincess.arraycounting";

    @MockBean
    private TestConfig.Thrower thrower;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @DisplayName("check if all exceptions processed")
    @ParameterizedTest(name = "{0}")
    @MethodSource("getPackageExceptionCases")
    void checkIfAllExceptionsProcessed(final String description,
                                       final Class<? extends Throwable> packageExceptionType) throws RuntimeException {
        // Arrange
        List<TestParameter> testData = getTestExceptions().collect(Collectors.toList());

        // Act
        for (TestParameter testParam : testData) {
            if (packageExceptionType.isInstance(testParam.exception)) {
                return;
            }
        }

        // Assert
        throw new AssertionFailedError(String.format("Class %s does not have a handling test in ExceptionHandler",
                packageExceptionType.getName()));
    }

    @DisplayName("handle exceptions")
    @ParameterizedTest(name = "{0}")
    @MethodSource("getTestExceptionCases")
    void assertException(final String description, final TestParameter param) throws Exception {
        // Arrange
        doThrow(param.exception).when(thrower).doWork();

        // Act
        String jsonResult = mvc.perform(MockMvcRequestBuilders.get(TestConfig.Controller.DO_STAFF_URI))
                // Assert
                .andExpect(status().is(param.code))
                .andReturn()
                .getResponse()
                .getContentAsString();

        DefaultErrorDto result = mapper.readValue(jsonResult, DefaultErrorDto.class);

        assertEquals(param.exception.getMessage(), result.getMessage());
        assertEquals(param.exception.getClass().getSimpleName(), result.getError());
        assertEquals(TestConfig.Controller.DO_STAFF_URI, result.getPath());

        verify(thrower).doWork();
    }

    private static Stream<Arguments> getPackageExceptionCases() {
        return new Reflections(TARGET_PACKAGE, new SubTypesScanner(false))
                .getSubTypesOf(Throwable.class)
                .stream()
                .filter(item -> item.getPackage().getName().startsWith(TARGET_PACKAGE))
                .map(item -> new Object[]{item.getName().replace(TARGET_PACKAGE, ""), item})
                .map(Arguments::of);
    }

    private static Stream<TestParameter> getTestExceptions() {
        return Stream.of(
                        new TestParameterBuilder().ex(BusinessException.class).code(500),
                        new TestParameterBuilder().ex(EmptyArrayException.class).code(500),
                        new TestParameterBuilder().ex(ValidationException.class).code(400),
                        new TestParameterBuilder().ex(UnexpectedArrayCountingException.class).code(500))
                .map(TestParameterBuilder::build);
    }

    private static Stream<Arguments> getTestExceptionCases() {
        return getTestExceptions().map(item -> new Object[]{
                        item.exception.getClass().getName().replace(TARGET_PACKAGE, ""), item})
                .map(Arguments::of);
    }

    @TestConfiguration
    public static class TestConfig {

        public interface Thrower {
            void doWork() throws RuntimeException;
        }

        @Bean
        public Thrower thrower() {
            return mock(Thrower.class);
        }

        @RestController
        public static class Controller {
            final static String DO_STAFF_URI = "/throw";

            @Autowired
            private Thrower thrower;

            @GetMapping(DO_STAFF_URI)
            @ResponseStatus(OK)
            public void doStuff() throws RuntimeException {
                thrower.doWork();
            }
        }
    }

    private static class TestParameter {
        private Throwable exception;
        private int code;
    }

    private static class TestParameterBuilder {
        private final Random random = new Random();
        private final TestParameter testParameter = new TestParameter();

        private Class<? extends Throwable> exceptionType;
        private String message = UUID.randomUUID().toString();
        private int code = random.nextInt();

        public TestParameterBuilder ex(Class<? extends Throwable> exceptionType) {
            this.exceptionType = exceptionType;
            return this;
        }

        public TestParameterBuilder message(String message) {
            this.message = message;
            return this;
        }

        public TestParameterBuilder code(int code) {
            this.code = code;
            return this;
        }

        @SneakyThrows
        public TestParameter build() {
            testParameter.code = this.code;
            testParameter.exception = this.exceptionType
                    .getConstructor(String.class)
                    .newInstance(this.message);
            return testParameter;
        }
    }

}