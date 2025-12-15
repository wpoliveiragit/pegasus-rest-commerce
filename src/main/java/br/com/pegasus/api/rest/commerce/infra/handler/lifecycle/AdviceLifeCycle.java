package br.com.pegasus.api.rest.commerce.infra.handler.lifecycle;

import br.com.pegasus.api.rest.commerce.infra.telemetry.MetricsTelemetry;
import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public class AdviceLifeCycle implements ContractLifeCycle {

  private final ProceedingJoinPoint pjp;
  private final MetricsTelemetry metricsTelemetry;
  private ResponseEntity<?> exBody;

  public Object LifeCycle() throws Throwable {
    start();
    execute();
    return end();
  }

  private void start() {
    for (Object arg : pjp.getArgs()) {
      if (arg instanceof Throwable exParam) {
        metricsTelemetry.addTraceMessage(ConstUtil.REGEX_TRACE_ADVICE, exParam.getMessage());
        break;
      }
    }
  }

  private void execute() throws Throwable {
    exBody = (ResponseEntity<?>) pjp.proceed();
  }

  private Object end() {
    metricsTelemetry.send(exBody.getStatusCode().value());
    return exBody;
  }

}