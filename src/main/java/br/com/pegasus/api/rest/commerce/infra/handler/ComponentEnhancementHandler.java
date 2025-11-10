package br.com.pegasus.api.rest.commerce.infra.handler;

import br.com.pegasus.api.rest.commerce.infra.handler.annotation.LogProxyAnnotation;
import br.com.pegasus.api.rest.commerce.infra.handler.log.TrackLogHandler;
import br.com.pegasus.api.rest.commerce.infra.handler.proxy.BeanProxy;
import br.com.pegasus.api.rest.commerce.infra.handler.proxy.LogProxy;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;

@Component
@RequiredArgsConstructor
public class ComponentEnhancementHandler implements BeanPostProcessor {

  private final TrackLogHandler trackLogHandler;

  @Override
  public Object postProcessAfterInitialization(@NotNull Object bean, @NotNull String beanName) throws BeansException {
    LogProxy logProxy = checkLog(bean);

    if (logProxy == null) {
      return bean;
    }

    Class<?> clazz = bean.getClass();
    return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), new BeanProxy(bean, logProxy));
  }

  private LogProxy checkLog(Object bean) {
    LogProxyAnnotation annot = bean.getClass().getAnnotation(LogProxyAnnotation.class);
    if (annot == null) {
      return null;
    }
    return new LogProxy(bean, trackLogHandler);
  }

}
