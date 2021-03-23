package com.TFG.tempo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TempoApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(TempoApplication.class, args);
  }

}
