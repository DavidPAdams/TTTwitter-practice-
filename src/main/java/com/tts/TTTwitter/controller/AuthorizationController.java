package com.tts.TTTwitter.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.tts.TTTwitter.model.User;
import com.tts.TTTwitter.service.UserService;

@Controller
public class AuthorizationController {
  @Autowired
  private UserService userService;
  
  @GetMapping(value="/login")
  public String login() {
    return "login";
  }
  
  @GetMapping(value = "/signup")
  public String registration(Model model) {
    User user = new User();
    model.addAttribute("user", user);
    return "signup";
  }
  
  @PostMapping(value = "/signup")
  public String createNewUser(@Valid User user, BindingResult bindingResult, Model model) {
    User userExists = userService.findByUsernameContainsIgnoreCase(user.getUsername());
    if(userExists != null) {
      bindingResult.rejectValue("username", "error.user", "That username is already taken");
    }
    if(!bindingResult.hasErrors()) {
      userService.saveNewUser(user);
      model.addAttribute("Success", "Sign up was successful");
      model.addAttribute("user", new User());
    }
    return "signup";
  }
}
