package br.com.pegasus.api.rest.commerce.infra.handler.lifecycle;

import br.com.pegasus.api.rest.commerce.infra.telemetry.HandlerTelemetry;
import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import br.com.pegasus.api.rest.commerce.infra.util.TrackUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public class AdviceLifeCycle implements ContractLifeCycle {

  private final ProceedingJoinPoint pjp;
  private final HandlerTelemetry handlerTelemetry;
  private ResponseEntity<?> exBody;

  public Object LifeCycle() throws Throwable {
    start();
    execute();
    return end();
  }

  private void start() {
    for (Object arg : pjp.getArgs()) {
      if (arg instanceof Throwable exParam) {
        handlerTelemetry.addTraceEvent(ConstUtil.REGEX_TRACE_FAIL, TrackUtil.FAIL, exParam.getMessage());
        break;
      }
    }
  }

  private void execute() throws Throwable {
    exBody = (ResponseEntity<?>) pjp.proceed();
  }

  private Object end() {
    handlerTelemetry.ends(exBody.getStatusCode().value());
    handlerTelemetry.send();
    return exBody;
  }

}