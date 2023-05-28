package com.memeoven.memeoven.user;

import com.memeoven.memeoven.meme.Meme;
import com.memeoven.memeoven.meme.MemeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.regex.Pattern;

@Service
public class UserService {
    public static final String DEFAULT_AVATAR = "default.png";
    public static final String ROLE_USER = "ROLE_USER";

    private UserRepository userRepository;

    private AvatarFileService avatarFileService;

    @Autowired
    public UserService(UserRepository userRepository, AvatarFileService avatarFileService){
        this.userRepository = userRepository;
        this.avatarFileService = avatarFileService;
    }

    public void createUser(UserDto userDto){
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setNameOfProfilePhoto(DEFAULT_AVATAR);
        user.setGender(Gender.NOT_SPECIFIED);
        //user.setDateOfBirth(new Date(0));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setRole(ROLE_USER);
        user.setPassword(encodedPassword);
        this.userRepository.save(user);
    }

    public void updateUser(User user, ProfileDto profileDto){
        user.setGender(profileDto.getGender());
        user.setDateOfBirth(profileDto.getDate());
        user.setAbout(profileDto.getDescription());
        userRepository.save(user);
    }

    public void updateAvatar(User user, MultipartFile file){
        String avatarFileName = avatarFileService.save(file);
        user.setNameOfProfilePhoto(avatarFileName);
        this.userRepository.save(user);
    }

    public boolean isUsernameInUse(String username) {
        User userByUsername = userRepository.findByUsername(username);
        if(userByUsername !=null)
            return true;
        return false;
    }

    public boolean isEmailInUse(String email) {
        User userByEmail = userRepository.findByEmail(email);
        if(userByEmail != null)
            return true;
        return false;
    }

    public boolean isValidPassword(String password) {
        // must include number, upper and lower case character and min length of 8
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
        Pattern regex = Pattern.compile(pattern);
        return regex.matcher(password).matches();
    }

    public boolean isValidEmail(String email){
        String pattern = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
        Pattern regex = Pattern.compile(pattern);
        return regex.matcher(email).matches();
    }

}
