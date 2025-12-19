package br.com.pegasus.api.rest.commerce.infra.util;

import ch.qos.logback.classic.Level;

import java.util.Map;

public final class ConstUtil {

  public static final String LOGBACK_TRACE_LOG = "TRACE_LOG";


  // PATH
  public static final String PATH_OPENAPI = "openapi.yaml";
  public static final String PATH_SITE_INDEX = "templates/index.html";
  public static final String PATH_SITE_LICENSE = "templates/license.html";
  public static final String PATH_SITE_TERMS = "templates/terms.html";

  // REQUEST
  public static final String REST_HEADER_X_TRACE_ID = "X-Trace-Id";

  // KAFKA
  public static final String KAFKA_PRODUCT_LIFECYCLE_TOPIC = "PRODUCT-LIFECYCLE-TOPIC";
  public static final String KAFKA_CREATE_EVENT = "create";
  public static final String KAFKA_UPDATE_EVENT = "update";
  public static final String KAFKA_DELETE_EVENT = "delete";

  // METRIC
  public static final String METRIC_TAG_METHOD = "method";
  public static final String METRIC_TAG_STATUS = "status";
  public static final String METRIC_TAG_URL = "url";

  public static final String METRIC_NO_ACTIVE_REQUEST_MESSAGE = "Nenhuma requisição HTTP ativa no contexto atual. Request sem metrica de 'track'";
  public static final String METRIC_GAUGE_DESC = "Número de requisições HTTP em andamento";
  public static final String METRIC_RES_SIZE_NAME = "http_response_size_bytes";
  public static final String METRIC_REQ_SIZE_NAME = "http_request_size_bytes";
  public static final String METRIC_TIME_NAME = "http_request_duration_ms";
  public static final String METRIC_COUNTER_NAME = "http_requests_total";
  public static final String METRIC_GAUGE_NAME = "http_active_requests";


  public static final String REGEX_TRACE = "[{}] {}.{}";
  public static final String REGEX_TRACE_FAIL = "[{}] {}";

  public static final String REGEX_TRACE_CACHE = "[✪ cache(↻): {}]";
  public static final String REGEX_TRACE_ADVICE = " [✪]: {}";
  public static final String REGEX_TRACE_CONTROLLER_INIT = " [★] {}#{}";
  public static final String REGEX_TRACE_CONTROLLER_END = " [☆] {}#{}";
  public static final String REGEX_TRACE_COMPONENT_INIT = " [★] {}#{}";
  public static final String REGEX_TRACE_COMPONENT_END = " [☆⮒] {}#{}";

  public static final String METRIC_TRACK_LOG_PATTERN_FAIL = "[x-trace-id: {}]\n[Url: {} {}] [size request: {}]\n[trace]{}";
  public static final String METRIC_TRACK_LOG_PATTERN_OK = "[x-trace-id: {}] [Url: {} {}] [size request: {}] [message: {}]";

  // SECURITY
  public static final String SECURITY_ALGORITHM = "RSA";
  public static final String SECURITY_CLAIM_SCOPE = "scope";
  public static final String SECURITY_CLAIM_READ_WRITE = "read write";

  // ANSI COLOR
  public static final String COLOR_RESET = "\u001B[0m";

  public static final String COLOR_YELLOW = "\u001B[33m";
  public static final String COLOR_GREEN = "\u001B[32m";
  public static final String COLOR_BLUE = "\u001B[34m";
  public static final String COLOR_WHITE = "\u001B[37m";
  public static final String COLOR_CYAN = "\u001B[36m";
  public static final String COLOR_RED = "\u001B[31m";

  // [APP EXCEPTION]
  public final static String EXCEPTION_CONFLICT_NAME_MESSAGE = "Name already registered";
  public final static String EXCEPTION_NOT_FOUND_MESSAGE = "Element not found";

  // [TEXTOS]
  public static final String SPACE = " ";
  public static final String KEY_VALUE_SEP = ": ";
  public static final String ENTRY_SEP = "; ";

  // [BOOLEAN]
  public static final Boolean BOOLEAN_TRUE = true;

  // [CONSTANTES NUMÉRICOS]
  public static final int INT_0 = 0;
  public static final int INT_10 = 10;
  public static final int INT_60 = 60;
  public static final int INT_100 = 100;
  public static final int INT_1000 = 1000;
  public static final int INT_2048 = 2048;

  public static final long LONG_0 = 0L;
  public static final double DOUBLE_1000 = 1000.0;

  public static final Map<Integer, String> COLLOR_MAP = Map.of(//
      Level.INFO_INT, ConstUtil.COLOR_CYAN, //
      Level.WARN_INT, ConstUtil.COLOR_YELLOW, //
      Level.ERROR_INT, ConstUtil.COLOR_RED, //
      Level.DEBUG_INT, ConstUtil.COLOR_GREEN, //
      Level.TRACE_INT, ConstUtil.COLOR_WHITE //
  );

}