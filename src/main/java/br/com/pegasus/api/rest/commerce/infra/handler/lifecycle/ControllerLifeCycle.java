package br.com.pegasus.api.rest.commerce.infra.handler.lifecycle;

import br.com.pegasus.api.rest.commerce.infra.handler.marker.ControllerLayerMarker;
import br.com.pegasus.api.rest.commerce.infra.telemetry.HandlerTelemetry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.CompletableFuture;

public class ControllerLifeCycle implements ContractLifeCycle {

  private final ProceedingJoinPoint pjp;
  private final HandlerTelemetry handlerTelemetry;

  private final String methodName;
  private final String valueAnn;

  private CompletableFuture<ResponseEntity<?>> responseCompletableFuture;

  public ControllerLifeCycle(ProceedingJoinPoint pjp, HandlerTelemetry handlerTelemetry) {
    this.pjp = pjp;
    this.handlerTelemetry = handlerTelemetry;

    this.methodName = pjp.getSignature().getName();
    this.valueAnn = pjp.getTarget().getClass().getAnnotation(ControllerLayerMarker.class).value();
  }

  public Object LifeCycle() throws Throwable {
    start();
    execute();
    return end();
  }

  private void start() {
    handlerTelemetry.starts();
    handlerTelemetry.addTraceEvent("START: " + valueAnn + "#" + methodName);
  }

  private void execute() throws Throwable {
    responseCompletableFuture = (CompletableFuture<ResponseEntity<?>>) pjp.proceed();
  }

  private Object end() {
    handlerTelemetry.addTraceEvent("END: " + valueAnn + "#" + methodName);
    return responseCompletableFuture.thenApply(resp -> {
      handlerTelemetry.ends(resp.getStatusCode().value());
      handlerTelemetry.send();
      return resp;
    });
  }

}