# Docker - Kafka
```json
{
  "orderSequence": 10,
  "trace": [
    {
      "order": 1,
      "timestamp": "2025-12-16T00:22:33.265713Z",
      "message": "START: Delegate.Product#createProduct"
    },
    {
      "order": 2,
      "timestamp": "2025-12-16T00:22:33.269296500Z",
      "message": "START: Mapper.Product#delegateToService"
    },
    {
      "order": 3,
      "timestamp": "2025-12-16T00:22:33.272296Z",
      "message": "END: Mapper.Product#delegateToService"
    },
    {
      "order": 4,
      "timestamp": "2025-12-16T00:22:33.318358900Z",
      "message": "START: Service.Product#create"
    },
    {
      "order": 5,
      "timestamp": "2025-12-16T00:22:33.320378800Z",
      "message": " [★ INICIOU]ProductRestCore#checkNameConflict"
    },
    {
      "order": 6,
      "timestamp": "2025-12-16T00:22:33.322905200Z",
      "message": "START: Repository.Product#findByName"
    },
    {
      "order": 7,
      "timestamp": "2025-12-16T00:22:33.665678400Z",
      "message": "START: Mapper.Product#jpaToService"
    },
    {
      "order": 8,
      "timestamp": "2025-12-16T00:22:33.665678400Z",
      "message": "END: Mapper.Product#jpaToService"
    },
    {
      "order": 9,
      "timestamp": "2025-12-16T00:22:33.665678400Z",
      "message": "END: Repository.Product#findByName"
    },
    {
      "order": 10,
      "timestamp": "2025-12-16T00:22:33.672404200Z",
      "message": "FAIL: Name already registered"
    }
  ],
  "traceId": "a12f3cde-45b6-7890-abc1-def234567890",
  "method": "POST",
  "url": "/product",
  "requestSize": 61,
  "requestStartTime": 1765844553256,
  "keyDistributionSummary": "POST/product409",
  "status": "409",
  "runtime": 422,
  "responseSize": 0,
  "tags": [
    {
      "key": "method",
      "value": "POST"
    },
    {
      "key": "url",
      "value": "/product"
    },
    {
      "key": "status",
      "value": "409"
    }
  ]
}
```

## docker-compose.yml

Configurações do servidor Kafka: Escolha um diretório (ex: `C:\dev\instalados\kafka`)
e crie o arquivo `docker-compose.yml` com as configurações abaixo

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

## Instalação

1. Entre na BIOS e verifique se `Intel VT-x (Intel)` / `AMD-V (AMD)` / `SVM Mode (AMD)` esta como `Enabled`
1. abra um terminal
1. execute o comando `wsl --install` para instale o WSL2. Espera finalizar a instalação (pode fechar o terminal)
1. entre em https://www.docker.com/products/docker-desktop/
1. baixo o `docker desktop` (AMD64)
1. instale normalmente, mas habilite `Use WSL 2 instead of Hyper-V` e marque `Enable integration with WSL 2`
1. Reinicie o PCs
1. Abra um terminal (Administrador) e execute os comandos
    1. `dism.exe /online /enable-feature /featurename:Microsoft-Windows-Subsystem-Linux /all /norestart`
    1. `dism.exe /online /enable-feature /featurename:VirtualMachinePlatform /all /norestart`
1. Reinicie o PCs
1. Abra um terminal (Administrador) e execute os comandos para reiniciar o docker corretamente e garantir a versão:
    - `wsl --set-default-version 2` (A operação foi concluída com êxito.)
    - `net stop com.docker.service`
    - `net start com.docker.service`
1. abra o `Docker Desktop`
1. abra um terminal e execute os comando para baixar e subir os containers :
    - `docker compose pull`
    - `docker compose up -d`

1. verifique os containers com o comando `docker ps` (deve aparecer containers 'zookeeper' e 'kafka' com STATUS "Up")
1. pode verificar logs com os comandos
    - `docker compose logs -f kafka`
    - `docker compose logs -f zookeeper`

1. abra um terminal e navegue até o diretório do arquivo `docker-compose.yaml`
1. execute
    1. `docker compose down`
    1. `docker compose up -d`
1. Verifique o estado do WSL `wsl --status`

## Verificação

- O servidor kafka estará ouvindo em `localhost:9092`

Reinicie o backend do Docker (sem abrir o Desktop)

feche o Docker Desktop e abra o PowerShell como Administrador:

- net stop com.docker.service
- net start com.docker.service
- wsl --shutdown

Outros comandos

- docker logs kafka
- docker version
- docker info
- docker run hello-world

Subir servidor `docker compose up -d`

```bash
$ docker compose up -d
[+] Running 2/2
✔ Container zookeeper  Running              0.0s
✔ Container kafka      Running              0.0s
```

Lista containers em execução:

- `docker ps` → mostra ID, imagem, status, portas e nomes dos containers ativos.
- `docker ps -a` → mostra todos, inclusive parados.

```bash
CONTAINER ID   IMAGE                             COMMAND                  CREATED        STATUS        PORTS                                         NAMES
cd0497c2fd37   confluentinc/cp-kafka:7.4.1       "/etc/confluent/dock…"   43 hours ago   Up 43 hours   0.0.0.0:9092->9092/tcp, [::]:9092->9092/tcp   kafka
525485fc252f   confluentinc/cp-zookeeper:7.4.1   "/etc/confluent/dock…"   43 hours ago   Up 43 hours   0.0.0.0:2181->2181/tcp, [::]:2181->2181/tcp   zookeeper
```

version `docker version`

