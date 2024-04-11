package com.kardi.tests.spring.refresh.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.kardi.tests.spring.refresh.core.EnvActualizer.TEST_PROPERTY_NAME;

@Slf4j
@Component
@RequiredArgsConstructor
public class EnvironmentConsumer {

    private final Environment environment;

    @Scheduled(fixedDelay = 5_000)
    private void monitor() {
        String value = environment.getProperty(TEST_PROPERTY_NAME);
        log.info("State on {} : {}", System.currentTimeMillis(), value);
    }
}
