package dev.max.demo.alerts.tg.integration.alertmanager.dto;

import lombok.Builder;

import java.time.ZonedDateTime;
import java.util.Map;

@Builder
public record Alert(
        ZonedDateTime startsAt,
        Map<String, String> labels,
        Map<String, String> annotations
) { }