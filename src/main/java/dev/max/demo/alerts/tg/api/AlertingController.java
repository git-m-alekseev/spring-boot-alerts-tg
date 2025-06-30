package dev.max.demo.alerts.tg.api;

import dev.max.demo.alerts.tg.api.dto.SystemAlert;
import dev.max.demo.alerts.tg.integration.alertmanager.AlertManagerClient;
import dev.max.demo.alerts.tg.integration.alertmanager.dto.Alert;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/alerts")
@RequiredArgsConstructor
public class AlertingController {

    private final AlertManagerClient alertManagerClient;

    @PostMapping("/send")
    public void sendAlert(@RequestBody SystemAlert systemAlert) {
        var alert = Alert.builder()
                .startsAt(Instant.now())
                .labels(Map.of(
                        "name", systemAlert.name(),
                        "reason", systemAlert.reason(),
                        "source", systemAlert.source()
                ))
                .annotations(Map.of(
                        "summary", String.format("Alert: %s, Reason: %s, Source: %s", systemAlert.name(), systemAlert.reason(), systemAlert.source())
                ))
                .build();
        alertManagerClient.sendAlert(alert);
    }
}
