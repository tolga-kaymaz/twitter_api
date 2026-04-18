package com.workintech.twitter_api.service;
import com.workintech.twitter_api.exceptions.TweetNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import com.workintech.twitter_api.dto.request.TweetPatchRequestDto;
import com.workintech.twitter_api.dto.request.TweetRequestDto;
import com.workintech.twitter_api.dto.response.TweetResponseDto;
import com.workintech.twitter_api.entity.Tweet;
import com.workintech.twitter_api.entity.User;
import com.workintech.twitter_api.exceptions.TwitterApiException;
import com.workintech.twitter_api.repository.TweetRepository;
import com.workintech.twitter_api.util.TweetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService{

    @Autowired
    private final TweetRepository tweetRepository;

    @Autowired
    private final TweetMapper tweetMapper;

    @Override
    public List<TweetResponseDto> getAll() {
        return tweetRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream()
                .map(tweetMapper::toResponseDto)
                .toList();
    }


    @Override
    public TweetResponseDto create(TweetRequestDto tweetRequestDto, User user) {
        Tweet tweet = new Tweet();
        tweet.setContent(tweetRequestDto.content());
        tweet.setUser(user);
        return tweetMapper.toResponseDto(tweetRepository.save(tweet));
    }

    @Override
    public List<TweetResponseDto> findByUserId(Long userId) {
        return tweetRepository.findAllByUserId(userId)
                .stream()
                .map(tweetMapper::toResponseDto)
                .toList();
    }

    @Override
    public TweetResponseDto findById(Long id) {
        Tweet tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new TweetNotFoundException(
                        "Tweet bulunamadı: " + id));
        return tweetMapper.toResponseDto(tweet);
    }

    @Override
    public TweetResponseDto update(Long id, TweetPatchRequestDto tweetPatchRequestDto, User user) {
        Tweet tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new TweetNotFoundException(
                        "Tweet bulunamadı: " + id));

        if (!tweet.getUser().getId().equals(user.getId())) {
            throw new TwitterApiException(
                    "Sadece kendi tweetini güncelleyebilirsin.",
                    HttpStatus.FORBIDDEN);
        }

        if (tweetPatchRequestDto.content() != null && !tweetPatchRequestDto.content().isBlank()) {
            tweet.setContent(tweetPatchRequestDto.content());
        }

        return tweetMapper.toResponseDto(tweetRepository.save(tweet));
    }

    @Override
    public TweetResponseDto replace(Long id, TweetRequestDto tweetRequestDto, User user) {
        Tweet tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new TweetNotFoundException(
                        "Tweet bulunamadı: " + id));

        if (!tweet.getUser().getId().equals(user.getId())) {
            throw new TwitterApiException(
                    "Sadece kendi tweetini güncelleyebilirsin.",
                    HttpStatus.FORBIDDEN);
        }

        tweet.setContent(tweetRequestDto.content());

        return tweetMapper.toResponseDto(tweetRepository.save(tweet));
    }

    @Override
    public void delete(Long id, User user) {
        Tweet tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new TweetNotFoundException(id));

        if (!tweet.getUser().getId().equals(user.getId())) {
            throw new TwitterApiException(
                    "Sadece kendi tweetini silebilirsin.",
                    HttpStatus.FORBIDDEN);
        }

        tweetRepository.delete(tweet);
    }

    @Override
    public Tweet getEntityById(Long id) {
        return tweetRepository.findById(id)
                .orElseThrow(() -> new TweetNotFoundException(
                        "Tweet bulunamadı: " + id));
    }
}
