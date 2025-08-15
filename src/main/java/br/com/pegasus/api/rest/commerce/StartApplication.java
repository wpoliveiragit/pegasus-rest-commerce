package br.com.pegasus.api.rest.commerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "br.com.pegasus.api.rest.commerce", //
        "br.com.pegasus.gen.openapi"// precisa scannear o pacore gerado pelo plugin
})
public class StartApplication {
    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }
}
