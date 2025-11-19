package br.com.pegasus.api.rest.commerce.infra.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component
@EnableScheduling
public class StartScheduler {

  private static final Logger logAudit = LoggerFactory.getLogger("AUDIT_INFO_LOG");
  private static final Logger logAudit2 = LoggerFactory.getLogger("AUDIT_WARN_LOG");
  private static final Logger logAudit3 = LoggerFactory.getLogger("AUDIT_TEST_LOG");

  @Scheduled(fixedRate = 3000)
  public void schedyledMethod() {
    System.out.println("Scheduling");
  }

}
