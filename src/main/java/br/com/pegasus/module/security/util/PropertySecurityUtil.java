package br.com.pegasus.module.security.util;

import org.springframework.core.env.Environment;

import java.util.List;

public final class PropertySecurityUtil {

  private final EnvUtil cMethod;
  private final Environment env;

  public PropertySecurityUtil(Environment env) {
    this.env = env;
    this.cMethod = new EnvUtil(env);
  }

  /**
   * Quem emitiu o token.
   *
   * @return
   */
  public String getProjectName() {
    return cMethod.getRequiredProp(ConstSecUtil.PROP_PROJECT_NAME);
  }

  public String getAudience() {
    return cMethod.getRequiredProp(ConstSecUtil.PROP_AUDIENCE);
  }

  /**
   * chave da mensagem adicional.
   *
   * @return
   */
  public String getClaimName() {
    return cMethod.getRequiredProp(ConstSecUtil.PROP_CLAIM_NAME);
  }

  /**
   * Texto da mensagem adicional.
   *
   * @return
   */
  public String getClaimValue() {
    return cMethod.getRequiredProp(ConstSecUtil.PROP_CLAIM_VALUE);
  }

  /**
   * Tempo de bloqueio de uso após a criação (data atual + propriedade).
   *
   * @return
   */
  public int getValidAfterSeconds() {
    return cMethod.getRequiredIntProp(ConstSecUtil.PROP_VALID_AFTER_SECONDS);
  }

  /**
   * Quando vai expira (data atual + propriedade)
   *
   * @return
   */
  public int getExpiresAt() {
    return cMethod.getRequiredIntProp(ConstSecUtil.PROP_EXPIRES_AT);
  }

  public boolean getEnableH2Console() {
    return cMethod.getBooleanPropOrFalse(ConstSecUtil.PROP_ENABLE_H2_CONSOLE);
  }

  public List<String> getOpenRoutes() {
    return cMethod.getStringListProp(ConstSecUtil.PROP_OPEN_ROUTES);
  }

  public boolean getEnable() {
    return env.getProperty(ConstSecUtil.PROP_ENABLED, Boolean.class, Boolean.TRUE);
  }

}
