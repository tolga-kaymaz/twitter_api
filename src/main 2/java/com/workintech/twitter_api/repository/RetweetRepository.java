package com.workintech.twitter_api.repository;

import com.workintech.twitter_api.entity.Like;
import com.workintech.twitter_api.entity.Retweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RetweetRepository extends JpaRepository<Retweet, Long> {

    Optional<Retweet> findByUserIdAndTweetId(Long userId, Long tweetId);

    // RetweetService.retweet → aynı tweeti 2 kez retweet etmeyi önler
    boolean existsByUserIdAndTweetId(Long userId, Long tweetId);
}
