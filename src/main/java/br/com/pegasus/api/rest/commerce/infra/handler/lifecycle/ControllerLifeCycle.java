package br.com.pegasus.api.rest.commerce.infra.handler.lifecycle;

import br.com.pegasus.api.rest.commerce.infra.handler.marker.ControllerLayerMarker;
import br.com.pegasus.api.rest.commerce.infra.telemetry.MetricsTelemetry;
import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class ControllerLifeCycle implements ContractLifeCycle {

  private final ProceedingJoinPoint pjp;
  private final MetricsTelemetry metricsTelemetry;

  private CompletableFuture<ResponseEntity<?>> responseCompletableFuture;
  private String methodName;
  private String valueAnn;

  public Object LifeCycle() throws Throwable {
    start();
    execute();
    return end();
  }

  private void start() {
    methodName = pjp.getSignature().getName();
    valueAnn = pjp.getTarget().getClass().getAnnotation(ControllerLayerMarker.class).value();
    metricsTelemetry.starts();
    metricsTelemetry.addTraceMessage(ConstUtil.REGEX_TRACE_CONTROLLER_INIT, valueAnn, methodName);
  }

  private void execute() throws Throwable {
    responseCompletableFuture = (CompletableFuture<ResponseEntity<?>>) pjp.proceed();
  }

  private Object end() {
    metricsTelemetry.addTraceMessage(ConstUtil.REGEX_TRACE_CONTROLLER_END, valueAnn, methodName);
    return responseCompletableFuture.thenApply(resp -> {
      metricsTelemetry.send(resp.getStatusCode().value());
      return resp;
    });
  }

}