package ru.otus.hw09.services;

import ru.otus.hw09.models.dto.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> findByBookId(long bookId);

    CommentDto insert(long bookId, String text);

    CommentDto update(long id, String text);

    void deleteById(long id);

}
