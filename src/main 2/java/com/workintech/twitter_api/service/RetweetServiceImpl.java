package com.workintech.twitter_api.service;

import com.workintech.twitter_api.dto.request.RetweetRequestDto;
import com.workintech.twitter_api.dto.response.RetweetResponseDto;
import com.workintech.twitter_api.entity.Retweet;
import com.workintech.twitter_api.entity.Tweet;
import com.workintech.twitter_api.entity.User;
import com.workintech.twitter_api.exceptions.RetweetNotFoundException;
import com.workintech.twitter_api.exceptions.TwitterApiException;
import com.workintech.twitter_api.repository.RetweetRepository;
import com.workintech.twitter_api.util.RetweetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetweetServiceImpl implements RetweetService {


    private final RetweetRepository retweetRepository;
    private final TweetService      tweetService;
    private final RetweetMapper retweetMapper;

    @Override
    public RetweetResponseDto retweet(RetweetRequestDto dto, User currentUser) {
        Tweet tweet = tweetService.getEntityById(dto.tweetId());

        if (retweetRepository.existsByUserIdAndTweetId(currentUser.getId(), tweet.getId())) {
            throw new TwitterApiException(
                    "Bu tweeti zaten retweet ettiniz.", HttpStatus.CONFLICT);
        }

        Retweet retweet = new Retweet();
        retweet.setUser(currentUser);
        retweet.setTweet(tweet);

        return retweetMapper.toResponseDto(retweetRepository.save(retweet));
    }


    @Override
    public RetweetResponseDto delete(Long id, User currentUser) {
        Retweet retweet = retweetRepository.findById(id)
                .orElseThrow(() -> new RetweetNotFoundException(id));

        if (!retweet.getUser().getId().equals(currentUser.getId())) {
            throw new TwitterApiException(
                    "Bu retweeti sadece sahibi silebilir.", HttpStatus.FORBIDDEN);
        }

        retweetRepository.delete(retweet);
        return retweetMapper.toResponseDto(retweet);
    }

}
