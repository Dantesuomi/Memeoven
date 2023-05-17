package services;

import com.memeoven.memeoven.entity.User;
import com.memeoven.memeoven.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public void createUser(User user) throws Exception {
        this.userRepository.save(user);
    }

    public User verifyUser(String username, String passwordHash) throws Exception {
        User user = this.userRepository.findByUsernameAndPassword(username, passwordHash);
        if (user == null) throw new Exception("Username or password is not correct");
        return user;
    }

}
