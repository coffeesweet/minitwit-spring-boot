package com.minitwit.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.core.annotation.Order;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.minitwit.common.annotation.RequestParamValidateResolver;

/**
 * https://github.com/spring-projects/spring-boot/blob/master/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/mobile/DeviceResolverAutoConfiguration.java
 * spring boot 1.4.0.M1
 * http://docs.spring.io/spring-boot/docs/1.4.0.M1/reference/htmlsingle/#boot-features-spring-mvc
 * If you want to take complete control of Spring MVC, you can add your own @Configuration annotated with @EnableWebMvc. If you want to keep Spring Boot MVC features, and you just want to add additional MVC configuration (interceptors, formatters, view controllers etc.) you can add your own @Bean of type WebMvcConfigurerAdapter, but without @EnableWebMvc
 * @author chenchao
 * @date 2016年3月23日 下午3:55:43
 * @version 1.0
 * @throws
 * @see
 */

/*
public class ResolverConfig {

  @Bean
  public RequestParamValidateResolver requestParamValidateResolver() {
    return new RequestParamValidateResolver();
  }

}
*/


@Configuration
//@ConditionalOnClass({ RequestParamValidateResolver.class})
//@AutoConfigureAfter(WebMvcAutoConfiguration.class)
//@ConditionalOnWebApplication
public class ResolverConfig{

  @Bean
  public RequestParamValidateResolver requestParamValidateResolver() {
    return new RequestParamValidateResolver();
  }
  
  @Configuration
  public static class RequestParamValidateResolverMvcConfig extends WebMvcConfigurerAdapter {
    private RequestParamValidateResolver requestParamValidateResolver;
    
    //default constructor
    public RequestParamValidateResolverMvcConfig(){
      this.requestParamValidateResolver = new RequestParamValidateResolver();
    }
    @Autowired
    protected RequestParamValidateResolverMvcConfig(RequestParamValidateResolver requestParamValidateResolver){
      this.requestParamValidateResolver = requestParamValidateResolver;
    }
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
      argumentResolvers.add(this.requestParamValidateResolver); // add
//      argumentResolvers.add(new RequestParamValidateResolver()); // add
    }
  }

}

