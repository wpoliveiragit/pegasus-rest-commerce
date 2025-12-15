package br.com.pegasus.api.rest.commerce.infra.handler;

import br.com.pegasus.api.rest.commerce.infra.handler.lifecycle.AdviceLifeCycle;
import br.com.pegasus.api.rest.commerce.infra.handler.lifecycle.ComponentLifeCycle;
import br.com.pegasus.api.rest.commerce.infra.handler.lifecycle.ControllerLifeCycle;
import br.com.pegasus.api.rest.commerce.infra.handler.marker.ComponentLayerMarker;
import br.com.pegasus.api.rest.commerce.infra.telemetry.MetricsTelemetry;
import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class RequestLifecycleAspectHandler {

  private final MetricsTelemetry metricsTelemetry;

  @Around("@within(br.com.pegasus.api.rest.commerce.infra.handler.marker.ControllerLayerMarker)")
  public Object controller(ProceedingJoinPoint pjp) throws Throwable {
    return new ControllerLifeCycle(pjp, metricsTelemetry).LifeCycle();
  }

  @Around("@within(br.com.pegasus.api.rest.commerce.infra.handler.marker.ComponentLayerMarker)")
  public Object component(ProceedingJoinPoint pjp) throws Throwable {
    return new ComponentLifeCycle(pjp, metricsTelemetry).LifeCycle();
  }

  @Around("@within(br.com.pegasus.api.rest.commerce.infra.handler.marker.AdviceLayerMarker)")
  public Object advice(ProceedingJoinPoint pjp) throws Throwable {
    return new AdviceLifeCycle(pjp, metricsTelemetry).LifeCycle();
  }

}