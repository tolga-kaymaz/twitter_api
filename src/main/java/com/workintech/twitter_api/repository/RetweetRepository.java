package com.workintech.twitter_api.repository;

import com.workintech.twitter_api.entity.Like;
import com.workintech.twitter_api.entity.Retweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RetweetRepository extends JpaRepository<Retweet, Long> {

    List<Retweet> findByUserId(Long userId);


    boolean existsByUserIdAndTweetId(Long userId, Long tweetId);


}
