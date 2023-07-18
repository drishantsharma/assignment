package com.example.assignment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_data")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostData {

    @Id
    private Integer postId;
    private Integer userId;
    private String title;
    private String body;
    private Long documentId;
}
