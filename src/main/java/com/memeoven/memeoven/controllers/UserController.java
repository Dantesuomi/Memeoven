package com.memeoven.memeoven.controllers;

import com.memeoven.memeoven.entity.NewUser;
import com.memeoven.memeoven.entity.RegistrationStatus;
import com.memeoven.memeoven.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.memeoven.memeoven.services.UserService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("login")
    public String displayLoginPage(){
//            @RequestParam(name = "status", required = false) String status,
//            @RequestParam(name = "message", required = false) String message,
//            Model model
//    ){
//        model.addAttribute("status", status);
//        model.addAttribute("message", message);
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage(
            @RequestParam(name = "status", required = false) String status,
            Model model
    ){
        model.addAttribute("status", status);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(NewUser newUser){
        boolean isUsernameInUse = userService.isUsernameInUse(newUser.getUsername());
        boolean isEmailInUse = userService.isEmailInUse(newUser.getEmail());
        boolean isValidPassword = userService.isValidPassword(newUser.getPassword());
        if(isUsernameInUse){
            return "redirect:register?status=" + RegistrationStatus.USERNAME_IN_USE;
        } else if (isEmailInUse) {
            return "redirect:register?status=" + RegistrationStatus.EMAIL_IN_USE;
        }else if (!isValidPassword){
            return "redirect:register?status=" + RegistrationStatus.PASSWORD_DOESNT_MEET_REQUIREMENTS;
        }
        User user = new User();
        user.setUsername(newUser.getUsername());
        user.setEmail(newUser.getEmail());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newUser.getPassword());
        user.setPasswordHash(encodedPassword);
        userService.createUser(user);
        return "login";
    }

//    @GetMapping("/profile")
//    public String showProfilePage(){
//        return "profile";
//    }

//    @PostMapping("/login")
//    public String handleLogin(LoginRequest loginRequest){
//        try {
//            User loggedInUser = this.userService.verifyUser(loginRequest.email, loginRequest.passwordHash);
//            return "redirect:chat-room";
//        }catch (Exception exception){
//            return "redirect: login?status=LOGIN FAILEDmessage="+ exception.getMessage();
//        }
//    }
}
