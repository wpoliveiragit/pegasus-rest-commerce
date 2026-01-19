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

  public static  JwtEncoder createJwtEncoder(String publicKey, String privateKey) {
    RSAKey rsaKey = new RSAKey.Builder(getPublicKey(publicKey))//
        .privateKey(getPrivateKey(privateKey))//
        .keyID(UUID.randomUUID().toString())//
        .build();
    return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(rsaKey)));
  }

  public NimbusJwtDecoder createNimbusJwtDecoder(final String publicKey) {
    return NimbusJwtDecoder.withPublicKey(getPublicKey(publicKey)).build();
  }

  public static RSAPublicKey getPublicKey(String publicKey) {
    try {
      KeyFactory kf = KeyFactory.getInstance(ConstSecUtil.ALGORITHM);
      return (RSAPublicKey) kf.generatePublic(new X509EncodedKeySpec(getDecodedKey(publicKey)));
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  public OAuth2Error createOAuth2Error() {
    return new OAuth2Error(ConstSecUtil.MSG_INVALID_TOKEN, ConstSecUtil.MSG_INVALID_AUDIENCE, null);
  }

  public  static  RSAPrivateKey getPrivateKey(String privateKey) {
    try {
      KeyFactory kf = KeyFactory.getInstance(ConstSecUtil.ALGORITHM);
      return (RSAPrivateKey) kf.generatePrivate(new PKCS8EncodedKeySpec(getDecodedKey(privateKey)));
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  private static byte[] getDecodedKey(String keyPem) {
    String content = keyPem.replaceAll(ConstSecUtil.REGEX_REPLACE_PP, ConstSecUtil.TXT_BLANK);
    content = content.replaceAll(ConstSecUtil.REGEX_REPLACE_BLANK, ConstSecUtil.TXT_BLANK);
    return Base64.getDecoder().decode(content);
  }

}