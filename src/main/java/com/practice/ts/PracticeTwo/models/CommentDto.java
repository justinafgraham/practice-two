package com.practice.ts.PracticeTwo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class CommentDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer _id;
    @Column(name = "comment_id")
    public Integer id;
    public String by;
    @Column(columnDefinition = "text")
    public String text;
    public Integer[] kids;
    public Integer parent;
    public String type;
}
