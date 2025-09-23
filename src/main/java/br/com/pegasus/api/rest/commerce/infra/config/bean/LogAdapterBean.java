package br.com.pegasus.api.rest.commerce.infra.config.bean;

import br.com.pegasus.api.rest.commerce.domain.adapter.LogAdapter;
import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class LogAdapterBean implements LogAdapter {

  private final Logger log;

  @Override
  public void info(String msg) {
    log.info(msg);
  }

  @Override
  public void warn(String msg) {
    log.warning(msg);
  }

  @Override
  public void erro(String msg) {
    log.severe(msg);
  }

  @Override
  public void info(String msg, Object[] values) {
    log.info(replaceBracesValues(msg, values));
  }

  @Override
  public void warn(String msg, Object[] values) {
    log.warning(replaceBracesValues(msg, values));
  }

  @Override
  public void erro(String msg, Object[] values) {
    log.severe(replaceBracesValues(msg, values));
  }

  private static String replaceBracesValues(final String msg, Object... values) {
    return (values == null) ? msg : Arrays.stream(values).reduce(msg, (newMsg, obj) -> {
      if (obj == null) {
        return newMsg.replaceFirst(ConstUtil.REGEX_BRACES, ConstUtil.T_NULL);
      }
      return newMsg.replaceFirst(ConstUtil.REGEX_BRACES, obj.toString());
    }, (s1, s2) -> s1);
  }

}