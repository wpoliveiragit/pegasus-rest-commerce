package br.com.pegasus.api.rest.commerce.infra.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetricData {
  private String method;
  private String requestURI;
  private String status;
  private long runtime;
  private Long requestSize;
  private Long responseSize;
  private String url;
  private long startRequest;
  private String xTraceId;
  private long currentRequestSize; // "CURRENT REQUEST SIZE: {}: {}B"
  private StringBuilder messageBuild = new StringBuilder();
  private String keyDistributionSummary;
}
