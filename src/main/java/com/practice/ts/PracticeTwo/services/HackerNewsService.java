package com.practice.ts.PracticeTwo.services;

import com.google.gson.GsonBuilder;
import com.practice.ts.PracticeTwo.models.StoryItemDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.net.http.HttpResponse.BodyHandlers.ofString;

@Service
public class HackerNewsService {
    private final String baseurl = "https://hacker-news.firebaseio.com/v0/";
    private final int TOP_LIMIT = 10;


    public List<StoryItemDto> getRestTemplate() {
        List<Integer> topIds = Arrays.stream(Objects.requireNonNull(
                        new RestTemplate()
                                .getForEntity(baseurl + "topstories.json", Integer[].class)
                                .getBody()))
                .limit(TOP_LIMIT)
                .toList();

        List<URI> uris = topIds.stream().map(this::buildUri).toList();

        List<StoryItemDto> storyItems = uris.stream()
                .map(uri->new RestTemplate().getForEntity(uri, StoryItemDto.class).getBody())
                .toList();

        return storyItems;
    }
    public List<StoryItemDto> getTopStoryItems() {
        List<Integer> topIds = Arrays.stream(Objects.requireNonNull(
                        new RestTemplate()
                                .getForEntity(baseurl + "topstories.json", Integer[].class)
                                .getBody()))
                .limit(TOP_LIMIT)
                .toList();

        List<URI> uris = topIds.stream().map(this::buildUri).toList();

        HttpClient client = HttpClient.newHttpClient();
        List<HttpRequest> requests = uris.stream()
                .map(HttpRequest::newBuilder)
                .map(HttpRequest.Builder::build)
                .toList();

        List<StoryItemDto> storyNewsItems = requests.parallelStream()
                .map(request -> {
                    try {
                        return client.send(request, ofString());
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .filter(Objects::nonNull)
                .map(item -> new GsonBuilder().create().fromJson(item.body(), StoryItemDto.class))
                .toList();

        return storyNewsItems;
    }


    private URI buildUri(Integer id) {
        try {
            return new URI(baseurl + "item/" + id + ".json");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}


