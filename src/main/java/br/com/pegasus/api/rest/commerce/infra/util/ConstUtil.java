package br.com.pegasus.api.rest.commerce.infra.util;

public final class ConstUtil {

  // [Textos]
  public static final String T_EMPTY = "";

  // [CONSTANTES CARACTERES]
  public static final char C_0 = '0';

  // [CONSTANTES NUMÉRICOS]
  public static final int N_0 = 0;
  public static final int N_9 = 9;
  public static final int N_10 = 10;
  public static final int N_11 = 11;

  // [EXPRESSÕES RECULARES]

  // Definição de caracteres não numéricos
  public static final String REGEX_N_DIG = "\\D";
  // Definição de um numero repetido de 0 a 10 na String (ex: 00000000000)
  public static final String REGEX_ALL_SAME_DIGITS = "(\\d)\\1{10}";
  // Define padrões de email
  public static final String REGEX_EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

}
