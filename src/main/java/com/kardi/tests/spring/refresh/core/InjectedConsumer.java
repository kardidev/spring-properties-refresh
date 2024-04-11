package com.kardi.tests.spring.refresh.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InjectedConsumer {

    /**
     * This value changes only when the bean is recreated.
     * We don't want to follow this approach, as it brings many challenges and risks.
     */
    @Value("${some-prefix.some-property-name:-1}")
    Long value;

    @Scheduled(fixedDelay = 5_000)
    private void monitor() {
        log.info("State on {} : {}", System.currentTimeMillis(), value);
    }
}
