package dev.max.demo.alerts.tg.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.max.demo.alerts.tg.api.dto.SystemAlert;
import dev.max.demo.alerts.tg.integration.alertmanager.AlertManagerClient;
import dev.max.demo.alerts.tg.integration.alertmanager.dto.Alert;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

import static dev.max.demo.alerts.tg.config.RestClientConfig.REST_CLIENT_OM;

@RestController
@RequestMapping("/api/v1/alerts")
public class AlertingController {

    private final ObjectMapper objectMapper;
    private final AlertManagerClient alertManagerClient;

    public AlertingController(
            @Qualifier(REST_CLIENT_OM)
            ObjectMapper objectMapper,
            AlertManagerClient alertManagerClient) {
        this.objectMapper = objectMapper;
        this.alertManagerClient = alertManagerClient;
    }

    @PostMapping("/send")
    public void sendAlert(@RequestBody SystemAlert systemAlert) {
        var alert = Alert.builder()
                .startsAt(ZonedDateTime.now(ZoneId.of("Europe/Moscow")))
                .labels(Map.of(
                        "name", systemAlert.name(),
                        "reason", systemAlert.reason(),
                        "source", systemAlert.source()
                ))
                .annotations(Map.of(
                        "summary", String.format("Alert: %s, Reason: %s, Source: %s", systemAlert.name(), systemAlert.reason(), systemAlert.source())
                ))
                .build();
        try {
            var str = objectMapper.writeValueAsString(alert);
            System.out.println(str);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize alert", e);
        }
        alertManagerClient.sendAlert(alert);
    }
}
