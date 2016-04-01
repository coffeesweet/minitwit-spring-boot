package com.minitwit;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * bootstrap class
 * 
 * @author chenchao
 * @date 2016年3月23日 上午11:24:25
 * @version 1.0
 * @throws
 */
@SpringBootApplication
public class App {
  public static void main(String[] args) {
    ApplicationContext ctx = SpringApplication.run(App.class, args);

    System.out.println("Let's inspect the beans provided by Spring Boot:");

    String[] beanNames = ctx.getBeanDefinitionNames();
    Arrays.sort(beanNames);
    for (String beanName : beanNames) {
      System.out.println(beanName);
    }

  }
}
