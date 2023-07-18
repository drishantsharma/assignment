package com.example.assignment.unit.controller;

import com.example.assignment.controller.CommentController;
import com.example.assignment.entity.CommentData;
import com.example.assignment.model.Comment;
import com.example.assignment.service.CommentService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Test
    public void testAddComment() throws Exception {

        CommentData commentData = CommentData.builder()
                .commentId(1)
                .postId(10)
                .name("Drishant")
                .email("email@gmail.com")
                .body("Comment Body")
                .build();

        Mockito.lenient().when(commentService.addComment(any(Comment.class))).thenReturn(commentData);

        Comment comment = new Comment(10, 1, "Drishant", "email@gmail.com", "Comment Body");

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(
                        post("/comment/add")
                                .content(objectMapper.writeValueAsString(comment))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
