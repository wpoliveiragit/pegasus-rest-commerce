package br.com.pegasus.api.rest.commerce.infra.telemetry.logger;

import br.com.pegasus.api.rest.commerce.infra.data.MetricData;
import br.com.pegasus.api.rest.commerce.infra.data.MetricRequestData;
import br.com.pegasus.api.rest.commerce.infra.handler.RequestContextHandler;
import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/** Use esta classe apenas dentro do contexto da request */
@Component
@RequiredArgsConstructor
public class TrackLogger {

  private final RequestContextHandler requestContext;
  private final Logger auditLogger = LoggerFactory.getLogger("CUSTOM_LOG");

  public void append(String message) {
    getMessage().append(ConstUtil.SPACE).append(message);
  }

  public void append(String message, Object... args) {
    getMessage().append(ConstUtil.SPACE).append(format(message, args));
  }

  public void log(MetricData metric) {
    if ((Integer.parseInt(metric.getResponse().getStatus()) / ConstUtil.INT_100) == 2) {
      auditLogger.info(createLogMessage(metric, ConstUtil.METRIC_TRACK_LOG_PATTERN_OK));
      return;
    }
    auditLogger.warn(createLogMessage(metric, ConstUtil.METRIC_TRACK_LOG_PATTERN_FAIL).replaceFirst(" ✕", "\n ✕"));
  }

  private StringBuilder getMessage() {
    try {
      return requestContext.getMetricData().getMessageBuild();
    } catch (ServletException ex) {
      return new StringBuilder();
    }
  }

  private static String createLogMessage(MetricData metric, String pattern) {
    MetricRequestData request = metric.getRequest();
    return format(pattern, //
        request.getXTraceId(),// x-trace-id
        request.getMethod(), request.getUrl(),// url
        request.getRequestSize(),// size request
        metric.getMessageBuild().toString()// message
    );
  }

  private static String format(String message, Object... objs) {
    return ParameterizedMessage.format(message, objs);
  }

}