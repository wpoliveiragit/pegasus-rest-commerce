package br.com.pegasus.api.rest.commerce.infra.config;

import br.com.pegasus.api.rest.commerce.StartApplication;
import br.com.pegasus.api.rest.commerce.app.controller.OpenApiController;
import br.com.pegasus.api.rest.commerce.app.controller.WebSiteController;
import br.com.pegasus.api.rest.commerce.app.delegate.ProductDelegate;
import br.com.pegasus.api.rest.commerce.app.handler.HttpMethodHandler;
import br.com.pegasus.api.rest.commerce.app.handler.RestControllerAdviceHandler;
import br.com.pegasus.api.rest.commerce.domain.adapter.ExceptionMethodAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.ToolAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.ProductAdaterJPA;
import br.com.pegasus.api.rest.commerce.domain.core.ProductCore;
import br.com.pegasus.api.rest.commerce.domain.port.ProductPort;
import br.com.pegasus.api.rest.commerce.infra.config.domain.adapter.ExceptionMethodConfigAdapter;
import br.com.pegasus.api.rest.commerce.infra.config.domain.adapter.ToolConfigAdapter;
import br.com.pegasus.api.rest.commerce.infra.config.domain.adapter.jpa.ProductAdaterConfigJPA;
import br.com.pegasus.api.rest.commerce.infra.config.domain.port.BeansPort;
import br.com.pegasus.api.rest.commerce.infra.data.DependencePomData;
import br.com.pegasus.api.rest.commerce.infra.handler.RequestContextHandler;
import br.com.pegasus.api.rest.commerce.infra.mapper.PageableMapper;
import br.com.pegasus.api.rest.commerce.infra.mapper.ProductMapper;
import br.com.pegasus.api.rest.commerce.infra.repository.ProductRepository;
import br.com.pegasus.api.rest.commerce.infra.scheduler.StartScheduler;
import br.com.pegasus.api.rest.commerce.infra.telemetry.MetricsTelemetry;
import br.com.pegasus.api.rest.commerce.infra.telemetry.aspect.TelemetryAspect;
import br.com.pegasus.api.rest.commerce.infra.telemetry.logger.LevelColorConverter;
import br.com.pegasus.api.rest.commerce.infra.telemetry.logger.TrackLogger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
public class StartupInfoConfig {

  private final RequestMappingHandlerMapping handlerMapping;
  private final ApplicationContext ctx;

  public StartupInfoConfig(@Qualifier("requestMappingHandlerMapping") RequestMappingHandlerMapping handlerMapping, ApplicationContext ctx) {
    this.handlerMapping = handlerMapping;
    this.ctx = ctx;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void init() throws Exception {
    printPom().forEach(System.out::println);
    printEndpoints().forEach(System.out::println);
    checkBeans().forEach(System.out::println);

    double seconds = (System.currentTimeMillis() - StartApplication.START_TIME) / 1000.0;
    System.out.printf("Started %s in %.3f seconds (process running for %.3f)%n", //
        StartApplication.class.getSimpleName(), seconds, seconds);
  }

  public List<String> printPom() throws Exception {
    NodeList docDeps = DocumentBuilderFactory.newInstance().newDocumentBuilder()//
        .parse(new File("pom.xml")).getElementsByTagName("dependency");

    return Stream.concat(Stream.of("◎ DEPENDÊNCIAS DO PROJETO"), IntStream.range(0, docDeps.getLength())//
        .mapToObj(i -> createDependencyLine(new DependencePomData((Element) docDeps.item(i))))//
    ).toList();
  }

  public List<String> printEndpoints() {
    return Stream.concat(Stream.of("◎ ENDPOINTS DISPONÍVEIS"), handlerMapping.getHandlerMethods().keySet()//
        .stream().map(key -> "\t- " + key.toString().substring(1, key.toString().length() - 1).trim())//
    ).toList();
  }

  public List<String> checkBeans() {
    List<Class<?>> beans = List.of(//
        //app
        OpenApiController.class, WebSiteController.class, //app.controller
        ProductDelegate.class,//app.delegate
        HttpMethodHandler.class, RestControllerAdviceHandler.class,//app.handler
        //domain
        ProductAdaterJPA.class,//domain.adapter.jpa
        ExceptionMethodAdapter.class, ToolAdapter.class, //domain.adapter
        ProductCore.class,//domain.core
        ProductPort.class, //domain.port
        //infra
        ProductAdaterConfigJPA.class, // infra.config.domain.adapter.jpa
        ExceptionMethodConfigAdapter.class, ToolConfigAdapter.class,// infra.config.domain.adapter
        BeansPort.class, // infra.config.domain.port
        RequestContextHandler.class,//infra.hadler
        PageableMapper.class, ProductMapper.class, //infra.mapper
        ProductRepository.class, //infra.repository
        StartScheduler.class,//infra.scheduler
        TelemetryAspect.class,//infra.telemetry.aspect
        LevelColorConverter.class, TrackLogger.class,//infra.telemetry.logger
        MetricsTelemetry.class);//infra.telemetry

    return Stream.concat(Stream.of("◎ CHECK BEANS"),//
        beans.stream().map(bean -> checkBean(bean) + bean.getSimpleName())//
    ).toList();
  }

  private String checkBean(Class<?> beanClass) {
    return (ctx.getBeanNamesForType(beanClass).length > 0) ? "\t[X] " : "\t[ ] ";
  }

  private String createDependencyLine(DependencePomData dep) {
    return "\t- " + dep.getArtifactId() + ", " + dep.getGroupId() + ", " + "[" + dep.getVersion() + "]";
  }

}
