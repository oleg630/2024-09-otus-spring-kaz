package ru.otus.hw08.service;

import ru.otus.hw08.model.Book;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookService {
    Optional<Book> findById(String id);

    List<Book> findAll();

    Book insert(String title, String authorId, Set<String> genresIds);

    Book update(String id, String title, String authorId, Set<String> genresIds);

    void deleteById(String id);

}
