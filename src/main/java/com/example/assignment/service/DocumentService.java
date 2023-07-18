package com.example.assignment.service;

import com.example.assignment.entity.DocumentData;
import com.example.assignment.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {

    @Autowired
    DocumentRepository repository;

    public DocumentData addDocument(MultipartFile file) throws IOException {

        byte[] data = file.getBytes();

        DocumentData documentData = DocumentData.builder()
                        .documentName(file.getOriginalFilename())
                                .documentType(file.getContentType())
                                        .documentData(data)
                                                .build();

        return repository.save(documentData);
    }

    public Boolean deleteDocument(Long documentId) {

        Optional<DocumentData> documentData = repository.findByDocumentId(documentId);
        if (!documentData.isPresent()) {
            return false;
        }

        repository.delete(documentData.get());

        return true;
    }

    public Optional<List<DocumentData>> viewDocument() {

        Optional<List<DocumentData>> documentDataList = Optional.of(repository.findAll());

        return documentDataList;
    }
}
