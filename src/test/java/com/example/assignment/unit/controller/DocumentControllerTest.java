package com.example.assignment.unit.controller;

import com.example.assignment.controller.DocumentController;
import com.example.assignment.entity.DocumentData;
import com.example.assignment.service.DocumentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DocumentController.class)
public class DocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DocumentService documentService;

    @Test
    public void testAddDocument() throws Exception {

        MockMultipartFile file =
                new MockMultipartFile(
                        "document",
                        "assignment.pdf",
                        MediaType.APPLICATION_PDF_VALUE,
                        "<<pdf data>>".getBytes(StandardCharsets.UTF_8));

        mockMvc.perform(
                        multipart("/document/add")
                                .file(file)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testDeleteDocument() throws Exception {

        Mockito.lenient().when(documentService.deleteDocument(any(Long.class))).thenReturn(true);

        mockMvc.perform(
                delete("/document/delete/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testViewDocument() throws Exception {

        byte[] data = "Dummy Data".getBytes();

        DocumentData documentData = DocumentData.builder()
                .documentId(1L)
                .documentName("assignment.pdf")
                .documentType("application/pdf")
                .documentData(data)
                .build();

        List<DocumentData> documentDataList = new ArrayList<>();
        documentDataList.add(documentData);

        Mockito.lenient().when(documentService.viewDocument()).thenReturn(Optional.of(documentDataList));

        mockMvc.perform(
                        get("/document/view"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
