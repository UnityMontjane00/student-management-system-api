package com.example.venussms.config;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Predicate;

@Component
public class StudentStatusPredicate implements Predicate<BigDecimal> {

    private static final BigDecimal PASSING_MARK = BigDecimal.valueOf(65);

    @Override
    public boolean test(BigDecimal averageMark) {
        Objects.requireNonNull(averageMark, "Average mark must not be null");

        return averageMark.compareTo(PASSING_MARK) > 0;
    }
}