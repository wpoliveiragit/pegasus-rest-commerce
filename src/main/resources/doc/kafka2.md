# Docker + Kafka — Guia Definitivo (Organizado e Corrigido)

## Requisitos e Instalação

### 1 Verificar virtualização

Ative na BIOS:

- Intel: VT-x
- AMD: AMD-V / SVM Mode

### 2 Instalar WSL2

```bash
wsl --install
```

### 3 Instalar Docker Desktop

Download: https://www.docker.com/products/docker-desktop/

Durante a instalação marque `Use WSL2 instead of Hyper-V` `Enable integration with WSL2`

### 4 Ativar recursos adicionais

Abra PowerShell (Admin) e execute cada comando abaixo e reinicie o PC

```bash
dism.exe /online /enable-feature /featurename:Microsoft-Windows-Subsystem-Linux /all /norestart
dism.exe /online /enable-feature /featurename:VirtualMachinePlatform /all /norestart
```

### 5 Configurar WSL2 no Docker

```bash
wsl --set-default-version 2
net stop com.docker.service
net start com.docker.service
```

## Configuração do Kafka (Docker)

Crie o arquivo `docker-compose.yml`

```yaml
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:7.4.1
    container_name: zookeeper
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000
    ports:
      - "2181:2181"
    volumes:
      - zk-data:/var/lib/zookeeper/data

  kafka:
    image: confluentinc/cp-kafka:7.4.1
    container_name: kafka
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
    environment:
      KAFKA_CFG_delete_topic_enable: "true"
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_LISTENERS: PLAINTEXT://0.0.0.0:9092
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
    volumes:
      - kafka-data:/var/lib/kafka/data

volumes:
  zk-data:
  kafka-data:
```

## Subir / Parar o Servidor

Subir

```bash
docker compose up -d
```

Parar e apagar volumes

```bash
docker compose down -v
```

Ver containers

```bash
docker ps
```

## Comandos Kafka — Organizados

### 1 Criar tópico

```bash
docker exec -it kafka bash -c "kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic topic-test"
```

### 2 Listar tópicos

```bash
docker exec -it kafka bash -c "kafka-topics --bootstrap-server localhost:9092 --list"
```

### 3 Deletar tópico

```bash
docker exec -it kafka bash -c "kafka-topics --bootstrap-server localhost:9092 --delete --topic topic-test"
```

## Producer e Consumer

### 1 Producer

```bash
docker exec -it kafka bash -c "kafka-console-producer --bootstrap-server localhost:9092 --topic topic-test"
```

**Exemplo:**

```json
{
  "nome": "thon",
  "idade": 33
}
```

### 2 Consumer — início do tópico

```bash
docker exec -it kafka bash -c "kafka-console-consumer --bootstrap-server localhost:9092 --topic topic-test --from-beginning"
```

### 3 Consumer com grupo

```bash
docker exec -it kafka bash -c "kafka-console-consumer --bootstrap-server localhost:9092 --topic topic-test --group meu-grupo"
```

## Grupos de consumidores

### Criar tópico e grupo "Grupo-A" + "Topic-A"

```bash
docker exec -it kafka bash -c "kafka-topics --create --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1 --topic Topic-A"
docker exec -it kafka bash -c "kafka-console-consumer --bootstrap-server localhost:9092 --topic Topic-A --group Grupo-A"
```

### Criar tópico e grupo "Grupo-B" + "Topic-B"

```bash
docker exec -it kafka bash -c "kafka-topics --create --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1 --topic Topic-B"
docker exec -it kafka bash -c "kafka-console-consumer --bootstrap-server localhost:9092 --topic Topic-B --group Grupo-B"
```

## Offset

### O que é offset?

É um ponteiro que indica até onde um grupo já consumiu o tópico. Cada partição possui seu próprio offset.

Resetar offset para ignorar mensagens antigas

```bash
docker exec -it kafka bash -c "kafka-consumer-groups --bootstrap-server localhost:9092 --group meu-grupo --reset-offsets --to-latest --execute --topic topic-test"
```

## Comandos úteis

### Ver arquivos no container

```bash
docker exec -it kafka bash -c "ls /usr/bin | grep kafka"
```

### Logs do Kafka:

```bash
docker logs kafka
```

### Teste do Docker:

```bash
docker run hello-world
```

## Dúvidas respondidas

É possível enviar mensagem com apenas 1 comando? `Sim, usando echo + pipe`

```bash
echo '{"nome":"thon","idade":40}' | docker exec -i kafka bash -c "kafka-console-producer --bootstrap-server localhost:9092 --topic Topic-A"
```

É possível enviar várias mensagens de uma vez? `Sim`

```bash
echo -e '{"a":1}\n{"b":2}\n{"c":3}' | docker exec -i kafka bash -c "kafka-console-producer --bootstrap-server localhost:9092 --topic Topic-A"
```

Como mudar o IP/porta do Kafka? `Edite`

```yaml
KAFKA_LISTENERS: PLAINTEXT://0.0.0.0:9094
KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9094
```

### 10 Curiosidades / Setores que usam Kafka

Finanças / Bancos

- transações em tempo real
- antifraude
- auditoria

E-commerce

- carrinho de compras
- eventos de navegação
- rastreamento de pedidos

IoT

- Milhões de sensores publicando mensagens por segundo.

Games

Plataformas usam Kafka para

- telemetria
- matchmaking
- logs de gameplay

Dados em streaming

- Frameworks integrados:
- Apache Flink
- Apache Spark
- ksqlDB