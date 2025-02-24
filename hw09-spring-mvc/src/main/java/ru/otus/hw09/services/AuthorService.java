package ru.otus.hw09.services;

import ru.otus.hw09.models.Author;

import java.util.List;

public interface AuthorService {
    List<Author> findAll();

    Author findById(long id);
}
