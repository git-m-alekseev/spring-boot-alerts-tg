package dev.max.demo.alerts.tg.integration.alertmanager;

import dev.max.demo.alerts.tg.integration.alertmanager.dto.Alert;

public interface AlertManagerClient {
    void sendAlert(Alert alert);
}