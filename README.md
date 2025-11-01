# TAREFAS

- remover fild all (sem page) de todos os endpoints do contrato
- ajustar o sistema de Exceptions
    - simplificar as classes exceptions
    - ajustar retorno rest
- criar um sistema de anotação pra log em métodos ou na classe onde ao iniciar ou
  finalizar um método deve se criado um log
- Banco de dados
    - Revisão
    - criaçãode imagens e arquivo para draw.io
    - Fazer a criação do script de criação do banco de dados
    - fazer comandos basicos de consulta e alteração no banco de dados

# Telemetria

## Configuração

### Dependências

Telemetria: habilita endpoints /actuator para expor métricas, saúde e informações da aplicação, baseando-se no
Micrometer para coleta e exportação de dados para Prometheus, Grafana etc.
Geral: agrega e configura automaticamente as bibliotecas do Actuator, fornecendo endpoints administrativos e de
diagnóstico prontos para uso em produção.

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

Telemetria: fornece a API de observabilidade do Micrometer, permitindo medir, rastrear e correlacionar métricas, logs e
traces de operações dentro do código.
Geral: biblioteca independente de instrumentação, usada para criar observações e capturar contexto de execução e erros
de maneira estruturada.

```xml

<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-observation</artifactId>
</dependency>
```

Telemetria: realiza a configuração automática dos componentes do Actuator e do Micrometer, como o registro de métricas e
o mapeamento dos endpoints de monitoramento.
Geral: módulo de auto-configuração do Spring Boot que cria automaticamente beans e serviços conforme as dependências
detectadas no classpath.

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-actuator-autoconfigure</artifactId>
</dependency>
```

Telemetria: implementa os endpoints e mecanismos de coleta de métricas (como /actuator/metrics e /actuator/prometheus),
além de integrar indicadores de saúde e performance.
Geral: é o núcleo funcional do Actuator, responsável por fornecer as classes e endpoints reais de monitoramento e
administração da aplicação.

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-actuator</artifactId>
</dependency>
```

Telemetria: converte métricas do Micrometer para o formato Prometheus, habilitando o endpoint /actuator/prometheus para
coleta externa via scraping.
Geral: implementação do MeterRegistry para Prometheus, permitindo exportar métricas padronizadas compatíveis com
ferramentas de observação e visualização como Grafana.

```xml

<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

### Application.yaml
 Essa configuração garante que você consiga acessar todas as métricas via /actuator/metrics e /actuator/prometheus.

`management.endpoints.web.exposure.include: health, info, metrics, prometheus, httpexchanges`
- health → endpoint de saúde da aplicação.
- info → informações gerais do app.
- metrics → exposição de métricas.
- prometheus → endpoint compatível com Prometheus.
- httpexchanges → endpoint de tracing

`management.endpoint.health.show-details: always`
- health.show-details: always → sempre mostra detalhes da saúde.

`spring.servlet.multipart.max-file-size: 10MB` e `spring.servlet.multipart.max-request-size: 10MB`
- limitam o tamanho de upload de arquivos; útil para medir requestSize corretamente.

`management.endpoint.metrics.enabled: true`
- metrics.enabled: true → garante que métricas estão habilitadas.

`management.endpoint.httptrace.enabled: true`
- httptrace.enabled: true -> garante que trace visual apareça por completo

`logging.pattern.level: "%5p [traceId=%X{traceId:-}, spanId=%X{spanId:-}]"`
- Ativa logs com traceId/spanId

```yaml
management.endpoints.web.exposure.include: health, info, metrics, prometheus, httpexchanges
management.endpoint.health.show-details: always
spring.servlet.multipart.max-file-size: 10MB
spring.servlet.multipart.max-request-size: 10MB
management.endpoint.metrics.enabled: true
management.endpoint.httptrace.enabled: true
logging.pattern.level: "%5p [traceId=%X{traceId:-}, spanId=%X{spanId:-}]"
```

```java


