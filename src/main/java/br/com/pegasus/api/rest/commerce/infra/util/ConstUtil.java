package br.com.pegasus.api.rest.commerce.infra.util;

public final class ConstUtil {

  // [Textos]

  public static final String T_EMPTY = "";
  public static final String T_SEP = "#:#";// String de separação
  public static final String T_NULL = "null";
  public static final String LOG_STARTED_METHOD = "FindPage ⇉ STARTED";
  public static final String LOG_FINISHED_METHOD = "FindPage ⇉ FINISHED";

  public static final String T_LOG_ADVICE_INTERNAL_SERVER_ERROR = "Internal Server Error → Code: {} Message: {}";
  public static final String T_LOG_ADVICE_NOT_FOUND = "Not Found → Code: {} Message: {}";
  public static final String T_LOG_ADVICE_BAD_REQUEST = "Bad Request → Code: {} Message: {}";
  public static final String T_LOG_ADVICE_CONFLICT = "Conflict → Code: {} Message: {}";
  public static final String T_LOG_ADVICE_UNPROCESSABLE_ENTITY = "Unprocessable Entity → Code: {} Message: {}";

  // ARQUIVOS
  public static final String FILE_OPENAPI = "openapi.yaml";

  // [CONSTANTES CARACTERES]
  public static final char C_0 = '0';

  // INDEX
  public static final int CONFLICT_INIT = 1000;
  public static final int NOT_FOUND_INIT = 2000;
  public static final int BAD_REQUEST_INIT = 3000;
  public static final int UNPROCESSABLE_INIT = 4000;
  public static final int INTERNAL_SERVER_ERROR = 5000;

  // [CONSTANTES NUMÉRICOS]
  public static final int N_0 = 0;
  public static final int N_9 = 9;
  public static final int N_10 = 10;
  public static final int N_11 = 11;

  // [EXPRESSÕES RECULARES]

  // Definição de um par de chaves
  public static final String REGEX_BRACES = "\\{}";
  // Definição de caracteres não numéricos
  public static final String REGEX_N_DIG = "\\D";
  // Definição de um numero repetido de 0 a 10 na String (ex: 00000000000)
  public static final String REGEX_ALL_SAME_DIGITS = "(\\d)\\1{10}";
  // Define padrões de email
  public static final String REGEX_EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
}
