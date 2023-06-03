package com.memeoven.memeoven.meme;

import com.memeoven.memeoven.user.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemeFavouriteRepository extends JpaRepository<Favourite, Long> {

    Favourite findFavouriteByUserAndMeme(User user, Meme meme);

    @Transactional
    void deleteFavouritesByMeme(Meme meme);

    List<Favourite> findFavouritesByUser(User user);
}
