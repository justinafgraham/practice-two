package com.practice.ts.PracticeTwo.repositories;

import com.practice.ts.PracticeTwo.models.StoryItemDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryItemRepo extends CrudRepository<StoryItemDto, Integer> {
    StoryItemDto getStoryItemDtoBy_id(int id);

    @Query(
            nativeQuery = true,
            value = "select * from stories order by id limit ?1"
    )
    List<StoryItemDto> getTopNewsItems(int count);
}
