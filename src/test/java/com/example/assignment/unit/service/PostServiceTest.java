package com.example.assignment.unit.service;

import com.example.assignment.entity.DocumentData;
import com.example.assignment.entity.PostData;
import com.example.assignment.feignClient.PostClient;
import com.example.assignment.model.Post;
import com.example.assignment.repository.DocumentRepository;
import com.example.assignment.repository.PostRepository;
import com.example.assignment.service.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @InjectMocks
    PostService postService;

    @Mock
    DocumentRepository documentRepository;

    @Mock
    PostRepository postRepository;

    @Mock
    PostClient postClient;

    @Test
    public void testAddPost() {

        byte[] content = "Dummy content".getBytes();

        MultipartFile file = new MockMultipartFile("assignment.pdf", "assignment.pdf", "application/pdf", content);

        DocumentData documentData = DocumentData.builder()
                .documentId(1L)
                .documentName(file.getOriginalFilename())
                .documentType(file.getContentType())
                .documentData(content)
                .build();

        Mockito.lenient().when(documentRepository.findByDocumentId(any(Long.class))).thenReturn(Optional.of(documentData));

        Post post = new Post(1L, 1, 10, "Post Title", "Post Body");

        Mockito.lenient().when(postClient.getPost(any(Post.class))).thenReturn(post);

        PostData postData1 = new PostData(10, 1, "Post Title", "Post Body", 1L);

        Mockito.lenient().when(postRepository.save(any(PostData.class))).thenReturn(postData1);

        PostData postData = postService.addPost(post);

        assertThat(postData).isEqualTo(postData1);
    }

    @Test
    public void testViewPost() {

        byte[] content = "Dummy content".getBytes();

        MultipartFile file = new MockMultipartFile("assignment.pdf", "assignment.pdf", "application/pdf", content);

        DocumentData documentData = DocumentData.builder()
                .documentId(1L)
                .documentName(file.getOriginalFilename())
                .documentType(file.getContentType())
                .documentData(content)
                .build();

        Mockito.lenient().when(documentRepository.findByDocumentId(any(Long.class))).thenReturn(Optional.of(documentData));

        PostData postData1 = new PostData(10, 1, "Post Title", "Post Body", 1L);

        Mockito.lenient().when(postRepository.findByDocumentId(any(Long.class))).thenReturn(Optional.of(postData1));

        PostData postData = postService.viewPost(1L).get();

        assertThat(postData).isEqualTo(postData1);
    }

}
