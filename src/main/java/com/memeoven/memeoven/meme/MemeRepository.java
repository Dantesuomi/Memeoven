package com.memeoven.memeoven.meme;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface MemeRepository extends JpaRepository<Meme, Long> {

     Optional<Meme> findById(Long id);
     List<Meme> getMemesByIdIsNotNull();

}
