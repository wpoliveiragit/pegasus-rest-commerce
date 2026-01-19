package br.com.pegasus.api.rest.commerce.app;

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
  private final JwtDecoder jwtDecoder;

  @GetMapping("/app/test")
  public ResponseEntity<String> appTest() {
    return ResponseEntity.ok("ok");
  }

  @PostMapping("/oauth/token")
  public ResponseEntity<TokenResponse> generateToken(@RequestBody TokenRequest request) {
    String token = jwtSecurity.createToken(request.getUsername());
    Jwt decode = jwtDecoder.decode(token);
    decode.getClaims().forEach((key, value) -> System.out.println("KEI: " + key + " VALUE: " + value));
    return ResponseEntity.ok(new TokenResponse(token));
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

