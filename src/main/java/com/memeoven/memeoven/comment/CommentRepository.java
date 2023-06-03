package com.memeoven.memeoven.comment;

import com.memeoven.memeoven.meme.Meme;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByMemeId(Long memeId);

    @Transactional
    void deleteCommentsByMeme(Meme meme);

    Integer countCommentsByMeme(Meme meme);
}
