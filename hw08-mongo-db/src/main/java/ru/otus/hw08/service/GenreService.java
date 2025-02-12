package ru.otus.hw08.service;

import ru.otus.hw08.model.Genre;

import java.util.List;
import java.util.Set;

public interface GenreService {
    List<Genre> findAll();

    List<Genre> findById(Set<String> ids);
}
