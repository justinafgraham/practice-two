package com.practice.ts.PracticeTwo.repositories;

import com.practice.ts.PracticeTwo.models.StoryItemDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryItemRepo extends CrudRepository<StoryItemDto, Integer> {}
