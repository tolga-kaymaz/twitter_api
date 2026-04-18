package com.workintech.twitter_api.controller;

import com.workintech.twitter_api.dto.request.TweetPatchRequestDto;
import com.workintech.twitter_api.dto.request.TweetRequestDto;
import com.workintech.twitter_api.dto.response.TweetResponseDto;
import com.workintech.twitter_api.entity.User;
import com.workintech.twitter_api.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/tweet")
public class TweetController {

    @Autowired
    private final TweetService tweetService;

    @GetMapping
    public List<TweetResponseDto> getAll() {
        return tweetService.getAll();
    }

    /****/
    @PostMapping
    public TweetResponseDto create(@RequestBody TweetRequestDto tweetRequestDto,
                                   @AuthenticationPrincipal User user) {
        return tweetService.create(tweetRequestDto, user);
    }

    /***/
    @GetMapping("/findById")
    public TweetResponseDto findById(@RequestParam Long id) {
        return tweetService.findById(id);
    }


   /***/
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
