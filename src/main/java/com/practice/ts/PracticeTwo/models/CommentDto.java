package com.practice.ts.PracticeTwo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class CommentDto implements Comparable<CommentDto>{
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

    @Override
    public int compareTo(CommentDto o) {
        return (this.by == null || o.by == null)? -1: this.by.toLowerCase().compareTo(o.by.toLowerCase());
    }
}
