package br.com.pegasus.module.security.util;

public final class ConstSecUtil {

  public static final String REGEX_REPLACE_BLANK = "\\s";
  public static final String REGEX_REPLACE_PP = "-----\\w+ (PUBLIC|PRIVATE) KEY-----";

  public static final String TXT_BLANK = "";
  public static final String ALGORITHM = "RSA";

  public static final int INT_0 = 0;
  public static final String MSG_INVALID_TOKEN = "invalid_token";
  public static final String MSG_INVALID_AUDIENCE = "Invalid Audience";
  public static final String MSG_EXCEPTION_PROPERTY = "The required property {} was not found.";

  public static final String PATTERN_ALL_PATHS = "/**";

  public static final String PROP_PROJECT_NAME = "api.security.proj-name";
  public static final String PROP_ENABLED = "api.security.enabled";
  public static final String PROP_EXPIRES_AT = "api.security.expires-at";
  public static final String PROP_OPEN_ROUTES = "api.security.open-routes";
  public static final String PROP_CLAIM_NAME = "api.security.claim.name";
  public static final String PROP_CLAIM_VALUE = "api.security.claim.value";
  public static final String PROP_VALID_AFTER_SECONDS = "api.security.valid-after-seconds";
  public static final String PROP_AUDIENCE = "api.security.audience";
  public static final String PROP_ENABLE_H2_CONSOLE = "api.security.enable-h2-console";
  public static final String PROP_RSA_PUBLIC_KEY = "api.security.rsa.public-key";
  public static final String PROP_RSA_PRIVATE_KEY = "api.security.rsa.private-key";

}
