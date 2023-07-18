package com.example.assignment.controller;

import com.example.assignment.entity.DocumentData;
import com.example.assignment.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping("/add")
    public ResponseEntity<?> addDocument(@RequestParam("document") MultipartFile document) throws IOException, SQLException {

        if (!document.getContentType().equals("application/pdf")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        DocumentData documentData = documentService.addDocument(document);

        return new ResponseEntity<>(documentData, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{documentId}")
    public ResponseEntity<?> deleteDocument(@PathVariable("documentId") Long documentId) {

        Boolean isDeleted = documentService.deleteDocument(documentId);

        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/view")
    public ResponseEntity<?> viewDocument() {

        Optional<List<DocumentData>> documentDataList = documentService.viewDocument();

        if (documentDataList.get().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(documentDataList.get(), HttpStatus.OK);
    }
}
