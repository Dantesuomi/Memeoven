package com.memeoven.memeoven.meme;

import com.memeoven.memeoven.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class MemeService {
    private MemeRepository memeRepository;
    @Autowired
    public MemeService(MemeRepository memeRepository){
        this.memeRepository = memeRepository;
    }

    public void uploadMeme(MultipartFile file){

    }

    public List<Meme> getAllMemes(){
        List<Meme> memes = memeRepository.getMemesByIdIsNotNull();
        return memes;
    }



}
