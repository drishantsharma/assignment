package com.example.assignment.repository;

import com.example.assignment.entity.PostData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostData, Integer> {

    Optional <PostData> findByDocumentId(Long documentId);

    Optional <PostData> findByPostId(Integer postId);
}
