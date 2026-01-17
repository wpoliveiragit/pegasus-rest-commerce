package br.com.pegasus.module.security;

public interface JwtTokenSecurity {
  String createToken(String subject);
}
