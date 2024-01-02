package com.practice.ts.PracticeTwo.services;

import com.google.gson.GsonBuilder;
import com.practice.ts.PracticeTwo.models.CommentDto;
import com.practice.ts.PracticeTwo.models.StoryItemDto;
import com.practice.ts.PracticeTwo.repositories.CommentRepo;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.net.http.HttpResponse.BodyHandlers.ofString;

@Service
public class HackerNewsService {
    private final String baseurl = "https://hacker-news.firebaseio.com/v0/";
    private final int TOP_LIMIT = 10;

    private final HttpClient client = HttpClient.newHttpClient();

    public CommentRepo commentRepo;

    public HackerNewsService(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    public List<StoryItemDto> getStoryItem() throws Exception {

        if (commentRepo == null) throw new Exception("no repo");

        var idReq = HttpRequest.newBuilder()
                .uri(buildUri("topstories.json"))
                .build();
        var idResp = client.send(idReq, ofString());
        var uris = Arrays.stream(new GsonBuilder().create().fromJson(idResp.body(), Integer[].class))
                .limit(TOP_LIMIT)
                .map(id -> buildUri("item/" + id + ".json"))
                .toList();

        List<HttpRequest> requests = uris.stream()
                .map(HttpRequest::newBuilder)
                .map(HttpRequest.Builder::build)
                .toList();

        List<StoryItemDto> storyNewsItems = requests.parallelStream()
                .map(req -> makeRequest(req, client))
                .filter(Objects::nonNull)
                .map(item -> new GsonBuilder().create().fromJson(item.body(), StoryItemDto.class))
                .map(item -> {
                    integrateComments(item.kids);
                    return item;
                })
                .toList();

        return storyNewsItems;
    }

    private void integrateComments(Integer[] commentIds) {
        var commentsReq = Arrays.stream(commentIds)
                .map(commentId -> buildUri("item/" + commentId + ".json"))
                .map(HttpRequest::newBuilder)
                .map(HttpRequest.Builder::build)
                .toList();

        var comments = commentsReq.parallelStream()
                .map(req -> makeRequest(req, client))
                .map(resp -> new GsonBuilder().create().fromJson(resp.body(), CommentDto.class))
                .limit(10)
                .toList();

        commentRepo.saveAll(comments);
    }

    private URI buildUri(String path) {
        try {
            return new URI(baseurl + path);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpResponse<String> makeRequest(HttpRequest request, HttpClient client) {
        try {
            return client.send(request, ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}


