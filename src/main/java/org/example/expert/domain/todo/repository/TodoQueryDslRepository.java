package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TodoQueryDslRepository {

    @Query("SELECT t FROM Todo t " +
            "LEFT JOIN t.user " +
            "WHERE t.id = :todoId")
    Optional<Todo> findByIdWithUser(Long todoId);

}
