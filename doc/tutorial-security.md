jwt

----

## Principais atributos do JwtClaimsSet

### subject(String sub)

Identifica quem é o “dono” do token, ou seja, quem foi autenticado.

Normalmente representa:

- usuário
- sistema
- client_id

```java
.subject("user123")
```

---

### issuer(String iss)

Identifica quem emitiu o token.  
Quem valida o token usa esse valor para decidir se confia no emissor.

```java
.issuer("pegasus-api-rest-commerce")
```

---

### audience(List<String> aud)

Define para quem o token é destinado.  
Somente sistemas listados aqui devem aceitar esse token.

```java
.audience(List.of("security-gateway"))
```

---

### issuedAt(Instant iat)

Momento exato em que o token foi criado.

```java
.issuedAt(Instant.now())
```

---

### expiresAt(Instant exp)

Momento em que o token deixa de ser válido.  
Após essa data, o Spring Security rejeita automaticamente o token.

```java
.expiresAt(now.plusSeconds(300)) // 5 minutos
```

---

### notBefore(Instant nbf)

Antes desse instante o token é inválido.  
Usado como “tempo de carência” após a criação.

```java
.notBefore(now.plusSeconds(10)) // só vale após 10s
```

---

### id(String jti)

Identificador único do token. Usado para:

- rastreio
- blacklist
- auditoria
- revogação manual

```java
.id(UUID.randomUUID().

toString())
```

---

### claim(String name, Object value) — Claims customizados

Permite adicionar dados extras ao token.  
Usado para informações do seu domínio, como:

- versão da API
- tipo de usuário
- escopo simples
- flags internas

```java
.claim("version","v1")
.

claim("role","ADMIN")
```

---

### Exemplo completo

```java
JwtClaimsSet claims = JwtClaimsSet.builder()
    .subject(subject)                              // dono do token
    .id(UUID.randomUUID().toString())              // id único
    .issuedAt(now)                                 // quando foi criado
    .issuer(name)                                  // quem criou
    .audience(List.of(audience))                   // quem pode usar
    .expiresAt(now.plusSeconds(expiresAt))         // quando expira
    .notBefore(now.plusSeconds(validAfterSeconds)) // carência
    .claim(claimKey, claimValue)                   // dados extras
    .build();
```

---

### Tabela resumo

| Claim JWT | Método builder | Pra quê                       |
|-----------|----------------|-------------------------------|
| sub       | subject        | Dono do token                 |
| jti       | id             | Identificador único           |
| iat       | issuedAt       | Quando foi criado             |
| iss       | issuer         | Emissor do token              |
| aud       | audience       | Quem pode aceitar             |
| exp       | expiresAt      | Quando expira                 |
| nbf       | notBefore      | Só vale depois desse instante |
| custom    | claim          | Dados extras do seu domínio   |

## Regras de segurança já aplicadas por default

- expiresAt (exp): Se now > exp → token inválido
- notBefore (nbf): Se now < nbf → token ainda não é válido
- issuedAt (iat): Valida se não está muito no futuro (proteção contra relógio errado)
- Formato básico do JWT: Estrutura de assinatura RSA válida com sua public key
- Algoritmo compatível: Garante que o token foi assinado com algoritmo esperado

----

1) Como gerar a chave para colocar na propriedade

Usando OpenSSL:

# gera chave privada (via console)

```shell
openssl genpkey -algorithm RSA -out private.pem -pkeyopt rsa_keygen_bits:2048
```

# Gera a chave pública (via console) extraindo da chave privada

```shell
openssl rsa -pubout -in private.pem -out public.pem
```

Depois copie o conteúdo dos arquivos:

