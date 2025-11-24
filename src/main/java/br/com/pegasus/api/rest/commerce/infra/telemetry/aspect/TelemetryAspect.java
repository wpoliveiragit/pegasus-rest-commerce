package br.com.pegasus.api.rest.commerce.infra.telemetry.aspect;

import br.com.pegasus.api.rest.commerce.infra.telemetry.MetricsTelemetry;
import br.com.pegasus.api.rest.commerce.infra.telemetry.aspect.mark.TelemetryComponentMark;
import br.com.pegasus.api.rest.commerce.infra.telemetry.aspect.mark.TelemetryControllerMark;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.concurrent.CompletableFuture;

@Aspect
@Component
@RequiredArgsConstructor
public class TelemetryAspect {

  private final MetricsTelemetry metricsTelemetry;

  @Around("@within(br.com.pegasus.api.rest.commerce.infra.telemetry.aspect.mark.TelemetryControllerMark)")
  public Object controller(ProceedingJoinPoint pjp) throws Throwable {
    metricsTelemetry.starts();

    String method = getMethodName(pjp);
    String value = getAnnotationClass(pjp, TelemetryControllerMark.class).value();

    beforeProceed(method, value);
    CompletableFuture<ResponseEntity<?>> future = (CompletableFuture<ResponseEntity<?>>) pjp.proceed();
    afterProceed(method, value);

    return future.thenApply(response -> {
      metricsTelemetry.send(response.getStatusCode().value());
      return response;
    });
  }

  @Around("@within(br.com.pegasus.api.rest.commerce.infra.telemetry.aspect.mark.TelemetryComponentMark)")
  public Object component(ProceedingJoinPoint pjp) throws Throwable {
    String method = getMethodName(pjp);
    String value = getAnnotationClass(pjp, TelemetryComponentMark.class).value();

    beforeProceed(method, value);
    Object ret = pjp.proceed();
    afterProceed(method, value);
    return ret;
  }

  @Around("@within(br.com.pegasus.api.rest.commerce.infra.telemetry.aspect.mark.TelemetryAdviceMark)")
  public Object advice(ProceedingJoinPoint pjp) throws Throwable {
    ResponseEntity<?> exBody = (ResponseEntity<?>) pjp.proceed();
    for (Object arg : pjp.getArgs()) {
      if (arg instanceof Throwable param) {
        metricsTelemetry.addTraceMessage("★ Advice({}): {}", getMethodName(pjp), param.getMessage());
        break;
      }
    }
    metricsTelemetry.send(exBody.getStatusCode().value());
    return exBody;
  }

  private void beforeProceed(String method, String value) {
    metricsTelemetry.addTraceMessage("● {}({})", value, method);
  }

  private void afterProceed(String method, String value) {
    metricsTelemetry.addTraceMessage("◎ {}({})", value, method);
  }

  private String getMethodName(ProceedingJoinPoint pjp) {
    return pjp.getSignature().getName();
  }

  private <T extends Annotation> T getAnnotationClass(ProceedingJoinPoint pjp, Class<T> annotationClass) {
    return pjp.getTarget().getClass().getAnnotation(annotationClass);
  }


}
