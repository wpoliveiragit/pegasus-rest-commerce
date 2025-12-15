package br.com.pegasus.api.rest.commerce.domain.port;

public interface AuthPort {
  String createToken(String username);
}