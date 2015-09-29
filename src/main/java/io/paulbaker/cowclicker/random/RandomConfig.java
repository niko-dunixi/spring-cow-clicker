package io.paulbaker.cowclicker.random;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

/**
 * Created by paul on 9/28/15.
 */
@Configuration
public class RandomConfig {

  @Bean
  public Random random() {
    return new Random();
  }

}
