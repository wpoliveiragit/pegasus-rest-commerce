package br.com.pegasus.api.rest.commerce.app.controller;

import br.com.pegasus.api.rest.commerce.app.handler.HttpMethodHandler;
import br.com.pegasus.api.rest.commerce.infra.security.MethodJwtSecurity;
import br.com.pegasus.gen.openapi.type.CreateToken200Response;
import br.com.pegasus.gen.openapi.type.ExceptionResponseType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class TokenController {

  private final MethodJwtSecurity jwtSecurity;
  private final HttpMethodHandler httpMethod;

  @PostMapping("/token2")
  public ResponseEntity<TokenResponse> generateToken(@RequestBody TokenRequest request) {
    String token = jwtSecurity.createToken(request.getUsername());
    return ResponseEntity.ok(new TokenResponse(token));
  }

  // DTO de entrada
  @Setter
  @Getter
  public static class TokenRequest {
    private String username;

  }

  // DTO de saída
  @Getter
  public static class TokenResponse {
    private final String token;

    public TokenResponse(String token) {
      this.token = token;
    }

  }
}

