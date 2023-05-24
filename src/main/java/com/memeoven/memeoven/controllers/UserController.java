package com.memeoven.memeoven.controllers;

import com.memeoven.memeoven.entity.User;
import com.memeoven.memeoven.entity.UserDto;
import com.memeoven.memeoven.entity.RegistrationStatus;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @GetMapping("/login")
    public String displayLoginPage(
            @RequestParam(name = "error", required = false) String error,
            Model model
   ){
        model.addAttribute("error", error);
        return "login";
    }

    @GetMapping("/register")
    public String displayRegisterPage(
            @RequestParam(name = "status", required = false)
            String status,
            Model model
    ) {
        model.addAttribute("status", status);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid UserDto userDto){
        boolean isUsernameInUse = userService.isUsernameInUse(userDto.getUsername());
        boolean isEmailInUse = userService.isEmailInUse(userDto.getEmail());
        boolean isValidPassword = userService.isValidPassword(userDto.getPassword());
        boolean isValidEmail = userService.isValidEmail(userDto.getEmail());
        if (!isValidEmail) {
            return "redirect:register?status=" + RegistrationStatus.INVALID_EMAIL;
        } else if (isUsernameInUse){
            return "redirect:register?status=" + RegistrationStatus.USERNAME_IN_USE;
        } else if (isEmailInUse) {
            return "redirect:register?status=" + RegistrationStatus.EMAIL_IN_USE;
        } else if (!isValidPassword) {
            return "redirect:register?status=" + RegistrationStatus.PASSWORD_DOESNT_MEET_REQUIREMENTS;
        }
        userService.createUser(userDto);
        return "login";
        //TODO SAVE MEME TO DATABASE
    }

    @GetMapping("/profile")
    public String displayProfilePage(){
        return "profile";
    }

    @GetMapping("/meme-page")
    public String displayMemePage(){
        return "meme-page";
    }


}