```text
-----BEGIN PRIVATE KEY-----
MIIEugIBADANBgkqhkiG9w0BAQEFAASCBKQwggSgAgEAAoIBAQCiaCBPO/r8pdWZ
6e4sq5mBmUHu+YROZaLd9CgzMs5eRtKLHSSsTbKw59rFrs24iAofAYUZ8DB5dHCz
hCF2v4KZp8GYbN7Wlmq0gjPNa9uA41qfzlbyLSdCz4G1D59jQ+hSZ9xiacYMT6Zr
bE1FU4gBFW8B+H8Bcz6nSJDpx//CvPYh3HvCgTNo6U4hoSrhAVGgC1BgY0IVe5wb
ry8Zei85KAmr2duFputulCAdOfY6qUfwojKZgBOjkbblD0wakS9KSANiwIt07GSC
ZmVRS5lm5tt2jb28Imv0obwyvoVO4xDl8WnJ0Md0C8tt98xY3NBxtZk+xurrHnyC
8wCRe4+pAgMBAAECgf8g/QWTEpvotcatfNbkO/byXC8IW6aERiDO9Mk1nRxOChS6
LTFFzoJ9bJsDjH48/WUQ5z4iBqfSA9TgofYqKLxeebTZQUQCUotaHxhmoYH0rDm8
PC1tA7710zCFlqbJgwGtu2sIknOoDIXBTD5l1pHJppDl1ZP2t3ICiLrmwbkx1N1J
pPxaJQmmBtN59UxpFQv06XTsur9l5yeKiSg3NhUZlzIsQaIyGTHSOi5eWm90gNNi
LV6ptvcXOGP2EO9DzK9UH48kXajL/7EO3hAAbR0z0nkB7+SGBFrB4ldUtLG430o/
IdhKuc96a+dGI+NvUuaIQ+ylDvT0SilzPO6p7UECgYEA0m6JN7fwv8CM+xQbXzOS
+NBwwoNYl2evend3sqS2/+5lnrqPMGw8pLtBBOmmcYYJi2xtVXEGXY7n/z5MRSm1
sKHIqPoOrIPiJQMm9zyBurzdslu01vmwO0uZDkqncnFAY+kwlLSx4w6lnh9T2CPh
WZqaXlFfPw/xycm5V+r2ew0CgYEAxZNIEtPEr8Cowc6VrFLxBO1YhNJHW0giBtb4
+MG33v2Zk33Vd6SUqY7JP6cZaBxPlbB/7LbUKET7CnNIdorIDMvnSlVfJ/yRsHKm
OJJh6laoTGeZZH33trG0gIpSBumdsVNKOmEUisa3/ZNZTuoXWgKY+164Zt7iR7JV
UITAkA0CgYBlZ/scLLoJzeKihDgEkrf6y4frVJx2qfK4U6YNIq9Fej7iVQj3ztES
mWJl2arp/ivPUnsFt6fXoixOplje45C5A93NAGPd4tgx+ejnL/NcZ/N2JhdpmX3B
tVaWuoci8UyUWSWeI82tm7tYDcS73d6U7ZoCZbg+jZJ5KYr1wTbyaQKBgAkooKiu
xGlZRvlk+C860Q1nazkM/l3O5DTo9jWh1UIzA1GA/cpECNgrVEztFBJtbYsE2YlB
mGgden2rhmpoWImvUhNyDa0u2hoR1n682mkgh0CzdLrh0//WEQX8Y30Ki1LTB5fZ
NDiSqajJkdREqbx0bl8IrwelwVuNesL2xG5pAoGAUQh8OwHNTbAEr8vXHZXowE4D
eZ8vXcmsRtETLQKWGeKFK+Ojm5JrHnYnGUaa1P5LjmHrbjOe32pDa8xyjcZ12Y/7
UfnkcVhntvj1xY7l1cGfghjDsxzNYJ2C6DsbD4p4km+WHpdQu5+koQ82VJeESQZy
ZfYfSpkgoD1441uXFdc=
-----END PRIVATE KEY-----
```

e

```text
-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAomggTzv6/KXVmenuLKuZ
gZlB7vmETmWi3fQoMzLOXkbSix0krE2ysOfaxa7NuIgKHwGFGfAweXRws4Qhdr+C
mafBmGze1pZqtIIzzWvbgONan85W8i0nQs+BtQ+fY0PoUmfcYmnGDE+ma2xNRVOI
ARVvAfh/AXM+p0iQ6cf/wrz2Idx7woEzaOlOIaEq4QFRoAtQYGNCFXucG68vGXov
OSgJq9nbhabrbpQgHTn2OqlH8KIymYATo5G25Q9MGpEvSkgDYsCLdOxkgmZlUUuZ
Zubbdo29vCJr9KG8Mr6FTuMQ5fFpydDHdAvLbffMWNzQcbWZPsbq6x58gvMAkXuP
qQIDAQAB
-----END PUBLIC KEY-----
```

Cole exatamente assim no application.yml.