```





URLs disponíveis para métricas

Com spring-boot-actuator + micrometer + prometheus:

Métrica URL Tagspossíveis
Counter http_requests_total /actuator/metrics/http_requests_total?tag=method:GET&tag=url:/produtos&tag=status:200
Timer http_request_duration_ms /actuator/metrics/http_request_duration_ms?tag=method:POST&tag=url:/orders&tag=status:201
Gauge http_active_requests /actuator/metrics/http_active_requests
DistributionSummary http_response_size_bytes /actuator/metrics/http_response_size_bytes?tag=method:GET&tag=url:
/produtos&tag=status:200
| Objetivo | Ferramenta | Endpoint/Local |
| ----------------------- | ------------------------- | ---------------- |
| Ver últimas requisições | `/actuator/httpexchanges` | Dados JSON |
| Ver métricas gerais | `/actuator/metrics`       | Dados Micrometer |
| Rastrear via logs | `traceId` e `spanId`      | Console/log file |

All metrics:    /actuator/metrics
Prometheus:     /actuator/prometheus
Health:         /actuator/health
Info:           /actuator/info
httptrace:      /actuator/httptrace (por padrão, mostra as 100 últimas requisições)

GET /actuator/metrics/http_requests_total
GET /actuator/metrics/http_request_duration_ms
GET /actuator/metrics/http_active_requests
GET /actuator/metrics/http_response_size_bytes
GET /actuator/metrics/http_request_size_bytes
GET /actuator/prometheus
GET /actuator/httptrace

Obs.: No Prometheus, você verá todas essas métricas agregadas e exportadas com labels (tags) correspondentes.

# pom.xml

**ACTUATOR**
fornece os recursos internos de monitoramento e métricas do Spring Boot. Sem ele, você não terá endpoints
como /actuator/metrics ou /actuator/health, nem o suporte nativo para integração com Micrometer.

## Funcionalidades que ele entrega:

- Exposição de métricas e saúde da aplicação:
    - /actuator/health → status geral da aplicação.
    - /actuator/metrics → todas as métricas registradas (CPU, memória, HTTP, etc).
- Integração com Micrometer:
    - Permite registrar métricas customizadas (Counter, Timer, etc).
    - Possibilita consultar métricas via HTTP ou exportar para sistemas externos (Prometheus, por exemplo).
- Endpoints configuráveis:
    - Você pode escolher quais endpoints expor via application.yaml sem precisar criar controllers.
- Coleta de métricas HTTP automaticamente:
    - Contagem de requisições, tempos de resposta, status code, entre outros.

Resumo: Com o Actuator, o projeto passa a possuir, entre outros recursos, registro automático de métricas, além de
consulta e monitoramento padronizados.

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

Essa dependência adiciona suporte à exposição das métricas no formato Prometheus,
permitindo que ferramentas
compatíveis consumam os dados de telemetria do seu aplicativo.
Em resumo: sem ela, você ainda teria métricas via Actuator, mas não no formato nativo do
Prometheus.

- Permite interceptar todas as requisições antes e depois de serem processadas pelos controllers.
- filter: Permite interceptar todas as requisições antes e depois de serem processadas pelos controllers.

```
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

# Classes

```
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class RequestMetricsFilter implements Filter {

    private final MeterRegistry meterRegistry;

    public RequestMetricsFilter(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        long start = System.currentTimeMillis();
        try {
            chain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - start;

            // contador de requisições
            Counter.builder("http_requests_total")
                    .tag("method", req.getMethod())
                    .tag("uri", req.getRequestURI())
                    .tag("status", String.valueOf(res.getStatus()))
                    .register(meterRegistry)
                    .increment();

            // timer de duração
            Timer.builder("http_request_duration_ms")
                    .tag("method", req.getMethod())
                    .tag("uri", req.getRequestURI())
                    .tag("status", String.valueOf(res.getStatus()))
                    .register(meterRegistry)
                    .record(duration, TimeUnit.MILLISECONDS);
        }
    }
}
```

# Application.yaml

- exposure.include → define quais endpoints do Actuator ficam acessíveis via HTTP.
- health → /actuator/health
- metrics → /actuator/metrics
- prometheus → /actuator/prometheus
- show-details: always → mostra detalhes completos no /actuator/health (senão pode aparecer só "status":"UP").

```
management:
  endpoints:
    web:
      exposure:
        include: health, metrics, prometheus
  endpoint:
    health:
      show-details: always
```

# URLs de verificação (GET)

- Todas as métricas disponíveis:
    - http://localhost:8080/actuator/metrics
- Métrica customizada de contagem de requests:
    - http://localhost:8080/actuator/metrics/http_requests_total
- Métrica customizada de duração de requests:
    - http://localhost:8080/actuator/metrics/http_request_duration_ms
- Formato Prometheus (todas as métricas):
    - http://localhost:8080/actuator/prometheus

###### SOBRE O PROJETO

`texto`

**

## Propósito

`Entrada rest via contrato openapi`

## COMO RODAR?

## Informações técnicas

### Regras de Negócios

### Banco de dados

Esta aplicação usa o banco de dados H2, pois ele oferece uma flexibilidade e pratico para o desenvolvimento de uma nova
aplicação

