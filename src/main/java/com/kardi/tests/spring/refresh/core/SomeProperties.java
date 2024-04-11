package com.kardi.tests.spring.refresh.core;

import lombok.Getter;
import lombok.Setter;
import lombok.Synchronized;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "some-prefix")
@Setter(onMethod_ = {@Synchronized})
@Getter(onMethod_ = {@Synchronized})
public class SomeProperties {

    private Long somePropertyName;
}
