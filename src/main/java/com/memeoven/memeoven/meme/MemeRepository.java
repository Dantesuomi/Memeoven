package com.memeoven.memeoven.meme;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface MemeRepository extends JpaRepository<Meme, Long> {

    Meme getMemeById(Long id);

    List<Meme> getMemesByIdIsNotNull();

    List<Meme> findByTitleContainingIgnoreCase(String query);

    @Query("SELECT m FROM Meme m WHERE m.createdAt >= :twoDaysAgo ORDER BY m.id DESC")
    List<Meme> findNewestMemesWithinLastTwoDays(Date twoDaysAgo);

    @Query("SELECT m FROM Meme m WHERE m.id IN :ids")
    List<Meme> getMemesById(@Param("ids") List<Long> topMemesId);

    List<Meme> getMemesByUserId(Long id);

}
