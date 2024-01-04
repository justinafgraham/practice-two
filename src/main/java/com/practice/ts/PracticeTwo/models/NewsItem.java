package com.practice.ts.PracticeTwo.models;

import lombok.Data;

import java.util.List;

@Data
public class NewsItem implements Comparable<NewsItem> {
    String title;
    String type;
    List<CommentDto> comments;


    @Override
    public int compareTo(NewsItem o) {
        return o.title.compareTo(this.title);
    }

    public int compareCommentCount(NewsItem o) {
        return this.comments.size() < o.comments.size() ? 1 : -1;
    }
}
