package br.com.pegasus.api.rest.commerce.infra.data;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class TraceEventLogListData {

  private final AtomicInteger orderSequence = new AtomicInteger(0);

  private final List<TraceEventLogData> trace;
  //request
  private final String traceId;
  private String method;
  private String url;
  private Long requestSize;
  //response
  private String keyDistributionSummary;
  private String status;
  private long runtime;
  private Long responseSize;

  public TraceEventLogListData(String traceId, String method, String url, Long requestSize) {
    this.trace = new ArrayList<>();
    this.traceId = traceId;
    this.method = method;
    this.url = url;
    this.requestSize = requestSize;
  }

  public void addEvent(String message) {
    new TraceEventLogData(orderSequence.incrementAndGet(), Instant.now(), message);
  }

  public void addEvent(Instant timestamp, String message) {
    new TraceEventLogData(orderSequence.incrementAndGet(), timestamp, message);
  }

  public void finish(String status, long runtime, Long responseSize){
    this.status = status;
    this.runtime = runtime;
    this.responseSize = responseSize;
  }

}
