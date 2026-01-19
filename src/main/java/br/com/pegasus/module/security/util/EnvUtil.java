package br.com.pegasus.module.security.util;

import lombok.RequiredArgsConstructor;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;

import java.util.List;

@RequiredArgsConstructor
public final class EnvUtil {

  private final Environment env;

  public String getRequiredProp(String key) {
    String prop = env.getProperty(key);
    if (prop == null) {
      throw new IllegalStateException(MessageFormatter.format(ConstSecUtil.MSG_EXCEPTION_PROPERTY, key).getMessage());
    }
    return prop;
  }

  public int getRequiredIntProp(String key) {
    Integer prop = env.getProperty(key, Integer.class);
    if (prop == null) {
      throw new IllegalStateException(MessageFormatter.format(ConstSecUtil.MSG_EXCEPTION_PROPERTY, key).getMessage());
    }
    return prop;
  }

  public List<String> getStringListProp(String key) {
    return Binder.get(env).bind(key, Bindable.listOf(String.class)).orElse(List.of());
  }

  public boolean getBooleanPropOrFalse(String key) {
    return env.getProperty(key, Boolean.class, Boolean.FALSE);
  }

}
