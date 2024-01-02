package com.practice.ts.PracticeTwo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "stories")
public class StoryItemDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer _id;
    @Column(name = "story_id")
    public Integer id;
    public String by;
    public Integer[] kids;
    public String title;
    public String type;
}
