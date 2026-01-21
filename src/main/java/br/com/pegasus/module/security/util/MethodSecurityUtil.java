package br.com.pegasus.module.security.util;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Logger;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;

@Log4j2 // ainda vou adicionar os logs
public final class MethodSecurityUtil {

  public static void logInfo(Logger log, boolean enabledLog, String message, Object... objs) {
    if (enabledLog) {
      log.info(message, objs);
    }
  }

  public static JwtEncoder createJwtEncoder(String publicKey, String privateKey) {
    RSAKey rsaKey = new RSAKey.Builder(getPublicKey(publicKey))//
        .privateKey(getPrivateKey(privateKey))//
        .keyID(UUID.randomUUID().toString())//
        .build();
    return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(rsaKey)));
  }

  public static NimbusJwtDecoder createNimbusJwtDecoder(final String publicKey) {
    return NimbusJwtDecoder.withPublicKey(getPublicKey(publicKey)).build();
  }

  public static RSAPublicKey getPublicKey(String pem) {
    return (RSAPublicKey) buildKey(pem, true);
  }

  public static RSAPrivateKey getPrivateKey(String pem) {
    return (RSAPrivateKey) buildKey(pem, false);
  }

  private static Object buildKey(String pem, boolean pub) {
    try {
      byte[] decoded = Base64.getDecoder().decode(getPemContent(pem));
      KeyFactory kf = KeyFactory.getInstance("RSA");
      return pub//
          ? kf.generatePublic(new X509EncodedKeySpec(decoded))//
          : kf.generatePrivate(new PKCS8EncodedKeySpec(decoded));
    } catch (Exception e) {
      throw new IllegalStateException("Chave RSA inválida", e);
    }
  }

  private static String getPemContent(String pem) {
    return pem.replaceAll("-----\\w+ (PUBLIC|PRIVATE) KEY-----", "").replaceAll("\\s", "");
  }

  public static OAuth2Error createOAuth2Error() {
    return new OAuth2Error("invalid_token", "Invalid Audience", null);
  }

}