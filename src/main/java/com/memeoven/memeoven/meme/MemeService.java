package com.memeoven.memeoven.meme;

import com.memeoven.memeoven.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemeService {
    private MemeRepository memeRepository;
    private MemeFileService memeFileService;
    @Autowired
    public MemeService(MemeRepository memeRepository, MemeFileService memeFileService){
        this.memeRepository = memeRepository;
        this.memeFileService = memeFileService;
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

}
