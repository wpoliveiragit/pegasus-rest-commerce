package br.com.pegasus.api.rest.commerce.infra.handler;

import br.com.pegasus.api.rest.commerce.infra.handler.lifecycle.AdviceLifeCycle;
import br.com.pegasus.api.rest.commerce.infra.handler.lifecycle.ComponentLifeCycle;
import br.com.pegasus.api.rest.commerce.infra.handler.lifecycle.ControllerLifeCycle;
import br.com.pegasus.api.rest.commerce.infra.telemetry.HandlerTelemetry;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class RequestLifecycleAspectHandler {

  private final HandlerTelemetry handlerTelemetry;

  @Around("@within(br.com.pegasus.api.rest.commerce.infra.handler.marker.ControllerLayerMarker)")
  public Object controller(ProceedingJoinPoint pjp) throws Throwable {
    return new ControllerLifeCycle(pjp, handlerTelemetry).LifeCycle();
  }

  @Around("@within(br.com.pegasus.api.rest.commerce.infra.handler.marker.ComponentLayerMarker)")
  public Object component(ProceedingJoinPoint pjp) throws Throwable {
    return new ComponentLifeCycle(pjp, handlerTelemetry).LifeCycle();
  }

  @Around("@within(br.com.pegasus.api.rest.commerce.infra.handler.marker.AdviceLayerMarker)")
  public Object advice(ProceedingJoinPoint pjp) throws Throwable {
    return new AdviceLifeCycle(pjp, handlerTelemetry).LifeCycle();
  }

}