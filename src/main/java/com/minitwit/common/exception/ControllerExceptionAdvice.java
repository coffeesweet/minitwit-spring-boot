package com.minitwit.common.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author chenchao
 * @date 2016年3月23日 下午8:10:47
 * @version 1.0
 * @throws
 * @see http://www.jianshu.com/p/752b44fc70e7
 * @see http://docs.spring.io/spring-boot/docs/1.4.0.M1/reference/htmlsingle/#boot-features-spring-mvc
 *      You can also define a @ControllerAdvice to customize the JSON document to return for a
 *      particular controller and/or exception type.
 */

@ControllerAdvice(basePackages = {"com.minitwit.controllers"})
public class ControllerExceptionAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler(HaltException.class)
  void handleControllerException(HttpServletRequest request, HttpServletResponse response,
      HaltException haltException) throws IOException {
    response.sendError(haltException.getStatusCode(), haltException.getBody());
  }
}
