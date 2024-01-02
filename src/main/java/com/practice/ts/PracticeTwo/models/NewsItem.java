package com.practice.ts.PracticeTwo.models;

import lombok.Data;

import java.util.List;

@Data
public class NewsItem {
    String title;
    String type;
    List<CommentDto> comments;
}
