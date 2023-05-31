package com.memeoven.memeoven.meme;

import com.memeoven.memeoven.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemeLikeRepository extends JpaRepository<MemeLike, Long> {
    MemeLike findMemeLikeByUserAndMeme(User user, Meme meme);

    Integer countMemeLikesByMeme(Meme meme);
}