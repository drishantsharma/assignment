package com.example.assignment.repository;

import com.example.assignment.entity.CommentData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentData, Long> {
}
