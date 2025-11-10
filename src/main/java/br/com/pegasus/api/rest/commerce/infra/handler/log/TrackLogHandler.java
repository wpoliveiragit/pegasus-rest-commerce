package br.com.pegasus.api.rest.commerce.infra.handler.log;

import br.com.pegasus.api.rest.commerce.infra.data.MetricData;
import br.com.pegasus.api.rest.commerce.infra.handler.RequestContextHandler;
import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.springframework.stereotype.Component;

/** Use esta classe apenas dentro do contexto da request */
@Log4j2
@Component
@RequiredArgsConstructor
public class TrackLogHandler {

  private final RequestContextHandler requestContextHandler;

  private static final String MESSAGE_LOG_PATTERN_OK = "[x-trace-id: {}] [Url: {} {}] [size request: {}] [message: {}]";
  private static final String MESSAGE_LOG_PATTERN_FAIL = "[x-trace-id: {}]\n[Url: {} {}] [size request: {}]\n[trace]{}";

  public void appendMessageLog(String message) {
    appendMessageLogCheck().append(ConstUtil.T_SPACE).append(message);
  }

  public void log() {
    try {
      MetricData metricData = requestContextHandler.getMetricData();
      int baseCode = Integer.parseInt(metricData.getStatus()) / ConstUtil.INT_100;
      if (baseCode == 2) {
        log.info(createLogMessage(MESSAGE_LOG_PATTERN_OK));
        return;
      }
      log.warn(createLogMessage(MESSAGE_LOG_PATTERN_FAIL)//
//          .replace(" ●", "\n ●")//
//          .replace(" ◎", "\n ◎")//
          .replaceFirst(" ✕", "\n ✕")//
      );
    } catch (Exception ex) {
      log.error("{}. Request sem metrica de 'track'", ex.getMessage());
    }
  }

  private String format(String message, Object... objs) {
    return ParameterizedMessage.format(message, objs);
  }

  private StringBuilder appendMessageLogCheck() {
    try {
      return requestContextHandler.getMetricData().getMessageBuild();
    } catch (ServletException ex) {
      return new StringBuilder();
    }
  }

  private String createLogMessage(String pattern) throws Exception {
    MetricData data = requestContextHandler.getMetricData();
    return format(pattern, //
        data.getXTraceId(),// x-trace-id
        data.getMethod(), data.getRequestURI(),// url
        data.getRequestSize(),// size request
        data.getMessageBuild().toString()// message
    );
  }

}