# TUTORIAL DE ESTUDO — KAFKA APLICADO AO CRUD DE PRODUTOS

## INTRODUÇÃO

Este documento organiza um estudo prático e objetivo sobre Kafka aplicado a um CRUD REST usando **Spring Boot**, **JDK
17**, **Maven**, **Caffeine** e **arquitetura hexagonal**, focando no uso do Kafka como ferramenta de eventos,
auditoria, métricas e integração.

---

# LISTA DE ESTUDO DO KAFKA

## **Produzir eventos JSON**

- Enviar mensagens JSON simples após operações importantes (create/update/delete).
- Cada evento representa um **fato**, não um estado.
- Usar eventos para auditoria, replicação ou observabilidade.

## **Criar tópicos com diferentes partições**

- Criar tópicos com **1, 3 e 5 partições** para testar paralelismo.
- Ver como Kafka distribui mensagens entre partições.
- Observar impacto da chave na escolha da partição.

## **Garantia de entrega: acks, retries, linger, idempotência**

- **acks**
    - `acks=1`: rápido, pode perder mensagens.
    - `acks=all`: mais seguro, espera réplicas.
- **retries**
    - Reenvio automático quando há falha.
- **linger.ms**
    - Atraso proposital para batching.
- **idempotência (`enable.idempotence=true`)**
    - Evita duplicação em caso de retries.

## **Enviar mensagens com chave e sem chave**

- **Sem chave**
    - Distribuição round-robin.
- **Com chave**
    - Kafka aplica hash → define partição.
    - Garante ordem **apenas naquela partição**.
- Testar chaves diferentes para observar distribuição.

## **Particionamento, ordenação e rebalanceamento**

- A chave determina a partição → garante ordem local.
- Não existe **ordem global**, apenas por partição.
- Entender **rebalanceamento**:
    - Ocorre quando um consumer entra ou sai do grupo.
    - Kafka redistribui partições automaticamente.
    - Consumer pode pausar durante rebalanceamento.

## **Eventos como streams de fatos**

- Registrar apenas acontecimentos: criado, atualizado, excluído.
- Facilita reconstrução histórica.
- Evita armazenar estados conflitantes.

## **Evolução de schema dos eventos**

- Adicionar campos sem quebrar consumidores.
- Testar compatibilidade evolutiva simples (adicionar campo opcional).
- Consumidores devem tolerar campos desconhecidos.

## **Visualizar mensagens no Kafdrop/AKHQ**

- Ver partição, offset, timestamp, headers e payload.
- Acompanhar fluxo em tempo real.
- Verificar erros e mensagens com falha.

## **Ajustar retenção do tópico**

- Configurar retenção por tempo: `retention.ms`.
- Configurar retenção por tamanho: `retention.bytes`.
- Estudar retenção curta vs longa.

## **Compactação por chave (Log Compaction)**

- Tópico mantém apenas a última mensagem por chave.
- Útil para manter um estado materializado.

## **Offsets (auto commit vs manual)**

- **Auto commit**
    - Fácil, mas pode confirmar mensagens não processadas.
- **Commit manual**
    - Mais seguro, confirma após processar.
- Permite reprocessar mensagens se necessário.

## **Dead Letter Queue (DLQ)**

- Destino para mensagens que falham repetidamente.
- Evita travar o consumo.
- Permite investigar mensagens “venenosas”.
- Importante ao explorar retries + commit manual.

## **Headers no Kafka**

- Permitem adicionar: traceId, correlationId, versão, origem.
- Melhora troubleshooting e rastreamento distribuído.

## **Limites de tamanho de mensagens**

- Kafka não suporta mensagens muito grandes.
- Estudar: `max.message.bytes`, `fetch.message.max.bytes`.
- Incentiva manter eventos pequenos.

## **Segurança (básico)**

- Conceitos essenciais:
    - SSL/TLS
    - SASL/PLAIN
    - SASL/SCRAM
- Apenas no nível teórico (não requer implementação).

---

## GRUPOS DE ESTUDO — EVENTOS PRODUZIDOS PELO CRUD

### EVENTOS DO CICLO DE VIDA DO PRODUTO

produção de eventos e JSON

**Produto Created**
: Motivo: registrar criação do produto.
: Registrar: id, nome, preço inicial, timestamp, traceId, usuário.

**Produto Updated**
: Motivo: manter histórico de alterações.
: Registrar: id, campos alterados, timestamp, traceId.

**Produto Deleted**
: Motivo: auditoria de remoção.
: Registrar: id, timestamp, traceId, motivo.

---

### **AUDITORIA E RASTREAMENTO**

acessos e trilha de leitura

**Registro de Acessos**
: Motivo: mapear consultas.
: Registrar: id consultado, timestamp, traceId.

**Trilha de Auditoria**
: Motivo: consolidar todos os eventos relevantes.
: Registrar: eventos do Grupo 1 + acessos.

