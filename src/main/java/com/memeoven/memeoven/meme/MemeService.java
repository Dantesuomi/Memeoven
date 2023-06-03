package com.memeoven.memeoven.meme;

import com.memeoven.memeoven.comment.CommentRepository;
import com.memeoven.memeoven.exceptions.ForbiddenException;
import com.memeoven.memeoven.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MemeService {
    private MemeRepository memeRepository;
    private MemeLikeRepository memeLikeRepository;

    private CommentRepository commentRepository;
    private MemeFileService memeFileService;

    private MemeFavouriteRepository memeFavouriteRepository;

    @Autowired
    public MemeService(MemeRepository memeRepository, MemeFileService memeFileService, MemeLikeRepository memeLikeRepository, MemeFavouriteRepository memeFavouriteRepository, CommentRepository commentRepository){
        this.memeRepository = memeRepository;
        this.memeFileService = memeFileService;
        this.memeLikeRepository = memeLikeRepository;
        this.memeFavouriteRepository = memeFavouriteRepository;
        this.commentRepository = commentRepository;
    }


    public Long saveMeme(MemeDto memeDto, User user) {
        Meme meme = new Meme();
        String memeFileName = memeFileService.save(memeDto.getFile());
        meme.setTitle(memeDto.getTitle());
        meme.setCategory(memeDto.getCategory());
        meme.setNameOfMemePhoto(memeFileName);
        meme.setUser(user);
        Meme savedMeme = this.memeRepository.save(meme);
        return savedMeme.getId();
    }

    public Meme getMeme(Long id) {
        return memeRepository.getMemeById(id);
    }

    public List<Meme> getAllMemes() {
        List<Meme> memes = memeRepository.getMemesByIdIsNotNull();
        return memes;
    }

    public List<Meme> searchMemes(String query) {
        if (query.length() >= 3) {
            return memeRepository.findByTitleContainingIgnoreCase(query);
        }
        return new ArrayList<>();
    }

    public List<Meme> searchNewMemes() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -2);
        Date twoDaysAgo = calendar.getTime();

        return memeRepository.findNewestMemesWithinLastTwoDays(twoDaysAgo);

    }

    public void saveMemeLike(Long memeId, User user) {
        Meme meme = memeRepository.getMemeById(memeId);
        MemeLike savedMemeLike = memeLikeRepository.findMemeLikeByUserAndMeme(user, meme);
        if (savedMemeLike == null) {
            MemeLike memeLike = new MemeLike();
            memeLike.setUser(user);
            memeLike.setMeme(meme);
            memeLikeRepository.save(memeLike);
        }
    }

    public void saveToFavourite(Long memeId, User user) {
        Meme meme = memeRepository.getMemeById(memeId);
        Favourite savedFavourite = memeFavouriteRepository.findFavouriteByUserAndMeme(user, meme);
        if (savedFavourite == null) {
            Favourite favourite = new Favourite();
            favourite.setUser(user);
            favourite.setMeme(meme);
            memeFavouriteRepository.save(favourite);
        }
    }

    public boolean isMemeLikedByUser(Long memeId, User user) {
        Meme meme = memeRepository.getMemeById(memeId);
        MemeLike savedMemeLike = memeLikeRepository.findMemeLikeByUserAndMeme(user, meme);
        if (savedMemeLike == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isUserFavouriteMeme(Long memeId, User user) {
        Meme meme = memeRepository.getMemeById(memeId);
        Favourite savedFavourite = memeFavouriteRepository.findFavouriteByUserAndMeme(user, meme);
        if (savedFavourite == null) {
            return false;
        } else {
            return true;
        }
    }

    public List<Meme> getFavouriteMemes(User user){
        List<Favourite> favouriteMemes = memeFavouriteRepository.findFavouritesByUser(user);
        List<Meme> memes = new ArrayList<>();
        for (Favourite favourite : favouriteMemes) {
            memes.add(favourite.getMeme());
        }
        return memes;
    }

    public Integer getLikeCount(Meme meme){
        Integer likeCount = memeLikeRepository.countMemeLikesByMeme(meme);
        return likeCount;
    }

    public List<Meme> getMemesByCategory(Category category) {
        return memeRepository.findByCategory(category);
    }
    
    public List<Meme> getTopLikedMemes() {
        List<Long> topMemeIds = memeLikeRepository.getTopLikedMemeIds();
        List<Meme> topLikedMemes = memeRepository.getMemesById(topMemeIds);

        // Sort the memes based on the order of topMemeIds
        Map<Long, Meme> memeMap = topLikedMemes.stream()
                .collect(Collectors.toMap(Meme::getId, Function.identity()));
        List<Meme> sortedMemes = topMemeIds.stream()
                .map(memeMap::get)
                .collect(Collectors.toList());

        return sortedMemes;
    }

    public List<Meme> getMemesUploadedByUser(Long id){
        return memeRepository.getMemesByUserId(id);
    }

    public List<Meme> getUploadedMemes(User user){
        List<Meme> uploadedMemes = memeRepository.findMemesByUser(user);
        return uploadedMemes;
    }

    public void deleteMeme(Long memeId, User user){
        Meme memeToDelete = memeRepository.getMemeById(memeId);
        long memeOwnerId = memeToDelete.getUser().getId();
        long authorizedUserId = user.getId();
        if (memeOwnerId == authorizedUserId){
            memeLikeRepository.deleteMemeLikesByMeme(memeToDelete);
            memeFavouriteRepository.deleteFavouritesByMeme(memeToDelete);
            commentRepository.deleteCommentsByMeme(memeToDelete);
            memeRepository.delete(memeToDelete);
        }else {
            throw new ForbiddenException();
        }

    }


}
