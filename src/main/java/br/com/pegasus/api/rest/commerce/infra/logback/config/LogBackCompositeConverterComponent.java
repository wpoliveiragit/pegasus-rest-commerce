package br.com.pegasus.api.rest.commerce.infra.logback.config;

import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.CompositeConverter;
import org.springframework.stereotype.Component;

@Component
public class LogBackCompositeConverterComponent extends CompositeConverter<ILoggingEvent>{

  @Override
  protected String transform(ILoggingEvent event, String in) {
    return ConstUtil.COLLOR_MAP.get(event.getLevel().levelInt) + in + ConstUtil.COLOR_RESET;
  }

}