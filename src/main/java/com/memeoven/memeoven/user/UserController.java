package com.memeoven.memeoven.user;

import com.memeoven.memeoven.comment.CommentService;
import com.memeoven.memeoven.meme.Meme;
import com.memeoven.memeoven.meme.MemeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Controller
public class UserController {

    private UserService userService;
    private MemeService memeService;
    private CommentService commentService;

    @Autowired
    public UserController(UserService userService, MemeService memeService, CommentService commentService){
        this.userService = userService;
        this.memeService = memeService;
        this.commentService = commentService;
    }

    @GetMapping("/login")
    public String displayLoginPage(
            @RequestParam(name = "error", required = false) String error,
            @RequestParam(name = "status", required = false) String status,
            Model model

    ){
        model.addAttribute("error", error);
        model.addAttribute("status", status);
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
        return "redirect:login?status=" + RegistrationStatus.REGISTRATION_SUCCESS;

    }

    @GetMapping("/profile")
    public String displayProfilePage(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("user", user);

        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@AuthenticationPrincipal User user, @Valid ProfileDto profileDto){
        userService.updateUser(user, profileDto);
        return "redirect:profile";
    }

    @PostMapping("/update-avatar")
    public String updateAvatar(@AuthenticationPrincipal User user, @Valid AvatarDto avatarDto){
        userService.updateAvatar(user, avatarDto.getFile());
        return "redirect:profile";
    }

    @GetMapping("/meme-page")
    public String displayMemePage(){
        return "meme-page";
    }

    @ModelAttribute("loggedIn")
    public boolean loggedIn(@AuthenticationPrincipal User user) {
        return user != null;
    }

    @ModelAttribute("genders")
    public Gender[] getGender() {
        return Gender.values();
    }

    @GetMapping("/member/{userId}")
    public String showMemberPage(@PathVariable("userId") Long id, Model model, @AuthenticationPrincipal User user) {
        User foundUser = userService.getUser(id);
        List<Meme> userMemes = memeService.getMemesUploadedByUser(id);

        if (id == user.getId()){
            return "redirect:/profile";
        }
        model.addAttribute("user", foundUser);
        model.addAttribute("memes", userMemes);
        model.addAttribute("memeService", memeService);
        model.addAttribute("commentService", commentService);
        model.addAttribute("loggedInUser", user);

        return "member";
    }

}
