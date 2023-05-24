package com.memeoven.memeoven.meme;

import com.memeoven.memeoven.entity.User;
import com.memeoven.memeoven.entity.UserDto;
import com.memeoven.memeoven.storage.StorageService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

}
