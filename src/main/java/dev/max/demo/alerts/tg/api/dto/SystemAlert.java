package dev.max.demo.alerts.tg.api.dto;

public record SystemAlert(
        String name,
        String reason,
        String source
) {
}
