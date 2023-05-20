package com.memeoven.memeoven.repository;

import com.memeoven.memeoven.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndPasswordHash(String username, String passwordHash);
    User findByUsername(String username);
    User findByEmail(String email);
}