```bash
$ docker version
  Client:
  Version:           29.0.1
  API version:       1.52
  Go version:        go1.25.4
  Git commit:        eedd969
  Built:             Fri Nov 14 16:19:55 2025
  OS/Arch:           windows/amd64
  Context:           desktop-linux
  
  Server: Docker Desktop 4.52.0 (210994)
  Engine:
  Version:          29.0.1
  API version:      1.52 (minimum version 1.44)
  Go version:       go1.25.4
  Git commit:       198b5e3
  Built:            Fri Nov 14 16:17:57 2025
  OS/Arch:          linux/amd64
  Experimental:     false
  containerd:
  Version:          v2.1.5
  GitCommit:        fcd43222d6b07379a4be9786bda52438f0dd16a1
  runc:
  Version:          1.3.3
  GitCommit:        v1.3.3-0-gd842d771
  docker-init:
  Version:          0.19.0
  GitCommit:        de40ad0
```

- como mudar o ip do kafka?

## Criar um novo group

docker exec -it kafka bash -c "kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1
--partitions 1 --topic novo-topico"
docker exec -it kafka bash -c "kafka-console-consumer --bootstrap-server localhost:9092 --topic novo-topico --group
grupo-novo"

## Up DOWN server

docker compose down -v
docker compose up -d

## producer

abre um prompt onde cada linha digitada vira uma mensagem enviada para test-topic.

```bash
docker exec -it kafka-local kafka-console-producer.sh --broker-list localhost:9092 --topic test-topic

docker exec -it kafka bash -c "kafka-console-producer --bootstrap-server localhost:9092 --topic topic-test"
```

{"nome":"thon","idade":33}

```bash
docker exec -it kafka-local kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test-topic --from-beginning
```

## consumer

docker exec -it kafka bash -c "kafka-console-consumer --bootstrap-server localhost:9092 --topic topic-test
--from-beginning"

## Resetar o offset do consumer

Se você só quer que o consumer pare de ler mensagens antigas
docker exec -it kafka bash -c "kafka-consumer-groups --bootstrap-server localhost:9092 --group meu-grupo --reset-offsets
--to-latest --execute --topic topic-test"

## Cria topic

docker exec -it kafka bash -c "kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1
--partitions 1 --topic topic-test"

## lista topic

docker exec -it kafka bash -c "kafka-topics --bootstrap-server localhost:9092 --list"

## Deleta topic

docker exec -it kafka bash -c "kafka-topics --bootstrap-server localhost:9092 --delete --topic topic-test"

# Não sei onde colocar

- `docker exec -it kafka bash -c "ls /usr/bin | grep kafka"`
- `docker compose down -v`
- `docker compose up -d`
- `docker exec -it kafka bash -c "grep delete_topic_enable /etc/kafka/server.properties"`
- `docker exec -it kafka bash -c "kafka-topics --bootstrap-server localhost:9092 --create --topic test --partitions 1 --replication-factor 1"`
- `docker exec -it kafka bash -c "kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic topic-test"`
- `docker exec -it kafka bash -c "kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic novo-topico"`
- `docker exec -it kafka bash -c "kafka-console-consumer --bootstrap-server localhost:9092 --topic novo-topico --group grupo-novo"`
- `docker exec -it kafka bash -c "kafka-topics --bootstrap-server localhost:9092 --delete --topic test"`
- `docker exec -it kafka bash -c "kafka-console-producer --bootstrap-server localhost:9092 --topic topic-test"`
- `{"nome":"thon","idade":33}`
- `docker exec -it kafka bash -c "kafka-console-consumer --bootstrap-server localhost:9092 --topic topic-test --from-beginning"`
- `docker exec -it kafka bash -c "kafka-console-producer --bootstrap-server localhost:9092 --topic topic-test"`
- `docker logs kafka | grep delete`
- `docker exec -it kafka bash -c "kafka-topics --bootstrap-server localhost:9092 --list"`
- `docker exec -it kafka bash -c "kafka-consumer-groups --bootstrap-server localhost:9092 --group meu-grupo --reset-offsets --to-latest --execute --topic topic-test"`
- `docker exec -it kafka bash -c "kafka-console-consumer --bootstrap-server localhost:9092 --topic topic-test --group meu-grupo"`
- `docker exec -it kafka bash -c "kafka-configs --bootstrap-server localhost:9092 --alter --entity-type topics --entity-name topic-test --add-config retention.ms=1000"`
- `docker exec -it kafka bash -c "kafka-topics --create --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1 --topic Topic-A"
- `docker exec -it kafka bash -c "kafka-topics --create --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1 --topic Topic-B"`

# Duvidas

- É possível em apenas um comando abrir o console e enviar uma mensagem de alguma forma? gostaria de ao em vez de usar 2
  comando pra enviar uma mensagem, que tudo fosse em uma só
- É possivel enviar varias mensagens de uma vez?
- o que pe offset e como resetar ele?


```
# DATABASE - Configurações do Banco de Dados H2
spring.datasource.url: 'jdbc:h2:mem:banco-dados-h2'  # URL do banco de dados
spring.datasource.driver-class-name: 'org.h2.Driver'  # Driver do banco de dados H2
spring.datasource.username: 'sa'  # Usuário do banco de dados
spring.datasource.password: ''  # Senha do banco de dados

# CONSOLE H2 - Acesso ao Console do H2 via Navegador
spring.h2.console.enabled: true  # Habilita o console do H2
spring.h2.console.path: /h2-console  # Define o caminho para acessar o console no navegador (ex: http://localhost:8080/h2-console)

# JPA - Configurações de Persistência
spring.jpa.show-sql: false  # Exibe as consultas SQL geradas pelo JPA no console
spring.jpa.hibernate.ddl-auto: create  # Define o comportamento do Hibernate em relação ao esquema do banco (no caso, cria o esquema e apaga os dados existentes)
hibernate.dialect: org.hibernate.dialect.H2Dialect  # Dialeto específico do H2 para o Hibernate (H2 não precisa)

```