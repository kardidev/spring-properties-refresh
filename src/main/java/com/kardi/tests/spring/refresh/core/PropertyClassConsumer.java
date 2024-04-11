package com.kardi.tests.spring.refresh.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PropertyClassConsumer {

    private final ConfigurationPropertiesBindingPostProcessor propertiesBindingPostProcessor;

    private final SomeProperties someProperties;

    /**
     * Kind of a hacker approach.
     */
    @EventListener
    public void onEnvironmentUpdateEvent(EnvironmentUpdateEvent event) {
        propertiesBindingPostProcessor.postProcessBeforeInitialization(someProperties, "someProperties");
        log.info("Properties have been refreshed");
    }

    @Scheduled(fixedDelay = 5_000)
    private void monitor() {
        log.info("State on {} : {}", System.currentTimeMillis(), someProperties.getSomePropertyName());
    }
}
