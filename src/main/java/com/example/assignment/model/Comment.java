package com.example.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comment {

    private Integer postId;
    private Integer id;
    private String name;
    private String email;
    private String body;
}
