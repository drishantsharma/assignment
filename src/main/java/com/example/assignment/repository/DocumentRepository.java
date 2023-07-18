package com.example.assignment.repository;

import com.example.assignment.entity.DocumentData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentData, Long> {

    Optional <DocumentData> findByDocumentId(Long documentId);
}
