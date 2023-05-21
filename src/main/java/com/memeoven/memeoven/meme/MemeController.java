package com.memeoven.memeoven.meme;

import com.memeoven.memeoven.entity.User;
import com.memeoven.memeoven.storage.StorageService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MemeController {

    private MemeFileService memeFileService;
    @Autowired
    public MemeController(MemeFileService memeFileService) {
        this.memeFileService = memeFileService;
    }
    @GetMapping("/upload")
    public String displayUploadPage(@AuthenticationPrincipal User user,
                                    Model model
    ){
        return "upload";
    }

    //TODO Validate Title (not empty, not null)
    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   @AuthenticationPrincipal User user,
                                   @RequestParam ("title") String title,
                                  // @RequestParam ("category") Category category,
                                   RedirectAttributes redirectAttributes) {

        String memeId = memeFileService.save(file).toString();
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

}
