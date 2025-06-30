package dev.max.demo.alerts.tg.integration.alertmanager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.Instant;
import java.util.Map;

@Builder
public record Alert(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
        Instant startsAt,
        Map<String, String> labels,
        Map<String, String> annotations
) { }