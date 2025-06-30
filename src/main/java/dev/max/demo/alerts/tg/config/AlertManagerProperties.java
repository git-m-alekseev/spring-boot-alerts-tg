package dev.max.demo.alerts.tg.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "alertmanager")
public class AlertManagerProperties {
    private String uri;
    private Duration connectTimeout = Duration.ofMillis(500);
    private Duration readTimeout = Duration.ofMillis(2000);
    private Duration keepAliveTimeout = Duration.ofMinutes(5);
    private Map<String, String> staticLabels;

}