package com.example.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Post {

    private Long documentId;
    private Integer userId;
    private Integer id;
    private String title;
    private String body;
}
