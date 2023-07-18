package com.example.assignment.unit.controller;

import com.example.assignment.controller.PostController;
import com.example.assignment.entity.PostData;
import com.example.assignment.model.Post;
import com.example.assignment.service.DocumentService;
import com.example.assignment.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Test
    public void testAddPost() throws Exception {

        PostData postData = PostData.builder()
                        .postId(10)
                                .documentId(1L)
                                        .userId(1)
                                                .title("Post Title")
                                                        .body("Post Body")
                                                                .build();

        Mockito.lenient().when(postService.addPost(any(Post.class))).thenReturn(postData);

        Post post = new Post(1L, 1, 10, "Post Title", "Post Body");

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(
                        post("/post/add")
                                .content(objectMapper.writeValueAsString(post))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testViewPost() throws Exception {

        PostData postData = PostData.builder()
                .postId(10)
                .documentId(1L)
                .userId(1)
                .title("Post Title")
                .body("Post Body")
                .build();

        Mockito.lenient().when(postService.viewPost(any(Long.class))).thenReturn(Optional.of(postData));

        mockMvc.perform(
                        get("/post/view/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