---

### **MÉTRICAS E ANÁLISE**

**Estudo:** métricas assíncronas

- **Métricas Operacionais**
    - Motivo: medir ações do CRUD.
    - Registrar: tipo de evento, timestamp, id.
- **Eventos Analíticos**
    - Motivo: geração de dashboards.
    - Registrar: payload completo para BI.

---

### **INTEGRAÇÃO E REPLICAÇÃO**

**Estudo:** comunicação entre serviços

- **Propagação de Mudanças**
    - Motivo: notificar outros sistemas.
    - Registrar: id, ação, timestamp.
- **Replicação**
    - Motivo: sincronizar bases externas.
    - Registrar: evento completo.

---

# PRODUCER / CONSUMER E REGRA DE NEGÓCIO

| Ação                              | É Regra de Negócio? | Quando Sim                                                        | Quando Não                                           |
|-----------------------------------|---------------------|-------------------------------------------------------------------|------------------------------------------------------|
| Producer publica evento           | Depende             | Quando publicar faz parte do processo (ex.: criação gera evento). | Quando só envia auditoria, logs ou métricas.         |
| Consumer processa evento          | Depende             | Quando altera estado ou inicia um fluxo do domínio.               | Quando apenas indexa, salva ou registra informações. |
| Validação antes de publicar       | Sim                 | Quando o domínio exige regra para permitir o evento.              | —                                                    |
| Tratamento de falhas (retry, DLQ) | Não                 | —                                                                 | Retries, DLQ e config → infraestrutura.              |
| Formatação da mensagem            | Não                 | —                                                                 | Serialização, headers e schema → infraestrutura.     |
| Evento dispara fluxo              | Sim                 | Quando um evento inicia um processo real do negócio.              | —                                                    |

---

## DEAD LETTER QUEUE (DLQ)

- Destino para mensagens que **falharam várias vezes**.
- Evita travar o consumidor.
- Permite inspeção manual.
- Tipicamente usado com retries + commit manual.
- Importante em pipelines críticos.

---

# SEPARAÇÃO DE RESPONSABILIDADES

## **Infraestrutura**

- Kafka Producer
- Kafka Consumer
- Configuração Kafka
- Serialização JSON
- Retentativas e DLQ
- Commit manual/automático
- Particionamento

## **Domínio**

- Regras ao criar/alterar produto
- Decisão se evento será enviado
- Garantia de coerência
- Processamento lógico dentro do Consumer Handler

---

# EXEMPLOS (CONCISOS) — SPRING BOOT + KAFKA — JDK 17

### Producer (Infraestrutura)

```java

@Component
public class ProdutoEventProducer {
  private final KafkaTemplate<String, ProdutoEvent> template;

  public void enviar(ProdutoEvent event) {
    template.send("produto.event", event.id(), event);
  }
}
```


----
Aqui vão ideias práticas e diretas do que você pode fazer com o Kafka Consumer no seu projeto de estudo (CRUD simples de produtos).
Tudo pensado para um ambiente de aprendizado, mas com “cara” de arquitetura real.

✅ 1. Registrar histórico de eventos (event log)

Cada ação no produto gera um evento:

create

update

delete

price-change

stock-change

Consumer: grava esses eventos em uma tabela de histórico.

📌 Uso real: auditoria, rastreabilidade.

✅ 2. Manter um cache atualizado

Você pode criar um cache Redis que guarda os produtos mais acessados.

Consumer: quando receber evento de update ou delete, ele limpa/atualiza o cache.

📌 Uso real: performance.

✅ 3. Criar índices de busca (simulação de Elasticsearch)

Para estudo, você pode criar uma tabela “search_index”.

Consumer: sempre que um produto for criado/atualizado, ele escreve a versão indexada lá.

📌 Uso real: motores de busca.

✅ 4. Gatilho para enviar e-mails/notificações

Simular envio de notificações:

produto criado → manda email

preço atualizado → manda email

produto removido → manda email

📌 Uso real: e-commerce, ERP.

✅ 5. Enviar dados para outro microserviço

Simular uma arquitetura com 2 serviços:

API de Produtos (seu CRUD)

Serviço de Relatórios (consumer)

O consumer recebe o evento e grava em outro banco.

📌 Uso real: desacoplamento.

✅ 6. Criar métricas

Consumer pode alimentar métricas:

total de produtos criados no dia

total de updates

média de alterações por produto

📌 Uso real: dashboards.

✅ 7. Detectar anomalias (ex.: preço muito baixo)

Consumer verifica o evento:

Se o preço recebido for menor que X → loga alerta.

📌 Uso real: prevenção de erros.

✅ 8. Simular workflow

Exemplo:

Produto criado → consumer coloca status “aguardando validação”

Outro consumer valida → coloca “validado”

📌 Uso real: pipelines internos. 