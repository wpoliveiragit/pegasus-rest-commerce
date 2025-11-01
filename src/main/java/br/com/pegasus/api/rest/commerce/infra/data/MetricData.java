package br.com.pegasus.api.rest.commerce.infra.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetricData {
  private long startData;
  private String method;
  private String requestURI;
  private String status;
  private long runtime;
  private Long requestSize;
  private Long responseSize;
}
