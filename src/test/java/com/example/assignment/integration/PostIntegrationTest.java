package com.example.assignment.integration;

import com.example.assignment.AssignmentApplication;
import com.example.assignment.entity.DocumentData;
import com.example.assignment.entity.PostData;
import com.example.assignment.model.Post;
import com.example.assignment.repository.DocumentRepository;
import com.example.assignment.repository.PostRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AssignmentApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private PostRepository postRepository;

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
    }

    @AfterEach
    public void removeData() {
        documentRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    public void testAddPost_positive() {

        List<DocumentData> documentDataList = documentRepository.findAll();

        Post post = new Post(documentDataList.get(0).getDocumentId(), 1, null, "Post Title", "Post Body");

        HttpEntity<Post> entity = new HttpEntity<>(post, headers);

        ResponseEntity<PostData> response = restTemplate.exchange(
                createURLWithPort("/post/add"),
                HttpMethod.POST, entity, PostData.class);

        HttpStatusCode statusCode = response.getStatusCode();

        assertThat(statusCode).isEqualTo(HttpStatusCode.valueOf(201));
    }

    @Test
    public void testAddPost_negative() {

        documentRepository.deleteAll();

        Post post = new Post(1L, 1, null, "Post Title", "Post Body");

        HttpEntity<Post> entity = new HttpEntity<>(post, headers);

        ResponseEntity<PostData> response = restTemplate.exchange(
                createURLWithPort("/post/add"),
                HttpMethod.POST, entity, PostData.class);

        HttpStatusCode statusCode = response.getStatusCode();

        assertThat(statusCode).isEqualTo(HttpStatusCode.valueOf(404));
    }

    @Test
    public void testViewPost_positive() {

        List<DocumentData> documentDataList = documentRepository.findAll();

        PostData postData = PostData.builder()
                .postId(10)
                .userId(1)
                .title("Post Title")
                .body("Post Body")
                .documentId(documentDataList.get(0).getDocumentId())
                .build();

        postRepository.save(postData);

        ResponseEntity<PostData> response = restTemplate.getForEntity(
                createURLWithPort("/post/view/" + documentDataList.get(0).getDocumentId()), PostData.class
        );

        HttpStatusCode statusCode = response.getStatusCode();

        assertThat(statusCode).isEqualTo(HttpStatusCode.valueOf(200));
    }

    @Test
    public void testViewPost_negative_no_document() {

        documentRepository.deleteAll();

        ResponseEntity<PostData> response = restTemplate.getForEntity(
                createURLWithPort("/post/view/1"), PostData.class
        );

        HttpStatusCode statusCode = response.getStatusCode();

        assertThat(statusCode).isEqualTo(HttpStatusCode.valueOf(404));
    }
    @Test
    public void testViewPost_negative_no_post() {

        List<DocumentData> documentDataList = documentRepository.findAll();

        ResponseEntity<PostData> response = restTemplate.getForEntity(
                createURLWithPort("/post/view/" + documentDataList.get(0).getDocumentId()), PostData.class
        );

        HttpStatusCode statusCode = response.getStatusCode();

        assertThat(statusCode).isEqualTo(HttpStatusCode.valueOf(404));
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
