package br.com.pegasus.api.rest.commerce.infra.handler.lifecycle;

import br.com.pegasus.api.rest.commerce.infra.handler.marker.ComponentLayerMarker;
import br.com.pegasus.api.rest.commerce.infra.telemetry.MetricsTelemetry;
import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;

@RequiredArgsConstructor
public class ComponentLifeCycle implements ContractLifeCycle {

  private final ProceedingJoinPoint pjp;
  private final MetricsTelemetry metricsTelemetry;

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
    metricsTelemetry.addTraceMessage(ConstUtil.REGEX_TRACE_COMPONENT_INIT, valueAnn, methodName);
  }

  private void execute() throws Throwable {
    ret = pjp.proceed();
  }

  private Object end() {
    metricsTelemetry.addTraceMessage(ConstUtil.REGEX_TRACE_COMPONENT_END, valueAnn, methodName);
    return ret;
  }

}
