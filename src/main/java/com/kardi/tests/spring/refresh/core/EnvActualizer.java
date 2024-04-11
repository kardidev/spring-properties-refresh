package com.kardi.tests.spring.refresh.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
@RequiredArgsConstructor
public class EnvActualizer {

    static final String TEST_PROPERTY_SOURCE = "some-property-source-v";
    static final String TEST_PROPERTY_NAME = "some-prefix.some-property-name";

    private final ConfigurableEnvironment environment;
    private final ApplicationEventPublisher eventPublisher;

    private final AtomicLong sourceVersion = new AtomicLong(0);

    @Scheduled(fixedDelay = 5_000)
    private void update() {
        long nextVersion = sourceVersion.incrementAndGet();
        MutablePropertySources propertySources = environment.getPropertySources();
        propertySources.addFirst(new MapPropertySource(
                sourceName(nextVersion),
                Map.of(TEST_PROPERTY_NAME, System.currentTimeMillis())));
        propertySources.remove(sourceName(nextVersion - 1));
        log.info("Property source {} is updated. Total sources: {}", sourceName(nextVersion), propertySources.size());

        eventPublisher.publishEvent(new EnvironmentUpdateEvent());
    }

    private static String sourceName(long version) {
        return TEST_PROPERTY_SOURCE + version;
    }
}
