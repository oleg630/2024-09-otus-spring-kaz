package ru.otus.hw08.service;

import ru.otus.hw08.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findByBookId(String bookId);

    Comment insert(String bookId, String text);

    Comment update(String bookId, String id, String text);

    void deleteById(String bookId, String id);

}
