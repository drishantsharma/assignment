package com.example.assignment.controller;

import com.example.assignment.entity.PostData;
import com.example.assignment.model.Post;
import com.example.assignment.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/post")
@Slf4j
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/add")
    public ResponseEntity<?> addPost(@RequestBody Post post) {

        PostData postData = postService.addPost(post);

        if (postData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(postData, HttpStatus.CREATED);
    }

    @GetMapping("/view/{documentId}")
    public ResponseEntity<?> viewPost(@PathVariable("documentId") Long documentId) {

        Optional<PostData> postData = postService.viewPost(documentId);

        if (postData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!postData.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(postData, HttpStatus.OK);
    }
}
