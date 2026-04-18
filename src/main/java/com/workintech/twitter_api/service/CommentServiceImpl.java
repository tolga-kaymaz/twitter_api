package com.workintech.twitter_api.service;

import com.workintech.twitter_api.dto.request.CommentPatchRequestDto;
import com.workintech.twitter_api.dto.request.CommentRequestDto;
import com.workintech.twitter_api.dto.response.CommentResponseDto;
import com.workintech.twitter_api.entity.Comment;
import com.workintech.twitter_api.entity.Tweet;
import com.workintech.twitter_api.entity.User;
import com.workintech.twitter_api.exceptions.CommentNotFoundException;
import com.workintech.twitter_api.exceptions.TwitterApiException;
import com.workintech.twitter_api.repository.CommentRepository;
import com.workintech.twitter_api.util.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    @Autowired
    private final CommentRepository commentRepository;

    @Autowired
    private  final TweetService      tweetService;

    @Autowired
    private final CommentMapper commentMapper;


    @Override
    public CommentResponseDto create(CommentRequestDto commentRequestDto, User currentUser) {
        Tweet tweet = tweetService.getEntityById(commentRequestDto.tweetId());

        Comment comment = new Comment();
        comment.setContent(commentRequestDto.content());
        comment.setUser(currentUser);
        comment.setTweet(tweet);

        return commentMapper.toResponseDto(commentRepository.save(comment));
    }

    @Override
    public List<CommentResponseDto> findByTweetId(Long tweetId) {
        tweetService.getEntityById(tweetId);
        return commentRepository.findByTweetId(tweetId)
                .stream().map(commentMapper::toResponseDto).toList();
    }

    @Override
    public CommentResponseDto findById(Long id) {
        Comment comment  = commentRepository.findById(id)
                .orElseThrow(() -> new TwitterApiException(
                        "Yorum bulunamadı: " + id,
                        HttpStatus.NOT_FOUND));
        return commentMapper.toResponseDto(comment);
    }




    @Override
    public CommentResponseDto update(Long id, CommentPatchRequestDto commentPatchRequestDto, User user) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new TwitterApiException(
                        "Yorum bulunamadı: " + id,
                        HttpStatus.NOT_FOUND));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new TwitterApiException(
                    "Sadece kendi yorumunu güncelleyebilirsin.",
                    HttpStatus.FORBIDDEN);
        }

        if (commentPatchRequestDto.content() != null && !commentPatchRequestDto.content().isBlank()) {
            comment.setContent(commentPatchRequestDto.content());
        }

        return commentMapper.toResponseDto(commentRepository.save(comment));
    }

    @Override
    public CommentResponseDto replace(Long id, CommentRequestDto commentRequestDto, User user) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new TwitterApiException(
                        "Yorum bulunamadı: " + id,
                        HttpStatus.NOT_FOUND));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new TwitterApiException(
                    "Sadece kendi yorumunu güncelleyebilirsin.",
                    HttpStatus.FORBIDDEN);
        }

        comment.setContent(commentRequestDto.content());

        return commentMapper.toResponseDto(commentRepository.save(comment));
    }

    @Override
    public void delete(Long id, User user) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));

        boolean isCommentOwner = comment.getUser().getId().equals(user.getId());
        boolean isTweetOwner   = comment.getTweet().getUser().getId().equals(user.getId());

        if (!isCommentOwner && !isTweetOwner) {
            throw new TwitterApiException(
                    "Bu yorumu sadece yorum sahibi veya tweet sahibi silebilir.",
                    HttpStatus.FORBIDDEN);
        }

        commentRepository.delete(comment);
    }
}
