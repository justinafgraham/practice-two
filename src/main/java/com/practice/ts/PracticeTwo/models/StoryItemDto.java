package com.practice.ts.PracticeTwo.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.repository.Query;

@Data
@Entity
@Table(name = "stories")
public class StoryItemDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer _id;
    @JsonAlias("id")
    int story_id;
    String by;
    Integer[] kids;
    String title;
    String type;
}
