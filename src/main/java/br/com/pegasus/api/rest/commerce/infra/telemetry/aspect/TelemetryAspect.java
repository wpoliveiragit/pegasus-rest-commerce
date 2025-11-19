package br.com.pegasus.api.rest.commerce.infra.telemetry.aspect;

import br.com.pegasus.api.rest.commerce.infra.telemetry.MetricsTelemetry;
import br.com.pegasus.api.rest.commerce.infra.telemetry.aspect.mark.TelemetryComponentMark;
import br.com.pegasus.api.rest.commerce.infra.telemetry.aspect.mark.TelemetryControllerMark;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
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

    metricsTelemetry.addTraceMessage(" ● {}.{}", getAnnotationClass(pjp, TelemetryControllerMark.class).value(), method);
    CompletableFuture<ResponseEntity<?>> future = (CompletableFuture<ResponseEntity<?>>) pjp.proceed();

    return future.thenApply(response -> {
      int status = response.getStatusCode().value();
      metricsTelemetry.addTraceMessage("◎ {}({})", getAnnotationClass(pjp, TelemetryControllerMark.class).value(), method);
      metricsTelemetry.send(status);
      return response;
    });
  }

  @Around("@within(br.com.pegasus.api.rest.commerce.infra.telemetry.aspect.mark.TelemetryAdviceMark)")
  public Object advice(ProceedingJoinPoint pjp) throws Throwable {
    ResponseEntity<?> exBody = (ResponseEntity<?>) pjp.proceed();

    for (Object arg : getMethodArgs(pjp)) {
      if (arg instanceof Throwable param) {
        metricsTelemetry.addTraceMessage("★ Advice({}): {}", getMethodName(pjp), param.getMessage());
        break;
      }
    }

    metricsTelemetry.send(exBody.getStatusCode().value());
    return exBody;
  }

  @Around("@within(br.com.pegasus.api.rest.commerce.infra.telemetry.aspect.mark.TelemetryComponentMark)")
  public Object method(ProceedingJoinPoint pjp) throws Throwable {
    String method = getMethodName(pjp);
    metricsTelemetry.addTraceMessage(" ● {}.{}", getAnnotationClass(pjp, TelemetryComponentMark.class).value(), method);
    Object ret = pjp.proceed();
    metricsTelemetry.addTraceMessage(" ◎ {}.{}", getAnnotationClass(pjp, TelemetryComponentMark.class).value(), method);
    return ret;
  }

  private Object[] getMethodArgs(ProceedingJoinPoint pjp) {
    return pjp.getArgs();
  }

  private String getMethodName(ProceedingJoinPoint pjp) {
    return pjp.getSignature().getName();
  }

  private <T extends Annotation> T getAnnotationClass(ProceedingJoinPoint pjp, Class<T> annotationClass) {
    return pjp.getTarget().getClass().getAnnotation(annotationClass);
  }

  private <T extends Annotation> T getAnnotationMethod(ProceedingJoinPoint pjp, Class<T> annotationClass) {
    return ((MethodSignature) pjp.getSignature()).getMethod().getAnnotation(annotationClass);
  }

}
