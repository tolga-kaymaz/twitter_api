package com.workintech.twitter_api.service;

import com.workintech.twitter_api.dto.request.LikeRequestDto;
import com.workintech.twitter_api.dto.response.LikeResponseDto;
import com.workintech.twitter_api.entity.Like;
import com.workintech.twitter_api.entity.Tweet;
import com.workintech.twitter_api.entity.User;
import com.workintech.twitter_api.exceptions.LikeNotFoundException;
import com.workintech.twitter_api.exceptions.TwitterApiException;
import com.workintech.twitter_api.repository.LikeRepository;
import com.workintech.twitter_api.util.LikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    @Autowired
    private final LikeRepository likeRepository;

    @Autowired

    private final TweetService   tweetService;

    @Autowired
    private final LikeMapper likeMapper;

    @Override
    public LikeResponseDto like(LikeRequestDto dto, User currentUser) {
        Tweet tweet = tweetService.getEntityById(dto.tweetId());

        if (likeRepository.existsByUserIdAndTweetId(currentUser.getId(), tweet.getId())) {
            throw new TwitterApiException(
                    "Bu tweeti zaten beğendiniz.", HttpStatus.CONFLICT);
        }

        Like like = new Like();
        like.setUser(currentUser);
        like.setTweet(tweet);

        return likeMapper.toResponseDto(likeRepository.save(like));
    }

    @Override
    public LikeResponseDto dislike(LikeRequestDto dto, User currentUser) {
        Like like = likeRepository
                .findByUserIdAndTweetId(currentUser.getId(), dto.tweetId())
                .orElseThrow(() -> new LikeNotFoundException(
                        "Bu tweeti daha önce beğenmediniz."));

        likeRepository.delete(like);
        return likeMapper.toResponseDto(like);
    }

    @Override
    public List<LikeResponseDto> findByTweetId(Long tweetId) {
        tweetService.getEntityById(tweetId); // tweet var mı kontrol et
        return likeRepository.findByTweetId(tweetId)
                .stream()
                .map(likeMapper::toResponseDto)
                .toList();
    }

}
