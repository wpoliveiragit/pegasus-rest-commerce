//package br.com.pegasus.api.rest.commerce.app.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.oauth2.jwt.JwtClaimsSet;
//import org.springframework.security.oauth2.jwt.JwtEncoder;
//import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.time.Instant;
//import java.util.Map;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/oauth")
//public class AuthController {
//
//  private final JwtEncoder encoder;
//
//  @PostMapping(value = "/token", consumes = "application/x-www-form-urlencoded")
//  public Map<String, String> token(@RequestParam String username) {
//    Instant now = Instant.now();
//    JwtClaimsSet claims = JwtClaimsSet.builder()//
//        .subject(username)//
//        .issuedAt(now)//
//        .expiresAt(now.plusSeconds(60))//
//        .claim("scope", "read write")//
//        .build();
//
//    return Map.of("access_token", encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue());
//  }
//}