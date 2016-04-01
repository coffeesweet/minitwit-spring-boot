package com.minitwit.common.annotation;


import java.lang.annotation.Annotation;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * RPNotEmpty annotation implement
 * 
 * @author chenchao
 * @date 2016年3月23日 上午11:26:17
 * @version 1.0
 * @throws
 * @see AbstractNamedValueMethodArgumentResolver
 * @see RequestParamMethodArgumentResolver
 */
public class RequestParamValidateResolver extends WebMvcConfigurerAdapter implements HandlerMethodArgumentResolver {
  

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    Annotation[] annotations = parameter.getParameterAnnotations();
    for (Annotation annotation : annotations) {
      if (RPNotEmpty.class.isInstance(annotation))
        return true;
    }
    return false;

  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    // Class<?> paramType = parameter.getParameterType();
    HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
    String arg = servletRequest.getParameter(parameter.getParameterName());
    // Object arg = resolveName(parameter.getParameterName(), parameter, webRequest);
    if (null == arg || "".equals(arg.trim())) {
      throw new MissingServletRequestParameterException(parameter.getParameterName(), parameter
          .getParameterType().getSimpleName());
    }

    return arg;

  }
  
  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(new RequestParamValidateResolver()); // add
  }




}
