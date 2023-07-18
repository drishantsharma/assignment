package com.example.assignment.controller;

import com.example.assignment.entity.*;
import com.example.assignment.model.Comment;
import com.example.assignment.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@Slf4j
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/add")
    public ResponseEntity<?> addComment(@RequestBody Comment comment) {

        CommentData commentData = commentService.addComment(comment);

        if (commentData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(commentData, HttpStatus.CREATED);
    }
}
