package br.com.pegasus.api.rest.commerce.infra.util;

public final class ConstUtil {

  // [APP EXCEPTION]
  public final static String EXCEPTION_CONFLICT_NAME_MESSAGE = "Name already registered";
  public final static String EXCEPTION_NOT_FOUND_MESSAGE = "Element not found";

  // [TEXTOS]
  public static final String SPACE = " ";
  public static final String KEY_VALUE_SEP = ": ";
  public static final String ENTRY_SEP = "; ";

  // [CONSTANTES NUMÉRICOS]
  public static final int INT_0 = 0;
  public static final int INT_100 = 100;

  public static final long LONG_0 = 0L;

  public static final String HEADER_X_TRACE_ID = "X-Trace-Id";

  // METRICs
  public static final String METRIC_TAG_METHOD = "method";
  public static final String METRIC_TAG_URL = "url";
  public static final String METRIC_TAG_STATUS = "status";

  public static final String METRIC_NO_ACTIVE_REQUEST_MESSAGE = "Nenhuma requisição HTTP ativa no contexto atual. Request sem metrica de 'track'";
  public static final String METRIC_COUNTER_NAME = "http_requests_total";
  public static final String METRIC_TIME_NAME = "http_request_duration_ms";
  public static final String METRIC_GAUGE_NAME = "http_active_requests";
  public static final String METRIC_GAUGE_DESC = "Número de requisições HTTP em andamento";
  public static final String METRIC_REQ_SIZE_NAME = "http_request_size_bytes";
  public static final String METRIC_RES_SIZE_NAME = "http_response_size_bytes";

  public static final String METRIC_TRACK_LOG_PATTERN_OK = "[x-trace-id: {}] [Url: {} {}] [size request: {}] [message: {}]";
  public static final String METRIC_TRACK_LOG_PATTERN_FAIL = "[x-trace-id: {}]\n[Url: {} {}] [size request: {}]\n[trace]{}";

}
