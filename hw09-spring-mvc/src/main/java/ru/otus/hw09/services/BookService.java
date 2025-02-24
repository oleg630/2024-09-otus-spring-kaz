package ru.otus.hw09.services;

import ru.otus.hw09.models.dto.BookDto;
import ru.otus.hw09.models.dto.BookUpdateDto;

import java.util.List;

public interface BookService {
    BookDto findById(long id);

    List<BookDto> findAll();

    BookUpdateDto insert(String title, long authorId, List<Long> genresIds);

    BookUpdateDto update(long id, String title, long authorId, List<Long> genresIds);

    void deleteById(long id);

}
