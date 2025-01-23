package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface TodoQueryDslRepository {

    Optional<Todo> findByIdWithUser(Long todoId);

    Page<TodoSearchResponse> searchTodo(Pageable pageable, String title, String nickname, LocalDate startDate, LocalDate endDate);
}
