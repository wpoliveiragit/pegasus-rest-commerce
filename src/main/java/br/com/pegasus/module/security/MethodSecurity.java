package br.com.pegasus.module.security;

import org.slf4j.helpers.MessageFormatter;
import org.springframework.core.env.Environment;

import java.util.Base64;

public final class MethodSecurity {

  public static String getReqProp(Environment env, String key){
    String prop = env.getProperty(key);
    if (prop == null) {
      throw new IllegalStateException(MessageFormatter.format(ConstSecurity.MSG_EXCEPTION_PROPERTY, key).getMessage());
    }
    return prop;
  }

  public static int getReqIntProp(Environment env, String key){
    Integer prop = env.getProperty(key, Integer.class);
    if (prop == null) {
      throw new IllegalStateException(MessageFormatter.format(ConstSecurity.MSG_EXCEPTION_PROPERTY, key).getMessage());
    }
    return prop;
  }

  public static byte[] getDecodedKey(String keyPem) {
    String content1 = keyPem.replaceAll(ConstSecurity.REG_EX_REPLACE_PP, ConstSecurity.TXT_BLANK)
        .replaceAll(ConstSecurity.REG_EX_REPLACE_BLANK, ConstSecurity.TXT_BLANK);
    return Base64.getDecoder().decode(content1);
  }

}
