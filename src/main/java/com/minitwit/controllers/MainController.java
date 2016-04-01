package com.minitwit.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.minitwit.common.annotation.RPNotEmpty;
import com.minitwit.common.exception.HaltException;
import com.minitwit.model.LoginResult;
import com.minitwit.model.Message;
import com.minitwit.model.User;
import com.minitwit.service.impl.MiniTwitService;

/**
 * 
 * @author chenchao
 * @date 2016年3月23日 上午11:31:29
 * @version 1.0
 * @throws
 */
@Controller
public class MainController extends BaseController {

  private static final String USER_SESSION_ID = "user";
  private static final String VIEW_TIMELINE_LIST = "timeline";
  private static final String VIEW_LOGIN = "login";
  private static final String VIEW_REGISTER = "register";

  @Resource
  private MiniTwitService miniTwitService;

  @RequestMapping("/")
  public String index(Map<String, Object> map) throws IOException {
    User user = getAuthenticatedUser();
    if (null == user) {
      return "redirect:/public";
    }
    map.put("pageTitle", "Timeline");
    map.put("user", user);
    List<Message> messages = miniTwitService.getUserFullTimelineMessages(user);
    map.put("messages", messages);
    return VIEW_TIMELINE_LIST;
  }

  @RequestMapping("/public")
  public String timelinelist(Map<String, Object> map) throws IOException {
    User user = getAuthenticatedUser();
    map.put("pageTitle", "Public Timeline");
    map.put("user", user);
    List<Message> messages = miniTwitService.getPublicTimelineMessages();
    map.put("messages", messages);
    return VIEW_TIMELINE_LIST;
  }

  @RequestMapping(value = "/logout", method = RequestMethod.GET)
  public String logout() {
    removeAuthenticatedUser();
    return "redirect:/public";
  }

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String login(@RequestParam(required = false) @RPNotEmpty String q, Map<String, Object> map) {
    if (null != q && !"".equals(q.trim())) {
      map.put("message", "You were successfully registered and can login now");
    }
    return VIEW_LOGIN;
  }

  /*
   * @RequestBody调用合适的MessageConvert来把非application/x-www-form-urlencoded请求中的内容转换为指定的对象它通常与@
   * ResponseBody合用，
   * 
   * @ResponseBody与.@RequestBody刚好相反，他把指定的对象转换为合适的内容（请求头为Accept:application/json 则返回json数据）并返回。
   * http://www.cnblogs.com/liukemng/p/3736948.html
   */
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public String login(User user, Map<String, Object> map) {
    LoginResult result = miniTwitService.checkUser(user);
    if (null != result.getUser()) {
      addAuthenticatedUser(result.getUser());
      return "redirect:/";
    } else {
      map.put("error", result.getError());
    }
    map.put("username", user.getUsername());
    return VIEW_LOGIN;
  }

  @RequestMapping(value = "/register", method = RequestMethod.GET)
  public String register(Map<String, Object> map) {
    return VIEW_REGISTER;
  }

  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public String register(User user, Map<String, Object> map) {
    String error = user.validate();
    if (StringUtils.isEmpty(error)) {
      User existingUser = miniTwitService.getUserbyUsername(user.getUsername());
      if (existingUser == null) {
        miniTwitService.registerUser(user);
        map.put("r", 1);
        return "redirect:login";
        // res.redirect("/login?r=1");
        // halt();
      } else {
        error = "The username is already taken";
      }
    }
    map.put("error", error);
    map.put("username", user.getUsername());
    map.put("email", user.getEmail());
    return VIEW_REGISTER;
  }

  @RequestMapping(value = "/message", method = RequestMethod.POST)
  public String message(Message message, Map<String, Object> map) {
    User user = getAuthenticatedUser();
    if (null == user) {
      return "redirect:/login";
    }
    message.setUserId(user.getId());
    message.setPubDate(new Date());
    miniTwitService.addMessage(message);
    return "redirect:/";
  }

  /*
   * 1、通过@PathVariabl注解获取路径中传递参数 http://my.oschina.net/u/1013711/blog/209181
   * 2、springmvc3中的addFlashAttribute方法,使用addFlashAttribute,参数不会出现在url地址栏中
   * http://blog.csdn.net/jackpk/article/details/19121777
   */
  @RequestMapping(value = "/t/{username}", method = RequestMethod.GET)
  public String t(@PathVariable String username, Map<String, Object> map,
      RedirectAttributes redirectAttributes) {
    User profileUser = miniTwitService.getUserbyUsername(username);
    if (null == profileUser) {
      // redirectAttributes.addFlashAttribute("error", "User not Found");
      throw new HaltException(404, "User not Found");
    }
    User authUser = getAuthenticatedUser();
    boolean followed = false;
    if (authUser != null) {
      followed = miniTwitService.isUserFollower(authUser, profileUser);
    }
    List<Message> messages = miniTwitService.getUserTimelineMessages(profileUser);

    map.put("pageTitle", username + "'s Timeline");
    map.put("user", authUser);
    map.put("profileUser", profileUser);
    map.put("followed", followed);
    map.put("messages", messages);
    return VIEW_TIMELINE_LIST;
  }

  @RequestMapping(value = "/t/{username}/follow", method = RequestMethod.GET)
  public String tFollow(@PathVariable String username, Map<String, Object> map,
      RedirectAttributes redirectAttributes) {

    User authUser = getAuthenticatedUser();
    User profileUser = miniTwitService.getUserbyUsername(username);
    if (authUser == null) {
      return "redirect:/login";
    } else if (profileUser == null) {
      throw new HaltException(404, "User not Found");
    }

    miniTwitService.followUser(authUser, profileUser);
    return "redirect:/t/" + username;
  }

  @RequestMapping(value = "/t/{username}/unfollow", method = RequestMethod.GET)
  public String tUnFollow(@PathVariable String username, Map<String, Object> map,
      RedirectAttributes redirectAttributes) {

    User authUser = getAuthenticatedUser();
    User profileUser = miniTwitService.getUserbyUsername(username);
    if (authUser == null) {
      return "redirect:/login";
    } else if (profileUser == null) {
      throw new HaltException(404, "User not Found");
    }

    miniTwitService.unfollowUser(authUser, profileUser);
    return "redirect:/t/" + username;
  }

  private void addAuthenticatedUser(User u) {
    request.getSession(true).setAttribute(USER_SESSION_ID, u);

  }

  private void removeAuthenticatedUser() {
    request.getSession().removeAttribute(USER_SESSION_ID);

  }

  private User getAuthenticatedUser() {
    return (User) request.getSession().getAttribute(USER_SESSION_ID);
  }


}
