package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.Ops;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.sql.SQLExpressions;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.expert.domain.comment.entity.QComment.comment;
import static org.example.expert.domain.manager.entity.QManager.manager;
import static org.example.expert.domain.todo.entity.QTodo.todo;

@Repository
@RequiredArgsConstructor
public class TodoQueryDslRepositoryImpl implements TodoQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {
        return Optional.ofNullable(
                jpaQueryFactory.selectFrom(todo)
                        .leftJoin(todo.user).fetchJoin()
                        .where(todo.id.eq(todoId))
                        .fetchOne()
        );
    }

    @Override
    public Page<TodoSearchResponse> searchTodo(Pageable pageable, String title, String nickname, LocalDate startDate, LocalDate endDate) {
        List<TodoSearchResponse> content =
                jpaQueryFactory
                        .select(Projections.constructor(TodoSearchResponse.class,
                                todo.title,
                                manager.countDistinct().as("managerCount"),
                                comment.countDistinct().as("commentCount")
                        ))
                        .from(todo)
                        .leftJoin(todo.managers, manager)
                        .leftJoin(todo.comments, comment)
                        .where(containsTitle(title), createdAtOrAfter(startDate), createdAtOrBefore(endDate), containsUserNickname(nickname))
                        .groupBy(todo.id)
                        .orderBy(todo.createdAt.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        Long total = jpaQueryFactory
                .select(todo.count())
                .from(todo)
                .where(containsTitle(title), createdAtOrAfter(startDate), createdAtOrBefore(endDate), containsUserNickname(nickname))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression containsTitle(String title) {
        if (title == null) {
            return null;
        }
        return todo.title.contains(title);
    }

    private BooleanExpression createdAtOrAfter(LocalDate startDate) {
        if (startDate == null) {
            return null;
        }

        return Expressions.dateTimeOperation(
                LocalDate.class,
                Ops.DateTimeOps.DATE,
                todo.createdAt
        ).goe(startDate);
    }

    private BooleanExpression createdAtOrBefore(LocalDate endDate) {
        if (endDate == null) {
            return null;
        }

        return Expressions.dateTimeOperation(
                LocalDate.class,
                Ops.DateTimeOps.DATE,
                todo.createdAt
        ).loe(endDate);
    }

    private BooleanExpression containsUserNickname(String nickname) {
        if (nickname == null) {
            return null;
        }
        return todo.user.nickname.contains(nickname);
    }
}
