package br.com.pegasus.api.rest.commerce.infra.util;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public final class DateTimeUtil {

 public static OffsetDateTime getOffsetDateTimeNow(){
   return OffsetDateTime.now(ZoneOffset.UTC);
 }

}