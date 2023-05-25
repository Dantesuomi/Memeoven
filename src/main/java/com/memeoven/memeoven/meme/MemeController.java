package com.memeoven.memeoven.meme;

import com.memeoven.memeoven.entity.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemeController {

    private MemeService memeService;
    @Autowired
    public MemeController(MemeService memeService) {
        this.memeService = memeService;
    }
    @GetMapping("/upload")
    public String displayUploadPage(@AuthenticationPrincipal User user,
                                    Model model
    ){
        return "upload";
    }
    @PostMapping("/upload")
    public String handleMemeUpload(@AuthenticationPrincipal User user,
                                   @ModelAttribute("meme") @Valid MemeDto memeDto) {
        memeService.saveMeme(memeDto, user);

        return "redirect:/";
    }

    @GetMapping("/")
    public String displayMainPage(@AuthenticationPrincipal User user,
                                    Model model
    ){
        List<Meme> memes = memeService.getAllMemes();
        model.addAttribute("memes", memes);
        return "index";
    }




}
