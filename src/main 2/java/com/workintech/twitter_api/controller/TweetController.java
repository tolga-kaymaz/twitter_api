package com.workintech.twitter_api.controller;

import com.workintech.twitter_api.dto.request.TweetPatchRequestDto;
import com.workintech.twitter_api.dto.request.TweetRequestDto;
import com.workintech.twitter_api.dto.response.TweetResponseDto;
import com.workintech.twitter_api.entity.User;
import com.workintech.twitter_api.service.TweetService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweet")
public class TweetController {
    private TweetService tweetService;

    @PostMapping
    public TweetResponseDto create(@RequestBody TweetRequestDto tweetRequestDto,
                                   @AuthenticationPrincipal User user) {
        return tweetService.create(tweetRequestDto, user);
    }

    // Bir tweet için tüm bilgilerini getirmelidir.
    @GetMapping("/findById")
    public TweetResponseDto findById(@RequestParam Long id) {
        return tweetService.findById(id);
    }


   //  Bir kullanıcının tüm tweetlerini getirmelidir.
    @GetMapping("/findByUserId")
    public List<TweetResponseDto> findByUserId(@RequestParam Long userId) {
        return tweetService.findByUserId(userId);
    }




    @PutMapping("/{id}")
    public TweetResponseDto replace(@PathVariable Long id, @RequestBody TweetRequestDto tweetRequestDto,
                                    @AuthenticationPrincipal User user) {
        return tweetService.replace(id, tweetRequestDto, user);
    }

    @PatchMapping("/{id}")
    public TweetResponseDto update(@PathVariable Long id, @RequestBody TweetPatchRequestDto tweetPatchRequestDto,
                                   @AuthenticationPrincipal User user) {
        return tweetService.update(id, tweetPatchRequestDto, user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
        tweetService.delete(id, user);
    }
}
