package com.memeoven.memeoven.meme;

import com.memeoven.memeoven.comment.Comment;
import com.memeoven.memeoven.comment.CommentService;
import com.memeoven.memeoven.exceptions.ResourceNotFoundException;
import com.memeoven.memeoven.user.User;
import com.memeoven.memeoven.comment.CommentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemeController {

    private MemeService memeService;
    private CommentService commentService;

    @Autowired
    public MemeController(MemeService memeService, CommentService commentService) {
        this.memeService = memeService;
        this.commentService = commentService;
    }

    @GetMapping("/upload")
    public String displayUploadPage(@AuthenticationPrincipal User user,
                                    Model model
    ) {
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
        Meme meme = memeService.getMeme(id);
        if(meme == null){
            throw new ResourceNotFoundException();
        }
        String memeCategory = String.valueOf(meme.getCategory());
        model.addAttribute("memeName", meme.getTitle());
        model.addAttribute("memeCategory", memeCategory);
        model.addAttribute("user", meme.getUser().getUsername());
        model.addAttribute("image", meme.getNameOfMemePhoto());
        model.addAttribute("userId", meme.getUser().getId());
        List<Comment> comments = commentService.getAllComments(id);
        model.addAttribute("comment", comments);
        return "meme-page";
    }

    @PostMapping("/meme-page/{memeId}/comment")
    public String sendComment(@PathVariable("memeId") Long id, @RequestParam("text") String text, @AuthenticationPrincipal User user) {
        try {
            commentService.saveComment(text, id, user);
            return "redirect:/meme-page/{memeId}";
        } catch (Exception e) {
            return "redirect:/error";
        }
    }

    @ModelAttribute("loggedIn")
    public boolean loggedIn(@AuthenticationPrincipal User user) {
        return user != null;
    }

    @GetMapping("/")
    public String displayMainPage(@AuthenticationPrincipal User user,
                                  Model model
    ) {
        List<Meme> memes = memeService.getAllMemes();
        model.addAttribute("memes", memes);
        return "index";
    }

    @GetMapping("/category")
    public String showCategoryPage() {
        return "category";
    }

    @GetMapping("/search")
    public String showSearchPage() {
        return "search-result";
    }
}
