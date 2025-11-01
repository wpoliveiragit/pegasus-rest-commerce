//package br.com.pegasus.api.rest.commerce.infra.config.handler;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.BeanPostProcessor;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ProxyManualBeanPostProcessor implements BeanPostProcessor {
//
//  @Override
//  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
////    if (bean instanceof TestService) {
////      return Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces(), // precisa implementar interface!
////          (proxy, method, args) -> {
////            try {
////              return method.invoke(bean, args);
////            } catch (Exception e) {
////              throw e;
////            }
////          });
////    }
//    return bean;
//  }
//}
