package br.com.pegasus.api.rest.commerce;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {//
    "br.com.pegasus.api.rest.commerce", //
    "br.com.pegasus.gen.openapi"// precisa scannear o pacore gerado pelo plugin
})
public class StartApplication implements ApplicationRunner {

  public static final long START_TIME = System.currentTimeMillis();

  public static void main(String[] args) {
    SpringApplication.run(StartApplication.class, args);
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    double seconds = (System.currentTimeMillis() - StartApplication.START_TIME) / 1000.0;
    System.out.printf("Started %s in %.3f seconds (process running for %.3f)%n",
        StartApplication.class.getSimpleName(), seconds, seconds);
  }
}
