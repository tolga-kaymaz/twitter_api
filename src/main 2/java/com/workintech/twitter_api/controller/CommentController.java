package com.workintech.twitter_api.controller;

import com.workintech.twitter_api.dto.request.CommentPatchRequestDto;
import com.workintech.twitter_api.dto.request.CommentRequestDto;
import com.workintech.twitter_api.dto.response.CommentResponseDto;
import com.workintech.twitter_api.entity.User;
import com.workintech.twitter_api.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private CommentService commentService;

    @PostMapping
    public CommentResponseDto create(@RequestBody CommentRequestDto commentRequestDto,
                                     @AuthenticationPrincipal User user) {
        return commentService.create(commentRequestDto, user);
    }

    @GetMapping("/findById")
    public CommentResponseDto findById(@RequestParam Long id) {
        return commentService.findById(id);
    }

    @GetMapping("/findByTweetId")
    public List<CommentResponseDto> findByTweetId(@RequestParam Long tweetId) {
        return commentService.findByTweetId(tweetId);
    }

    @PutMapping("/{id}")
    public CommentResponseDto replace(@PathVariable Long id,
                                      @RequestBody CommentRequestDto commentRequestDto,
                                      @AuthenticationPrincipal User user) {
        return commentService.replace(id, commentRequestDto, user);
    }

    @PatchMapping("/{id}")
    public CommentResponseDto update(@PathVariable Long id,
                                     @RequestBody CommentPatchRequestDto commentPatchRequestDto,
                                     @AuthenticationPrincipal User user) {
        return commentService.update(id, commentPatchRequestDto, user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id,
                       @AuthenticationPrincipal User user) {
        commentService.delete(id, user);
    }
}
