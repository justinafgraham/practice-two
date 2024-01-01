package com.practice.ts.PracticeTwo.controllers;

import com.practice.ts.PracticeTwo.models.StoryItemDto;
import com.practice.ts.PracticeTwo.repositories.StoryItemRepo;
import com.practice.ts.PracticeTwo.services.HackerNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {
    @Autowired
    HackerNewsService hackerNewsService;

    @Autowired
    private StoryItemRepo storyItemRepo;


//    public HomeController(StoryItemsRepo storyItemsRepo) {
//        this.storyItemsRepo = storyItemsRepo;
//    }


    public List<StoryItemDto> getTopStoryItems() {
        var result = new HackerNewsService().getTopStoryItems();
        storyItemRepo.saveAll(result);

        return result;
    }


    public List<StoryItemDto> getRestTemplate() {
        var result = new HackerNewsService().getRestTemplate();
        storyItemRepo.saveAll(result);

        return result;
    }
}
