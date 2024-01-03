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
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class HomeController {
    @Autowired
    HackerNewsService hackerNewsService;

    @Autowired
    private StoryItemRepo storyItemRepo;
    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/top-10-news-items")
    public List<NewsItem> getTopNewsItems() {
        var stories = storyItemRepo.getTopNewsItems(10);
        var newsItems = stories.stream()
                .map(item -> {
                    var parentId = item.getStory_id();
                    var comment = commentRepo.getCommentDtoByParent(parentId);
                    var newsItem = new NewsItem();
                    newsItem.setComments(comment);
                    newsItem.setType(item.getType());
                    newsItem.setTitle(item.getTitle());
                    return newsItem;
                })
                .toList();

        return newsItems;
    }


    public List<StoryItemDto> getWithRestTemplate() {
        var result = new HackerNewsService(commentRepo, restTemplate).getWithRestTemplate();
        storyItemRepo.saveAll(result);

        return result;
    }

    public StoryItemDto getStoryItemById() {
        var item = storyItemRepo.getStoryItemDtoBy_id(1);

        return item;
    }

    public List<CommentDto> getCommentsForStory() {
        List<CommentDto> comments = commentRepo.getCommentDtoByParent(38845510);
        return comments;
    }
}
