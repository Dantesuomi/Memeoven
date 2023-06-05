package com.memeoven.memeoven.comment;

import com.memeoven.memeoven.exceptions.ForbiddenException;
import com.memeoven.memeoven.meme.Meme;
import com.memeoven.memeoven.meme.MemeRepository;
import com.memeoven.memeoven.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class CommentService {
    private CommentRepository commentRepository;
    private MemeRepository memeRepository;
    @Autowired
    public CommentService(CommentRepository commentRepository, MemeRepository memeRepository){
        this.commentRepository = commentRepository;
        this.memeRepository = memeRepository;
    }
    public  void saveComment(String commentText, Long memeId, User user){
        Meme meme = memeRepository.getMemeById(memeId);
        Comment comment = new Comment();
        comment.setComment(commentText);
        comment.setMeme(meme);
        comment.setUser(user);
        this.commentRepository.save(comment);
    }

    public List<Comment> getAllComments(Long memeId){
        return commentRepository.findByMemeId(memeId);
    }


    public Integer getCommentCountByMemeId(Meme meme) {
        Integer commentCount = commentRepository.countCommentsByMeme(meme);
        return commentCount;
    }


    public void deleteComment(Long id, User user) {
        Comment commentToDelete = commentRepository.getCommentById(id);
        Long commentUserId = commentToDelete.getUser().getId();
        Long userId = user.getId();
        if (Objects.equals(commentUserId, userId)){
            commentRepository.deleteById(id);
        }else {
            throw new ForbiddenException();
        }
    }
}
