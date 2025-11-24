package br.com.pegasus.api.rest.commerce.infra.logback;

import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.CompositeConverter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LevelColorConverter extends CompositeConverter<ILoggingEvent> {

  private static final Map<Integer, String> COLLOR_MAP = Map.of(//
      Level.INFO_INT, ConstUtil.COLOR_CYAN, //
      Level.WARN_INT, ConstUtil.COLOR_YELLOW, //
      Level.ERROR_INT, ConstUtil.COLOR_RED, //
      Level.DEBUG_INT, ConstUtil.COLOR_GREEN, //
      Level.TRACE_INT, ConstUtil.COLOR_WHITE //
  );

  @Override
  protected String transform(ILoggingEvent event, String in) {
    return COLLOR_MAP.get(event.getLevel().levelInt) + in + ConstUtil.COLOR_RESET;
  }

}

