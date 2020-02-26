package com.lace;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Slf4j
@SpringBootApplication
public class LaceSpringBootApp {

  public static void main(String... args) {
    log.info("Bootstrapping application...");
    SpringApplication.run(LaceSpringBootApp.class, args);
  }

  @Bean 
  public MethodValidationPostProcessor methodValidationPostProcessor() {
    return new MethodValidationPostProcessor();
  }
}
