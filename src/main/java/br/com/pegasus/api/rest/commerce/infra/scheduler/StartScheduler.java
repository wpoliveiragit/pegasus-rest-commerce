package br.com.pegasus.api.rest.commerce.infra.scheduler;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

//@Component
@EnableScheduling
public class StartScheduler {


  @Scheduled(fixedRate = 3000)
  public void schedyledMethod() {
    System.out.println("Scheduling");
  }

}
