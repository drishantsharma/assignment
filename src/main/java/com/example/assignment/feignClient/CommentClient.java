package com.example.assignment.feignClient;

import com.example.assignment.model.Comment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "https://jsonplaceholder.typicode.com", name = "comment-service")
public interface CommentClient {

    @PostMapping("/comments")
    Comment getComment(@RequestBody Comment comment);
}
