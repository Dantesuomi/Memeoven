package com.memeoven.memeoven.meme;

import com.memeoven.memeoven.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class MemeService {
    private MemeRepository memeRepository;
    private MemeLikeRepository memeLikeRepository;
    private MemeFileService memeFileService;
    @Autowired
    public MemeService(MemeRepository memeRepository, MemeFileService memeFileService, MemeLikeRepository memeLikeRepository){
        this.memeRepository = memeRepository;
        this.memeFileService = memeFileService;
        this.memeLikeRepository = memeLikeRepository;
    }


    public Long saveMeme(MemeDto memeDto, User user){
        Meme meme = new Meme();
        String memeFileName = memeFileService.save(memeDto.getFile());
        meme.setTitle(memeDto.getTitle());
        meme.setCategory(memeDto.getCategory());
        meme.setNameOfMemePhoto(memeFileName);
        meme.setUser(user);
        Meme savedMeme = this.memeRepository.save(meme);
        return savedMeme.getId();
    }

    public Meme getMeme(Long id){
        return memeRepository.getMemeById(id);
    }

    public List<Meme> getAllMemes(){
       List<Meme> memes = memeRepository.getMemesByIdIsNotNull();
       return memes;
    }

    public List<Meme> searchMemes(String query) {
        if (query.length() >= 3) {
            return memeRepository.findByTitleContainingIgnoreCase(query);
        }
        return new ArrayList<>();
    }

    public List<Meme> searchNewMemes(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -2);
        Date twoDaysAgo = calendar.getTime();

        return memeRepository.findNewestMemesWithinLastTwoDays(twoDaysAgo);

    }

    public void saveMemeLike(Long memeId, User user){
        Meme meme = memeRepository.getMemeById(memeId);
        MemeLike savedMemeLike = memeLikeRepository.findMemeLikeByUserAndMeme(user, meme);
        if (savedMemeLike == null){
            MemeLike memeLike = new MemeLike();
            memeLike.setUser(user);
            memeLike.setMeme(meme);
            memeLikeRepository.save(memeLike);
        }
    }

    public boolean isMemeLikedByUser(Long memeId, User user){
        Meme meme = memeRepository.getMemeById(memeId);
        MemeLike savedMemeLike = memeLikeRepository.findMemeLikeByUserAndMeme(user, meme);
        if (savedMemeLike == null){
            return false;
        }else {
            return true;
        }
    }

    public Integer getLikeCount(Meme meme){
        Integer likeCount = memeLikeRepository.countMemeLikesByMeme(meme);
        return likeCount;
    }
}
