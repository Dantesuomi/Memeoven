package com.memeoven.memeoven.services;

import com.memeoven.memeoven.entity.Gender;
import com.memeoven.memeoven.entity.NewUser;
import com.memeoven.memeoven.entity.User;
import com.memeoven.memeoven.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.regex.Pattern;

@Service
public class UserService {
    public static final String DEFAULT_AVATAR = "default.jpg";

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public void createUser(User user){
        user.setNameOfProfilePhoto(DEFAULT_AVATAR);
        user.setGender(Gender.NOT_SPECIFIED);
        user.setDateOfBirth(new Date(0));
        this.userRepository.save(user);
    }

    public User verifyUser(String username, String passwordHash) throws Exception {
        User user = this.userRepository.findByUsernameAndPasswordHash(username, passwordHash);
        if (user == null) throw new Exception("Username or password is not correct");
        return user;
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

}
