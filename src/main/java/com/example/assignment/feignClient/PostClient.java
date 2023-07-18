package com.example.assignment.feignClient;

import com.example.assignment.model.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "https://jsonplaceholder.typicode.com", name = "post-service")
public interface PostClient {

    @PostMapping("/posts")
    Post getPost(@RequestBody Post post);
}
