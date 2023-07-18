package com.example.assignment.unit.service;

import com.example.assignment.entity.CommentData;
import com.example.assignment.entity.PostData;
import com.example.assignment.feignClient.CommentClient;
import com.example.assignment.model.Comment;
import com.example.assignment.repository.CommentRepository;
import com.example.assignment.repository.PostRepository;
import com.example.assignment.service.CommentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @InjectMocks
    CommentService commentService;

    @Mock
    PostRepository postRepository;

    @Mock
    CommentRepository commentRepository;

    @Mock
    CommentClient commentClient;

    @Test
    public void testAddComment() throws SQLException, IOException {

        Comment comment = new Comment(10, 1, "Drishant", "email@gmail.com", "Comment Body");

        PostData postData1 = new PostData(10, 1, "Post Title", "Post Body", 1L);

        Mockito.lenient().when(postRepository.findByPostId(any(Integer.class))).thenReturn(Optional.of(postData1));

        CommentData commentData = new CommentData(1, 10, "Drishant", "email@gmail.com", "Comment Body");

        Mockito.lenient().when(commentClient.getComment(any(Comment.class))).thenReturn(comment);
        Mockito.lenient().when(commentRepository.save(any(CommentData.class))).thenReturn(commentData);

        CommentData commentData1 = commentService.addComment(comment);

        assertThat(commentData1).isEqualTo(commentData);
    }

}
