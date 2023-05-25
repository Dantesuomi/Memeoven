package com.memeoven.memeoven.meme;

import com.memeoven.memeoven.entity.User;
import com.memeoven.memeoven.repository.UserRepository;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public void saveMeme(MemeDto memeDto, User user){
        Meme meme = new Meme();
        String memeFileName = memeFileService.save(memeDto.getFile());
        meme.setTitle(memeDto.getTitle());
        meme.setCategory(memeDto.getCategory());
        meme.setNameOfMemePhoto(memeFileName);
        meme.setUser(user);
        this.memeRepository.save(meme);
    }

    public List<Meme> getAllMemes(){
       List<Meme> memes = memeRepository.getMemesByIdIsNotNull();
       return memes;
    }


}
