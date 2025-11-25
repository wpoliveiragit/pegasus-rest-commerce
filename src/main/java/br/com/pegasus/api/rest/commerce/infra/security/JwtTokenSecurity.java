package br.com.pegasus.api.rest.commerce.infra.security;

import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

/** JWT (JSON Web Token) */
@Configuration
public class JwtTokenSecurity {

  private final RSAPublicKey publicKey;
  private final RSAPrivateKey privateKey;

  public JwtTokenSecurity() throws Exception {
    // Gera um par de chaves RSA de 2048 bits
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ConstUtil.SECURITY_ALGORITHM);
    keyPairGenerator.initialize(ConstUtil.INT_2048);
    KeyPair keyPair = keyPairGenerator.generateKeyPair();

    this.publicKey = (RSAPublicKey) keyPair.getPublic();
    this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
  }

  @Bean
  public JwtDecoder jwtDecoder() {//valida
    return NimbusJwtDecoder.withPublicKey(publicKey).build();
  }

  @Bean
  public JwtEncoder jwtEncoder() {// gera
    return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(createImmutableJWKSet())));
  }

  private RSAKey createImmutableJWKSet() {
    return new RSAKey.Builder(publicKey)//
        .privateKey(privateKey)//
        .keyID(UUID.randomUUID().toString())//
        .build();
  }

}
