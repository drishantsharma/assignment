package com.example.assignment.integration;

import com.example.assignment.AssignmentApplication;
import com.example.assignment.entity.CommentData;
import com.example.assignment.entity.DocumentData;
import com.example.assignment.entity.PostData;
import com.example.assignment.model.Comment;
import com.example.assignment.repository.CommentRepository;
import com.example.assignment.repository.DocumentRepository;
import com.example.assignment.repository.PostRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AssignmentApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommentIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    public void setupData() {

        byte[] data = "Dummy Data".getBytes();

        DocumentData documentData = DocumentData.builder()
                .documentId(1L)
                .documentName("assignment.pdf")
                .documentType("application/pdf")
                .documentData(data)
                .build();

        documentRepository.save(documentData);

        PostData postData = PostData.builder()
                .postId(10)
                .userId(1)
                .title("Post Title")
                .body("Post Body")
                .documentId(1L)
                .build();

        postRepository.save(postData);
    }

    @AfterAll
    public void cleanData() {
        documentRepository.deleteAll();
        postRepository.deleteAll();
        commentRepository.deleteAll();
    }

    @Test
    public void testAddComment_positive() {

        Comment comment = new Comment(10, 1, "Drishant", "email@gmail.com", "Comment Body");

        HttpEntity<Comment> entity = new HttpEntity<>(comment, headers);

        ResponseEntity<CommentData> response = restTemplate.exchange(
                createURLWithPort("/comment/add"),
                HttpMethod.POST, entity, CommentData.class);

        HttpStatusCode statusCode = response.getStatusCode();

        assertThat(statusCode).isEqualTo(HttpStatusCode.valueOf(201));
    }

    @Test
    public void testAddComment_negative() {

        postRepository.deleteAll();

        Comment comment = new Comment(10, 1, "Drishant", "email@gmail.com", "Comment Body");

        HttpEntity<Comment> entity = new HttpEntity<>(comment, headers);

        ResponseEntity<CommentData> response = restTemplate.exchange(
                createURLWithPort("/comment/add"),
                HttpMethod.POST, entity, CommentData.class);

        HttpStatusCode statusCode = response.getStatusCode();

        assertThat(statusCode).isEqualTo(HttpStatusCode.valueOf(404));
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
