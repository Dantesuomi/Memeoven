package com.memeoven.memeoven.controllers;

import com.memeoven.memeoven.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import services.UserService;

@Controller
public class UserController {

    private UserService userService;

    //TODO

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("login")
    public String displayLoginPage(
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "message", required = false) String message,
            Model model
    ){
        model.addAttribute("status", status);
        model.addAttribute("message", message);
        return "login";
    }


    @PostMapping("/login")
    public String handleLogin(LoginRequest loginRequest){
        try {
            User loggedInUser = this.userService.verifyUser(loginRequest.email, loginRequest.passwordHash);
            return "redirect:chat-room";
        }catch (Exception exception){
            return "redirect: login?status=LOGIN FAILEDmessage="+ exception.getMessage();
        }
    }
}
