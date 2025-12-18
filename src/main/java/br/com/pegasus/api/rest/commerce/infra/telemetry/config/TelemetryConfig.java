package br.com.pegasus.api.rest.commerce.infra.telemetry.config;

import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelemetryConfig {


  /**
   * Define um {@link HttpExchangeRepository} baseado em memória.
   *
   * <p>
   * Esse repositório é utilizado pelo Spring Boot Actuator para armazenar
   * temporariamente o histórico de requisições e respostas HTTP processadas
   * pela aplicação.
   * </p>
   *
   * <p>
   * Os dados registrados incluem método HTTP, URI, status da resposta,
   * tempo de execução e headers, conforme configuração do Actuator.
   * </p>
   *
   * <p>
   * A implementação {@link InMemoryHttpExchangeRepository} mantém os registros
   * apenas em memória, sendo indicada para ambientes de desenvolvimento
   * e diagnóstico.
   * </p>
   *
   * <p>
   * As informações armazenadas são perdidas quando a aplicação é reiniciada
   * e não devem ser utilizadas como mecanismo de auditoria em produção.
   * </p>
   *
   * @return repositório em memória para histórico de exchanges HTTP
   */
  @Bean
  public HttpExchangeRepository createHttpExchangeRepository() {
    return new InMemoryHttpExchangeRepository();
  }

}