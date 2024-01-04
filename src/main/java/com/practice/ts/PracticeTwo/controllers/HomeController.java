package com.practice.ts.PracticeTwo.controllers;

import com.practice.ts.PracticeTwo.models.CommentDto;
import com.practice.ts.PracticeTwo.models.NewsItem;
import com.practice.ts.PracticeTwo.models.StoryItemDto;
import com.practice.ts.PracticeTwo.repositories.CommentRepo;
import com.practice.ts.PracticeTwo.repositories.StoryItemRepo;
import com.practice.ts.PracticeTwo.services.HackerNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {
    @Autowired
    HackerNewsService hackerNewsService;

    @Autowired
    private StoryItemRepo storyItemRepo;
    @Autowired
    CommentRepo commentRepo;

    @GetMapping("/top-news-items-with-comments")
    public List<NewsItem> getTopNewsItems() {
//        Comparator<NewsItem> comparator =
        var stories = storyItemRepo.getTopNewsItems(10);
        var newsItems = stories.stream()
                .map(item -> {
                    var parentId = item.id;
                    var comment = commentRepo.getCommentDtoByParent(parentId).stream().sorted().toList();
                    var newsItem = new NewsItem();

                    newsItem.setComments(comment);
                    newsItem.setType(item.type);
                    newsItem.setTitle(item.title);
                    return newsItem;
                })
                .sorted()
                .toList();

        return newsItems;
    }

    ;

    public List<StoryItemDto> getTopStoryItems() throws Exception {
        var result = new HackerNewsService(commentRepo).getStoryItem();
        storyItemRepo.saveAll(result);

        return result;
    }

    public StoryItemDto getStoryItemById() {
        var item = storyItemRepo.getStoryItemDtoBy_id(1);

        return item;
    }

    public List<CommentDto> getCommentsForStory() {
        var item = commentRepo.getCommentDtoByParent(38845510);

        return item;

    }
}
