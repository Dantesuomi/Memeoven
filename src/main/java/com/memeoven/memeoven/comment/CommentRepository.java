package com.memeoven.memeoven.comment;

import com.memeoven.memeoven.meme.Meme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByMemeId(Long memeId);

    Integer countCommentsByMeme(Meme meme);
}
