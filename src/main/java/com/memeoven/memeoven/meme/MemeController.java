package com.memeoven.memeoven.meme;

import com.memeoven.memeoven.comment.Comment;
import com.memeoven.memeoven.comment.CommentService;
import com.memeoven.memeoven.exceptions.ResourceNotFoundException;
import com.memeoven.memeoven.user.User;
import com.memeoven.memeoven.comment.CommentRepository;
import jakarta.servlet.http.HttpServletRequest;
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

    private MemeDto memeDto;

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
                                   @Valid MemeDto memeDto) {
        Long memeId = memeService.saveMeme(memeDto, user);
        return "redirect:/meme-page/" + memeId;
    }

    @GetMapping("/meme-page/{memeId}")
    public String showMeme(@PathVariable("memeId") Long id, Model model, @AuthenticationPrincipal User user) {
        Meme meme = memeService.getMeme(id);
        if(meme == null){
            throw new ResourceNotFoundException();
        }
        Integer likeCount = memeService.getLikeCount(meme);
        Integer commentCount = commentService.getCommentCountByMemeId(meme);
        String memeCategory = String.valueOf(meme.getCategory());
        model.addAttribute("memeName", meme.getTitle());
        model.addAttribute("memeCategory", memeCategory);
        model.addAttribute("user", meme.getUser().getUsername());
        model.addAttribute("image", meme.getNameOfMemePhoto());
        model.addAttribute("userId", meme.getUser().getId());
        model.addAttribute("likeCount", likeCount);
        model.addAttribute("commentCount", commentCount);
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


    //TODO
    @PostMapping("/fav-memes/{memeId}")
    public Long savedToFavorite(@PathVariable("memeId") Long id, @AuthenticationPrincipal User user) {
        Meme meme = memeService.getMeme(id);
        meme.setSaved(true);
        return memeService.saveMeme(memeDto, user);
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
    public String showSearchResult(@RequestParam("query") String query, @AuthenticationPrincipal User user,
                                   Model model) {
        List<Meme> memes = memeService.searchMemes(query);
        model.addAttribute("memes", memes);

        return "search-result";
    }

    @PostMapping("/like/{memeId}")
    public String saveMemeLike(@AuthenticationPrincipal User user, @PathVariable("memeId") Long memeId, HttpServletRequest request){
        String referrer = request.getHeader("referer");
        memeService.saveMemeLike(memeId, user);
        return "redirect:" + referrer;
    }

    @GetMapping("/like/{memeId}/userLiked")
    @ResponseBody
    public boolean hasUserLiked(@AuthenticationPrincipal User user, @PathVariable("memeId") Long memeId){
        boolean hasUserLiked = memeService.isMemeLikedByUser(memeId, user);
        return hasUserLiked;
    }

    @GetMapping("/favourites")
    public String showFavPage() {
        return "fav-memes";
    }

    @GetMapping("/my-uploads")
    public String showMyUploadsPage() {
        return "my-uploads";
    }

    @GetMapping("/top-rate")
    public String showTopOfTheTopPage() {
        return "top-rate";
    }

    @GetMapping("/new")
    public String showRecentUploadsPage() {
        return "new";
    }

}
