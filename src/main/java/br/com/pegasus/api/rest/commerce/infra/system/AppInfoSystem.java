//package br.com.pegasus.api.rest.commerce.infra.system;
//
//import br.com.pegasus.api.rest.commerce.StartApplication;
//import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
//import br.com.pegasus.api.rest.commerce.infra.util.TextFormatUtil;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.beans.factory.config.BeanPostProcessor;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public final class AppInfoSystem implements BeanPostProcessor {
//
//  private String rootPackage;
//  private List<Class<?>> beans;
//  private ApplicationContext ctx;
//
//  public AppInfoSystem(ApplicationContext ctx) {
//    this.ctx = ctx;
//    this.rootPackage = StartApplication.class.getPackageName();
//    this.beans = new ArrayList<>();
//  }
//
//  @Override
//  public Object postProcessAfterInitialization(@NotNull Object bean, @NotNull String beanName) {
//    if (bean.getClass().getPackageName().contains(rootPackage)) {
//      beans.add(bean.getClass());
//    }
//    return bean;
//  }
//
//  @EventListener(ApplicationReadyEvent.class)
//  public void init() throws Exception {
//    RequestMappingHandlerMapping handlerMapping = ctx.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
//
//    PomContextSystem.printPom();
//    EndPoinSystem.printEndpoints(handlerMapping);
//    BeanSystem.checkBeans(beans);
//    printStartApplication();
//    flush();
//  }
//
//  private void printStartApplication() {
//
//    //CALCULO DO TEMPO DE INICIALIZAÇÃO DO SISTEMA
//    double seconds = (System.currentTimeMillis() - StartApplication.START_TIME) / ConstUtil.DOUBLE_1000;
//
//    // Cria o texto de inicialização do sistema
//    String text = TextFormatUtil.addColorGreen("Started") //
//        .concat(TextFormatUtil.addColorBlue(" %s"))//
//        .concat(TextFormatUtil.addColorGreen(" in"))//
//        .concat(TextFormatUtil.addColorBlue(" %.3f"))//
//        .concat(TextFormatUtil.addColorGreen(" seconds (process running for"))//
//        .concat(TextFormatUtil.addColorBlue(" %.3f"))//
//        .concat(TextFormatUtil.addColorGreen(")")).concat("%n");
//
//    // IMPRIME A MENSAGEM
//    System.out.printf(text, StartApplication.class.getSimpleName(), seconds, seconds);
//  }
//
//  private void flush() {// libera a referencia para o garbage collector
//    rootPackage = null;
//    beans = null;
//    ctx = null;
//  }
//
//}