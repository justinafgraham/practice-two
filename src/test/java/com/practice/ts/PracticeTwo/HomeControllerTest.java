package com.practice.ts.PracticeTwo;

import com.practice.ts.PracticeTwo.controllers.HomeController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class HomeControllerTest {

    @Autowired
    private HomeController sut;

    final int ITEM_COUNT = 500;

    @BeforeEach
    public void setup() {
    }


    @Test
    void testGetRestTemplate() {
        // 25.3, 24,9, 25.5
        var actual = sut.getWithRestTemplate();
        assertThat(actual.size()).isEqualTo(ITEM_COUNT);
    }

    @Test
    void testGetStoryItem() {
        var item = sut.getStoryItemById();
        assertThat(item.getStory_id()).isNotNull();
    }

    @Test
    void testGetCommentForStory() {
        var actual = sut.getCommentsForStory();
        assertThat(actual.size()).isEqualTo(ITEM_COUNT);
    }

    @Test
    void testGetTopBy_id() {
        var actual = sut.getTopNewsItems();
        assertThat(actual).isNotNull();
    }
}
