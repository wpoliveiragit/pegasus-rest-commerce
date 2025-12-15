package br.com.pegasus.api.rest.commerce.infra.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetricResponseData {

  private String keyDistributionSummary;
  private String status;
  private long runtime;
  private Long responseSize;

}