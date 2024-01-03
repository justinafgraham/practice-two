package com.practice.ts.PracticeTwo.services;

import com.practice.ts.PracticeTwo.models.CommentDto;
import com.practice.ts.PracticeTwo.models.StoryItemDto;
import com.practice.ts.PracticeTwo.repositories.CommentRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class HackerNewsService {
    private final String baseurl = "https://hacker-news.firebaseio.com/v0/";
    private final int TOP_LIMIT = 500;
    private final CommentRepo commentRepo;

    public RestTemplate restTemplate;

    public HackerNewsService(CommentRepo commentRepo, RestTemplate restTemplate) {
        this.commentRepo = commentRepo;
        this.restTemplate = restTemplate;
    }

    public List<StoryItemDto> getWithRestTemplate() {
        List<Integer> topIds = Arrays.stream(Objects.requireNonNull(
                        restTemplate
                                .getForEntity(baseurl + "topstories.json", Integer[].class)
                                .getBody()))
                .limit(TOP_LIMIT)
                .toList();

        List<String> uris = topIds.stream().map(id -> baseurl + "item/" + id + ".json").toList();

        List<StoryItemDto> storyItems = uris.parallelStream()
                .map(uri -> restTemplate.getForEntity(uri, StoryItemDto.class).getBody())
                .peek(item -> integrateStoryComments(item.getKids()))
                .toList();

        return storyItems;
    }

    private void integrateStoryComments(Integer[] commentIds) {
        if (commentIds == null) return;

        List<String> uris = Arrays.stream(commentIds).map(id -> baseurl + "item/" + id + ".json").toList();
        List<CommentDto> comments = uris.parallelStream()
                .filter(Objects::nonNull)
                .map(uri -> restTemplate.getForEntity(uri, CommentDto.class).getBody())
                .limit(TOP_LIMIT)
                .toList();

        commentRepo.saveAll(comments);
    }


}


