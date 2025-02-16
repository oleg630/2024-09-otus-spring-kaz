package ru.otus.hw08.service;

import ru.otus.hw08.model.Author;
import java.util.List;

public interface AuthorService {
    List<Author> findAll();

    Author findById(String id);
}
