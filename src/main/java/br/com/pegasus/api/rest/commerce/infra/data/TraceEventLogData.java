package br.com.pegasus.api.rest.commerce.infra.data;

import java.time.Instant;

public record TraceEventLogData(int order, Instant timestamp, String message) {
}
