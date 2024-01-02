package com.practice.ts.PracticeTwo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "comments")
public class CommentDto {
    @Id
    Integer id;
    String by;
    @Column(columnDefinition = "text")
    String text;
    Integer[] kids;
    Integer parent;
    String type;
}
