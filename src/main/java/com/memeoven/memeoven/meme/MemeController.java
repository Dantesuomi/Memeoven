package com.memeoven.memeoven.meme;

import com.memeoven.memeoven.entity.User;
import com.memeoven.memeoven.repository.CommentRepository;
import com.memeoven.memeoven.storage.StorageService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class MemeController {

    private MemeFileService memeFileService;
    private MemeRepository memeRepository;
    private CommentRepository commentRepository;
    private MemeService memeService;
    @Autowired
    public MemeController(MemeFileService memeFileService, MemeRepository memeRepository, CommentRepository commentRepository, MemeService memeService) {
        this.memeFileService = memeFileService;
        this.memeRepository = memeRepository;
        this.commentRepository = commentRepository;
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


    @GetMapping("/meme-page/{memeId}")
    public String showMeme(@PathVariable("memeId") Long id, Model model, @AuthenticationPrincipal User user) {
        Optional<Meme> memeOptional = memeRepository.findById(id);
        if (memeOptional.isPresent()) {
            Meme memeData = memeOptional.get();
            String memeName = memeData.getTitle();
            String memeCategory = String.valueOf(memeData.getCategory());
            String userName = memeData.getUser().getUsername(); // Get the username directly

            model.addAttribute("memeName", memeName);
            model.addAttribute("memeCategory", memeCategory);
            model.addAttribute("user", userName);
            model.addAttribute("image", memeData.getNameOfMemePhoto());
            model.addAttribute("userId", memeData.getUser().getId());

            List<Comment> comments = commentRepository.findByMemeId(id);
            model.addAttribute("comment", comments);
        }
        return "meme-page";
    }

    @PostMapping("/meme-page/{memeId}/comment")
    public String sendComment(@PathVariable("memeId") Long id, @RequestParam("text") String text, Model model, @AuthenticationPrincipal User user) {
        try {
            Optional<Meme> memeOptional = memeRepository.findById(id);
            if (memeOptional.isPresent()) {
                Meme meme = memeOptional.get();
                Comment comment = new Comment();
                comment.setComment(text);
                comment.setUser(user);
                comment.setMeme(meme);
                this.commentRepository.save(comment);
            } else {
                return "redirect:/error";
            }

            return "redirect:/meme-page/{memeId}";
        } catch (Exception e) {
            return "redirect:login" + e.getMessage();
        }
    }

    @ModelAttribute("loggedIn")
    public boolean loggedIn(@AuthenticationPrincipal User user) {
        return user != null;
    }

    @GetMapping("/")
    public String displayMainPage(@AuthenticationPrincipal User user,
                                    Model model
    ){
        List<Meme> memes = memeService.getAllMemes();
        model.addAttribute("memes", memes);
        return "index";
    }

    @GetMapping("/category")
    public String showCategoryPage(){
        return "category";
    }

    @GetMapping("/search")
    public String showSearchPage(){
        return "search-result";
    }

}
