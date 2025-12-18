package br.com.pegasus.api.rest.commerce.infra.handler.lifecycle;

import br.com.pegasus.api.rest.commerce.infra.handler.marker.ComponentLayerMarker;
import br.com.pegasus.api.rest.commerce.infra.telemetry.HandlerTelemetry;
import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import br.com.pegasus.api.rest.commerce.infra.util.TrackUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;

@RequiredArgsConstructor
public class ComponentLifeCycle implements ContractLifeCycle {

  private final ProceedingJoinPoint pjp;
  private final HandlerTelemetry handlerTelemetry;

  private Object ret;
  private String valueAnn;
  private String methodName;

  public Object LifeCycle() throws Throwable {
    start();
    execute();
    return end();
  }

  private void start() {
    valueAnn = pjp.getTarget().getClass().getAnnotation(ComponentLayerMarker.class).value();
    methodName = pjp.getSignature().getName();
    handlerTelemetry.addTraceEvent(ConstUtil.REGEX_TRACE, TrackUtil.START, valueAnn, methodName);
  }

  private void execute() throws Throwable {
    ret = pjp.proceed();
  }

  private Object end() {
    handlerTelemetry.addTraceEvent(ConstUtil.REGEX_TRACE, TrackUtil.FINISH, valueAnn, methodName);
    return ret;
  }

}