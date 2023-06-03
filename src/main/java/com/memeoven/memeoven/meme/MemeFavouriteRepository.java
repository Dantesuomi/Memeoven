package com.memeoven.memeoven.meme;

import com.memeoven.memeoven.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemeFavouriteRepository extends JpaRepository<Favourite, Long> {

    Favourite findFavouriteByUserAndMeme(User user, Meme meme);

    List<Favourite> findFavouritesByUser(User user);
}
