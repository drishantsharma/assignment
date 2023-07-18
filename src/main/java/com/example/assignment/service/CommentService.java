package com.example.assignment.service;

import com.example.assignment.entity.CommentData;
import com.example.assignment.entity.PostData;
import com.example.assignment.feignClient.CommentClient;
import com.example.assignment.model.Comment;
import com.example.assignment.repository.CommentRepository;
import com.example.assignment.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentClient commentClient;

    @Retryable(maxAttempts = 2, backoff = @Backoff(delay = 1000))
    public CommentData addComment(Comment comment) {

        Optional<PostData> postData = postRepository.findByPostId(comment.getPostId());

        if (!postData.isPresent()) {
            return null;
        }

        Comment commentResponse = commentClient.getComment(comment);

        CommentData commentData = CommentData.builder()
                .commentId(commentResponse.getId())
                .postId(commentResponse.getPostId())
                .name(commentResponse.getName())
                .email(commentResponse.getEmail())
                .body(commentResponse.getBody())
                .build();

        return commentRepository.save(commentData);
    }

    @Recover
    public CommentData addCommentFallback(Comment comment) {
        System.out.println("Comment service unavailable, fallback method executed");
        return null;
    }
}
