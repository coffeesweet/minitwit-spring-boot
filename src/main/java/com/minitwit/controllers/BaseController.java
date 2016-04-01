package com.minitwit.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * 
 * @author chenchao
 * @date 2016年3月23日 上午11:31:17
 * @version 1.0
 * @throws
 */
@Controller
public class BaseController {

  protected HttpServletRequest request;
  protected HttpServletResponse response;
  protected HttpSession session;

  @ModelAttribute
  public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
    this.request = request;
    this.response = response;
    this.session = request.getSession();
  }

}
