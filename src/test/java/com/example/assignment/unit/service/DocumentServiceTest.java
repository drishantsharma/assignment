package com.example.assignment.unit.service;

import com.example.assignment.entity.DocumentData;
import com.example.assignment.repository.DocumentRepository;
import com.example.assignment.service.DocumentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DocumentServiceTest {

    @InjectMocks
    DocumentService documentService;

    @Mock
    DocumentRepository documentRepository;

    @Test
    public void testAddDocument() throws SQLException, IOException {

        byte[] content = "Dummy content".getBytes();

        MultipartFile file = new MockMultipartFile("assignment.pdf", "assignment.pdf", "application/pdf", content);

        DocumentData documentData = DocumentData.builder()
                .documentId(1L)
                .documentName(file.getOriginalFilename())
                .documentType(file.getContentType())
                .documentData(content)
                .build();

        Mockito.lenient().when(documentRepository.save(any(DocumentData.class))).thenReturn(documentData);

        DocumentData documentData1 = documentService.addDocument(file);

        DocumentData documentData2 = DocumentData.builder()
                .documentId(1L)
                .documentName("assignment.pdf")
                .documentType("application/pdf")
                .documentData(content)
                .build();

        assertThat(documentData1).isEqualTo(documentData2);
    }

    @Test
    public void testDeleteDocument() throws IOException, SQLException {

        byte[] content = "Dummy content".getBytes();

        MultipartFile file = new MockMultipartFile("assignment.pdf", "assignment.pdf", "application/pdf", content);

        Blob blob = new SerialBlob(file.getBytes());

        DocumentData documentData = DocumentData.builder()
                .documentId(1L)
                .documentName(file.getOriginalFilename())
                .documentType(file.getContentType())
                //.documentData(blob)
                .build();

        Mockito.lenient().when(documentRepository.findByDocumentId(any(Long.class))).thenReturn(Optional.of(documentData));

        Boolean isDeleted = documentService.deleteDocument(1L);

        assertThat(isDeleted).isTrue();
    }

    @Test
    public void testViewDocument() throws IOException, SQLException {

        byte[] content = "Dummy content".getBytes();

        MultipartFile file = new MockMultipartFile("assignment.pdf", "assignment.pdf", "application/pdf", content);

        DocumentData documentData = DocumentData.builder()
                .documentId(1L)
                .documentName(file.getOriginalFilename())
                .documentType(file.getContentType())
                .documentData(content)
                .build();

        List<DocumentData> documentDataList1 = new ArrayList<>();
        documentDataList1.add(documentData);

        Mockito.lenient().when(documentRepository.findAll()).thenReturn(documentDataList1);

        Optional<List<DocumentData>> documentDataList = documentService.viewDocument();

        assertThat(documentDataList.get()).isEqualTo(documentDataList1);
    }
}
