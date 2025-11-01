package br.com.pegasus.api.rest.commerce.infra.util.mapper;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Component
public final class CommonMapper {

  public OffsetDateTime toOffsetDateTime(LocalDateTime date) {
    if (date == null) return null;
    return date.atZone(ZoneId.systemDefault()).toOffsetDateTime();
  }
}
