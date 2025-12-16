package br.com.pegasus.api.rest.commerce.infra.telemetry;

import br.com.pegasus.api.rest.commerce.infra.data.TraceEventLogListData;
import br.com.pegasus.api.rest.commerce.infra.telemetry.logger.TrackLogger;
import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import br.com.pegasus.api.rest.commerce.infra.util.MethodUtil;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

@RequiredArgsConstructor
public class RegisterMetricsTelemetry {

  private final MeterRegistry meterRegistry;
  private final Map<String, DistributionSummary> reqSizeCache = new ConcurrentHashMap<>();
  private final Map<String, DistributionSummary> resSizeCache = new ConcurrentHashMap<>();

  /** Contador de requisições */
  public void counterRegister(List<Tag> tags) {
    Counter.builder(ConstUtil.METRIC_COUNTER_NAME)//
        .tags(tags)//
        .register(meterRegistry).increment();
  }

  /** Tempo da requisição */
  public void timerRegister(List<Tag> tags, long runtime) {
    Timer.builder(ConstUtil.METRIC_TIME_NAME)//
        .tags(tags)//
        .register(meterRegistry)//
        .record(runtime, TimeUnit.MILLISECONDS);
  }

  /** Quantidade de requisições em andamento */
  public void gaugeRegister(LongAdder activeRequests) {
    Gauge.builder(ConstUtil.METRIC_GAUGE_NAME, activeRequests, LongAdder::sum)//
        .description(ConstUtil.METRIC_GAUGE_DESC)//
        .register(meterRegistry);
  }

  /** Request/Response size — com cache */
  public void distributionSummaryRegister(List<Tag> tags, String key, Long requestSize) {
    long amount = (requestSize == null) ? ConstUtil.INT_0 : requestSize;

    reqSizeCache.computeIfAbsent(key, k -> createSummary(ConstUtil.METRIC_REQ_SIZE_NAME, tags)).record(amount);
    resSizeCache.computeIfAbsent(key, k -> createSummary(ConstUtil.METRIC_RES_SIZE_NAME, tags)).record(amount);
  }

  public void tracelogRegister(TraceEventLogListData traceEventLogListData) {
    if ((Integer.parseInt(traceEventLogListData.getStatus()) / ConstUtil.INT_100) < 400) {
      TrackLogger.TRACE_LOG.info(MethodUtil.toJson(traceEventLogListData));
      return;
    }
    TrackLogger.TRACE_LOG.warn(MethodUtil.toJson(traceEventLogListData));
  }

  /** Cria um DistributionSummary com tags padrão */
  private DistributionSummary createSummary(String name, List<Tag> tags) {
    return DistributionSummary.builder(name).tags(tags).register(meterRegistry);
  }

}