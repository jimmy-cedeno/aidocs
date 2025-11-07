package dev.jimmycedeno.aidoc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.shell.command.annotation.CommandScan;

@ImportRuntimeHints(HintsRegistrar.class)
@CommandScan
@SpringBootApplication
public class AidocApplication {

  public static void main(String[] args) {
    SpringApplication.run(AidocApplication.class, args);
  }

  @Bean
  CommandLineRunner commandLineRunner() {
    return args -> System.out.println("hello ");
  }
}
