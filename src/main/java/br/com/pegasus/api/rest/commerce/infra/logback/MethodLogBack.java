package br.com.pegasus.api.rest.commerce.infra.logback;

import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MethodLogBack {

  public static final Logger TRACE_LOG = LoggerFactory.getLogger(ConstUtil.LOGBACK_TRACE_LOG);

}
