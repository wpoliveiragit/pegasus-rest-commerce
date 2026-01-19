package br.com.pegasus.module.security.util;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import org.springframework.core.env.Environment;
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

public final class MethodSecurityUtil {

  private final EnvUtil envUtil;

  public MethodSecurityUtil(Environment env) {
    this.envUtil = new EnvUtil(env);
  }

  public JwtEncoder createJwtEncoder() {
    RSAKey rsaKey = new RSAKey.Builder(getPublicKey())//
        .privateKey(getPrivateKey())//
        .keyID(UUID.randomUUID().toString())//
        .build();
    return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(rsaKey)));
  }

  public NimbusJwtDecoder createNimbusJwtDecoder() {
    return NimbusJwtDecoder.withPublicKey(getPublicKey()).build();
  }

  public RSAPublicKey getPublicKey() {
    try {
      String sKey = envUtil.getRequiredProp(ConstSecUtil.PROP_RSA_PUBLIC_KEY);
      KeyFactory kf = KeyFactory.getInstance(ConstSecUtil.ALGORITHM);
      return (RSAPublicKey) kf.generatePublic(new X509EncodedKeySpec(getDecodedKey(sKey)));
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  public OAuth2Error createOAuth2Error() {
    return new OAuth2Error(ConstSecUtil.MSG_INVALID_TOKEN, ConstSecUtil.MSG_INVALID_AUDIENCE, null);
  }
  public RSAPrivateKey getPrivateKey() {
    try {
      String sKey = envUtil.getRequiredProp(ConstSecUtil.PROP_RSA_PRIVATE_KEY);
      KeyFactory kf = KeyFactory.getInstance(ConstSecUtil.ALGORITHM);
      return (RSAPrivateKey) kf.generatePrivate(new PKCS8EncodedKeySpec(getDecodedKey(sKey)));
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  private byte[] getDecodedKey(String keyPem) {
    String content = keyPem.replaceAll(ConstSecUtil.REGEX_REPLACE_PP, ConstSecUtil.TXT_BLANK);
    content = content.replaceAll(ConstSecUtil.REGEX_REPLACE_BLANK, ConstSecUtil.TXT_BLANK);
    return Base64.getDecoder().decode(content);
  }

}
