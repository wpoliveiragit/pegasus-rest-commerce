package br.com.pegasus.api.rest.commerce.infra.util;

public final class ConstUtil {

  // [TEXTOS]
  public static final String T_SPACE = " ";

  // [CONSTANTES CARACTERES]
  public static final char CHAR_0 = '0';

  // [CONSTANTES NUMÉRICOS]
  public static final int INT_0 = 0;
  public static final int INT_100 = 100;

  public static final long LONG_0 = 0L;

  public static final String HEADER_X_TRACE_ID = "X-Trace-Id";

  // METRICs
  public static final String METRIC_TAG_METHOD = "method";
  public static final String METRIC_TAG_URL = "url";
  public static final String METRIC_TAG_STATUS = "status";

  public static final String METRIC_COUNTER_NAME = "http_requests_total";
  public static final String METRIC_TIME_NAME = "http_request_duration_ms";
  public static final String METRIC_GAUGE_NAME = "http_active_requests";
  public static final String METRIC_GAUGE_DESC = "Número de requisições HTTP em andamento";
  public static final String METRIC_REQ_SIZE_NAME = "http_request_size_bytes";
  public static final String METRIC_RES_SIZE_NAME = "http_response_size_bytes";

}
