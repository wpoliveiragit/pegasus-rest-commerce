# Pegasus Security

- Este projeto foi criado para fins de estudo
- O projeto esta no formato de biblioteca, por conta de fins praticos, mas o ideal era que ele fosse uma API REST

## Adicionando a biblioteca

**Adicione a dependencia**

```java
```

**Application.yml**

```yaml
```

**Explicando cada propriedade**

- `api.security.proj-name` 
  - Usado para auditoria
  - Obrigatírio, exemplo: pegasus-api-rest-commerce 
- `api.security.audience` 
  - Usado para alditoria
  - Obrigatório, exemplo: security-gateway 
- `api.security.expires-at` 
  - Opcional, exemplo: 60
- `api.security.valid-after-seconds`
  - usado 
  - Valor deve ser inteiro e positivo.
  - opcional, Exemplo: 5
- `api.security.claim.name` version # Obrigatório para minha auditoria
- `api.security.claim.value` v1 # Obrigatório para minha auditoria
- `api.security.enable-h2-console` true # Opcional: permite o acesso ao console web do h2
- `api.security.enable-log` true # Opcional: habilita logs do security
- `api.security.open-routes` # Opcional: endpoints q são liberados do security
- `api.security.rsa.public-key` # Obrigatorio, mas a chave pode ser alterada
- `api.security.rsa.privare-key` # Obrigatorio, mas a chave pode ser alterada

**Adicione a anotação `@EnabledOAuthWebSecurity` em algum Bean do projeto**

## Uma sugestaode uso

- Crie um controller
  - Crie o endpoint para obter o token
  - opcional: crie um segundo endpoint para testar
- libere apenas o endpoint do token pela propriedade `api.security.open-routes` (lembrando q ela é uma lista)
- faça uma request
**exemplo**

```java
import br.com.pegasus.module.security.JwtTokenSecurity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class AppController {

  private final JwtTokenSecurity jwtSecurity;

  @GetMapping("/app/test")
  public ResponseEntity<String> appTest() {
    return ResponseEntity.ok("ok");
  }

  @PostMapping("/oauth/token")
  public ResponseEntity<TokenResponse> generateToken(@RequestBody TokenRequest request) {
    return ResponseEntity.ok(new TokenResponse(jwtSecurity.createToken(request.getUsername())));
  }

  // DTO de entrada
  @Setter
  @Getter
  public static class TokenRequest {
    private String username;
  }

  // DTO de saída
  public record TokenResponse(String token) {
  }
}
```

**Onde adicionar o token?**
Por padrão OAuth2/JWT, o token vai no header Authorization.

**formato**

```
Authorization: Bearer <seu_token_aqui>
```