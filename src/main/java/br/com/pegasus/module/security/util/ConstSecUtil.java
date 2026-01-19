package br.com.pegasus.module.security.util;

public final class ConstSecUtil {

  public static final String REGEX_REPLACE_BLANK = "\\s";
//  public static final String REGEX_REPLACE_PP = "-----BEGIN (PUBLIC|PRIVATE) KEY-----|-----END (PUBLIC|PRIVATE) KEY-----";
  public static final String REGEX_REPLACE_PP = "-----\\w+ (PUBLIC|PRIVATE) KEY-----";

  public static final String TXT_BLANK = "";
  public static final String ALGORITHM = "RSA";

  public static final int INT_0 = 0;
  public static final String MSG_INVALID_TOKEN = "invalid_token";
  public static final String MSG_INVALID_AUDIENCE = "Invalid Audience";

  public static final String PATTERN_ALL_PATHS = "/**";

}
