package br.com.pegasus.api.rest.commerce.infra.telemetry.logger;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.CompositeConverter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LevelColorConverter extends CompositeConverter<ILoggingEvent> {

  /* Cores ANSI */
  private static final String RESET = "\u001B[0m";
  private static final String CYAN = "\u001B[36m";
  private static final String YELLOW = "\u001B[33m";
  private static final String RED = "\u001B[31m";
  private static final String GREEN = "\u001B[32m";
  private static final String WHITE = "\u001B[37m";

  private static final Map<Integer, String> COLLOR_MAP = Map.of(//
      Level.INFO_INT, CYAN, //
      Level.WARN_INT, YELLOW, //
      Level.ERROR_INT, RED, //
      Level.DEBUG_INT, GREEN, //
      Level.TRACE_INT, WHITE //
  );

  @Override
  protected String transform(ILoggingEvent event, String in) {
    return COLLOR_MAP.get(event.getLevel().levelInt) + in + RESET;
  }

}

