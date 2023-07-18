package com.example.assignment.integration;

import com.example.assignment.AssignmentApplication;
import com.example.assignment.entity.DocumentData;
import com.example.assignment.repository.DocumentRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AssignmentApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DocumentIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private DocumentRepository documentRepository;

    TestRestTemplate restTemplate = new TestRestTemplate();

    @BeforeEach
    public void setupData() {
        byte[] data = "Dummy Data".getBytes();

        DocumentData documentData = DocumentData.builder()
                .documentName("assignment.pdf")
                .documentType("application/pdf")
                .documentData(data)
                .build();

        documentRepository.save(documentData);
    }

    @AfterEach
    public void removeData() {
        documentRepository.deleteAll();
    }

    //@Test
    public void testAddDocument() {
    }

    @Test
    public void testDeleteDocument_positive() {

        List<DocumentData> documentDataList = documentRepository.findAll();

        restTemplate.delete(
                createURLWithPort("/document/delete/" + documentDataList.get(0).getDocumentId())
        );

        Optional<DocumentData> documentData2 = documentRepository.findByDocumentId(documentDataList.get(0).getDocumentId());

        assertThat(documentData2.isEmpty());
    }

    @Test
    public void testDeleteDocument_negative() {

        documentRepository.deleteAll();

        restTemplate.delete(
                createURLWithPort("/document/delete/1")
        );

        List<DocumentData> documentDataList = documentRepository.findAll();

        assertThat(documentDataList.isEmpty());
    }

    @Test
    public void testViewDocument_positive() {

        ResponseEntity<DocumentData[]> response = restTemplate.getForEntity(
                createURLWithPort("/document/view"), DocumentData[].class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testViewDocument_negative() {

        documentRepository.deleteAll();

        ResponseEntity<DocumentData[]> response = restTemplate.getForEntity(
                createURLWithPort("/document/view"), DocumentData[].class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    public MultiValueMap<String, HttpEntity<?>> fromFile(MultipartFile file) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("document", file.getResource());
        return builder.build();
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
