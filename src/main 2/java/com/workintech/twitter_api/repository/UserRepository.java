package com.workintech.twitter_api.repository;

import com.workintech.twitter_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    // AuthService register — kullanıcı adı çakışma kontrolü
    boolean existsByUsername(String username);

    // AuthService register — email çakışma kontrolü
    boolean existsByEmail(String email);
}
