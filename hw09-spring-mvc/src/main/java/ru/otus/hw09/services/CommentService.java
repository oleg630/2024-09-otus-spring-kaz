package ru.otus.hw09.services;

import ru.otus.hw09.models.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findByBookId(long bookId);

    Comment insert(long bookId, String text);

    Comment update(long id, String text);

    void deleteById(long id);

}
