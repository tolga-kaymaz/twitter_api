package com.workintech.twitter_api.repository;

import com.workintech.twitter_api.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserIdAndTweetId(Long userId, Long tweetId);

    // LikeService.like → aynı tweeti 2 kez beğenmeyi önler
    boolean existsByUserIdAndTweetId(Long userId, Long tweetId);


    List<Like> findByTweetId(Long tweetId);
}
