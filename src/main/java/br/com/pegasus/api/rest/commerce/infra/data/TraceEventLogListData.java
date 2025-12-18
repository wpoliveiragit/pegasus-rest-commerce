package br.com.pegasus.api.rest.commerce.infra.data;

import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import br.com.pegasus.api.rest.commerce.infra.util.MethodUtil;
import io.micrometer.core.instrument.Tag;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class TraceEventLogListData {

  private final AtomicInteger orderSequence = new AtomicInteger(0);

  //request
  private final String traceId;
  private final String method;
  private final String url;
  private final Long requestSize;
  private final long requestStartTime;

  //response
  private String keyDistributionSummary;
  private String status;
  private long runtime;
  private Long responseSize;
  private final List<TraceEventLogData> trace;

  @Setter
  private List<Tag> tags;

  public TraceEventLogListData() {
    trace = new ArrayList<>();
    traceId = "";
    method = "";
    url = "";
    requestSize = 0L;
    requestStartTime = 0;
  }

  public TraceEventLogListData(String traceId, String method, String url, Long requestSize) {
    this.trace = new ArrayList<>();
    this.traceId = traceId;
    this.method = method;
    this.url = url;
    this.requestSize = requestSize;
    this.requestStartTime = System.currentTimeMillis();
  }

  public void addEvent(String message) {
    trace.add(new TraceEventLogData(orderSequence.incrementAndGet(), Instant.now().toString(), message));
  }

  public void addEvent(Instant timestamp, String message) {
    trace.add(new TraceEventLogData(orderSequence.incrementAndGet(), timestamp.toString(), message));
  }

  public void finish(int status, Long responseSize) {
    keyDistributionSummary = method + url + status;
    this.status = status + "";
    this.runtime = System.currentTimeMillis() - requestStartTime;
    this.responseSize = responseSize;
    this.tags = List.of(//
        Tag.of(ConstUtil.METRIC_TAG_METHOD, method),//
        Tag.of(ConstUtil.METRIC_TAG_URL, url),//
        Tag.of(ConstUtil.METRIC_TAG_STATUS, this.status)//
    );
  }

  @Override
  public String toString(){
    return MethodUtil.toJson(this);
  }

}
