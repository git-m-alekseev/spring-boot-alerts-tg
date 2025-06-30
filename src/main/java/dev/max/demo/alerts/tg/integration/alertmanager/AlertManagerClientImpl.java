package dev.max.demo.alerts.tg.integration.alertmanager;

import dev.max.demo.alerts.tg.config.AlertManagerProperties;
import dev.max.demo.alerts.tg.integration.alertmanager.dto.Alert;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ExtractingResponseErrorHandler;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class AlertManagerClientImpl implements AlertManagerClient {

    public static final String ALERT_MANAGER_REST_CLIENT = "alertmanagerRestClient";

    private final RestClient restClient;

    public AlertManagerClientImpl(
            @Qualifier(ALERT_MANAGER_REST_CLIENT)
            RestClient.Builder restClientBuilder,
            AlertManagerProperties properties
    ) {
        this.restClient = restClientBuilder.baseUrl(properties.getUri())
                .build();
    }

    @Override
    public void sendAlert(Alert alert) {
        var response = restClient.post()
                .uri("/api/v2/alerts")
                .contentType(MediaType.APPLICATION_JSON)
                .body(List.of(alert))
                .retrieve();
        response.onStatus(new DefaultResponseErrorHandler())
                .toBodilessEntity();
    }
}