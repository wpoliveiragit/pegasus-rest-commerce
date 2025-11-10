package br.com.pegasus.api.rest.commerce.app.scheduler;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class StartScheduler {

  private static int i = 0;

  @Scheduled(fixedRate = 1000)
  public void schedyledMethod() {
//    System.out.println("Scheduled " + i++);
  }

}
