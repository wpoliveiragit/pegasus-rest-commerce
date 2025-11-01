package br.com.pegasus.api.rest.commerce.infra.handler.processor;

import br.com.pegasus.api.rest.commerce.infra.handler.annot.LogAnnot;
import br.com.pegasus.api.rest.commerce.infra.handler.log.ClassLog;
import br.com.pegasus.api.rest.commerce.infra.handler.log.CorelLog;
import br.com.pegasus.api.rest.commerce.infra.handler.log.HandlerLog;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Component
public class HandlerProcessor implements BeanPostProcessor {

  @Override
  public Object postProcessAfterInitialization(@NotNull Object bean, @NotNull String beanName) throws BeansException {
    HandlerLog handlerLog = log(bean);

    if(bean instanceof ClassLog){
      //encontra todos os methodos publicos da classe
      System.out.println("Classe anotada com @ClassLogAnnot");
      Method[] methods = bean.getClass().getDeclaredMethods();

      for (Method method : methods) {
        if (Modifier.isPublic(method.getModifiers())) {
          System.out.println(method.getName());
        }
      }
    }


    boolean doNotCreateProxy = true;
    ToolProcessor toolProcessor = new ToolProcessor();

    if (handlerLog != null) {
      doNotCreateProxy = false;
      toolProcessor.setLog(handlerLog);
    }
    return doNotCreateProxy ?  bean : createProxy(bean, toolProcessor);
  }

  private static Object createProxy(Object bean, ToolProcessor tool) {
    return Proxy.newProxyInstance(//
        bean.getClass().getClassLoader(), //
        bean.getClass().getInterfaces(), //
        new InvocationHandlerProcessor(bean, tool));
  }

  private static HandlerLog log(Object bean) {
    Map<Method, CorelLog> logMap = new HashMap<>(16);
    Class<?> clazz = bean.getClass();
    //TODO: melhorar usando stream
    for (Method method : clazz.getMethods()) {
      if (method.isAnnotationPresent(LogAnnot.class)) {
        logMap.put(method, new CorelLog(clazz, method));
      }
    }
    return logMap.isEmpty() ? null : new HandlerLog(clazz, logMap);
  }

}
