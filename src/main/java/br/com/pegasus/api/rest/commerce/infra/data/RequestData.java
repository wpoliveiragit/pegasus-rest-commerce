package br.com.pegasus.api.rest.commerce.infra.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestData {
  private String xTraceId;
  private long currentRequestSize; // "CURRENT REQUEST SIZE: {}: {}B"
  private StringBuilder messageBuild = new StringBuilder();
  private long startRequest;
  private String method;
  private String url;
}
