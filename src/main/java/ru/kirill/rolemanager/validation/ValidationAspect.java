package ru.kirill.rolemanager.validation;

import jakarta.validation.ConstraintViolation;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.kirill.rolemanager.exception.ValidationException;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Aspect
@Component
@RequiredArgsConstructor
public class ValidationAspect {

    private final Validator validator;

    @Pointcut("execution(public * (@jakarta.jws.WebService *).*(..))")
    public void webServiceWebMethods() {}

    @SneakyThrows
    @Before("webServiceWebMethods()")
    public void validateWebService(JoinPoint jp) {
        String message = getErrorMessage(jp);
        if (!message.isEmpty()) {
            throw new ValidationException(message);
        }
    }

    private String getErrorMessage(JoinPoint jp) {
        return Arrays
                .stream(jp.getArgs())
                .map(validator::validate)
                .flatMap(Collection::stream)
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("; "));
    }
}
