package com.example.assignment.service;

import com.example.assignment.entity.DocumentData;
import com.example.assignment.feignClient.PostClient;
import com.example.assignment.model.Post;
import com.example.assignment.entity.PostData;
import com.example.assignment.repository.DocumentRepository;
import com.example.assignment.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostClient postClient;

    @Retryable(maxAttempts = 2, backoff = @Backoff(delay = 1000))
    public PostData addPost(Post post) {

        Optional<DocumentData> documentData = documentRepository.findByDocumentId(post.getDocumentId());

        if (!documentData.isPresent()) {
            return null;
        }

        Post postResponse = postClient.getPost(post);

        PostData postData = PostData.builder()
                .postId(postResponse.getId())
                .title(postResponse.getTitle())
                .body(postResponse.getBody())
                .userId(postResponse.getUserId())
                .documentId(post.getDocumentId())
                .build();

        return postRepository.save(postData);
    }

    @Recover
    public PostData addPostFallback(Post post) {
        System.out.println("Post service unavailable, fallback method executed");
        return null;
    }

    public Optional<PostData> viewPost(Long documentId) {

        Optional<DocumentData> fileData = documentRepository.findByDocumentId(documentId);

        if (!fileData.isPresent()) {
            return null;
        }

        Optional<PostData> postData = postRepository.findByDocumentId(documentId);

        return postData;
    }
}