H2 é um banco leve, rápido, em memória ou arquivo, e é muito usado para testes e desenvolvimento por ser simples e
flexível. Ele fornece apenas o necessário para persistência temporária ou local, não sendo recomendado para produção de
grande escala.

#### applycation.yaml

A propriedade `spring.jpa.hibernate.ddl-auto` (ou `hibernate.hbm2ddl.auto`) controla como o Hibernate gerencia o esquema
do
banco de dados.

- **none**
    - descrição: não faz nada
    - uso: quando o esquema já existe e você quer total controle manual (ideal em produção).
- **validate**
    - descrição
        - valida se as entidades Java estão em conformidade com o banco
        - não cria nem altera nada
        - lança erro se algo estiver diferente.
    - uso: ambiente de produção quando o schema é criado por scripts externos
    - resumo:
- **update**
    - descrição
        - Cria ou altera tabelas conforme as entidades
        - Nunca apaga dados.
    - uso: ambiente de desenvolvimento (mantém dados entre execuções). Pode gerar inconsistências se alterar tipos de
      colunas existentes.
- **create**
    - descrição: dropa e recria o banco a cada inicialização, perdendo todos os dados anteriores
    - Uso: ambiente de teste rápido.
- **create-drop**
    - descrição: Igual ao create, mas também apaga o schema ao encerrar a aplicação.
    - Uso: testes temporários ou integrações automatizadas (JUnit, CI).
- **drop**
    - descrição:
        - pouco usado
        - Remove o schema do banco no início (sem recriar)

### Contrato OpenAPI

- dependencias
- Plugin
- arquivo openapi.yaml

### LOG

### telemetria

# Tutorial - Spring boot - Contrato OpenAPI - Copy Template

copia um template para o target e geristrar como pacote

## Base

* Crie o diretorio `src\main\template`
* crie uma estrutura assim `<nome-projeto>\br\com\<algum-nome>\gen\nome-projeto`
* crie a estrutura de sua necessidade

## Pom.xml

Adicione a propriedades abaixo

```xml

<properties>
    <gen.from>src/main/template/restful</gen.from>
    <gen.import.restful>br/com/pegasus/gen/restful/**/*.java</gen.import.restful>
    <gen.to>${project.build.directory}/generated-sources/local-project/src/main/java</gen.to>
</properties>
```

Adicione o plugin abaixo

```xml

<plugins>
    <plugin>
        <!-- Importe de pacotes do diretório template [1/2] -->
        <!-- transporta os pacotes desejados para a pasta target -->
        <artifactId>maven-resources-plugin</artifactId>
        <version>3.3.1</version>
        <executions>
            <execution>
                <phase>generate-sources</phase>
                <goals>
                    <goal>copy-resources</goal>
                </goals>
                <id>generate-id</id>
                <configuration>
                    <resources>
                        <resource>
                            <directory>${gen.from}</directory>
                            <includes>
                                <include>${gen.import.restful}</include>
                            </includes>
                        </resource>
                    </resources>
                    <outputDirectory>${gen.to}</outputDirectory>
                </configuration>
            </execution>
        </executions>
    </plugin>

    <plugin>
        <!-- Importe de pacotes do diretório template [2/2] -->
        <!-- registra os pacotes importados -->
        <groupId>org.codehaus.mojo</groupId>
        <artifactId>build-helper-maven-plugin</artifactId>
        <version>3.4.0</version>
        <executions>
            <execution>
                <phase>generate-sources</phase>
                <goals>
                    <goal>add-source</goal>
                </goals>
                <id>generate-id</id>
                <configuration>
                    <sources>
                        <source>${gen.to}</source>
                    </sources>
                </configuration>
            </execution>
        </executions>
    </plugin>
</plugins>
```

## Explicação

- `gen.from:` Indica ao plugin, onde esta os templates
- `gen.import.restful:` Define qual cadeia de codigo deseja adicionar (pode ser adicionado multiplos `gen.import`.
  Lembrando q `restful` é o nome do pacote, então ele não pode se repetir)
- `gen.to:` indica ao plugin para onde vai o template e que tudo dentro dele deve ser registrado como pacotes ativos

Obs.: Lembre que se algum pacote possuir algum component do spring, use:

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
    "<pacote-principa-projeto>", // pacote principal do projeto
    "<pacote-template>" // pacote com os subpacotes do template
})
public class StartApplication {

  public static void main(String[] args) {
    SpringApplication.run(StartApplication.class, args);
  }

}
```

## Fonte

- https://chatgpt.com/ (para refinamento)