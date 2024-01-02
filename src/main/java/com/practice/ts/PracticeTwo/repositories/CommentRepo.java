package com.practice.ts.PracticeTwo.repositories;


import com.practice.ts.PracticeTwo.models.CommentDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends CrudRepository<CommentDto, Integer> {
    List<CommentDto> getCommentDtoByParent(int parentId);
}
