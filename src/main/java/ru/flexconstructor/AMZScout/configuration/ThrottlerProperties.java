package ru.flexconstructor.AMZScout.configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * Application configuration properties.
 */
@Configuration
@ConfigurationProperties(prefix = "throttler")
@NoArgsConstructor
@Validated
@Getter
@Setter
public class ThrottlerProperties {

    /**
     * Timeout duration.
     */
    @DurationUnit(ChronoUnit.MINUTES)
    private Duration timeUnit;

    /**
     * Requests limit.
     */
    private int limit;
}
