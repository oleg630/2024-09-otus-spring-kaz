package ru.otus.hw09.services;

import ru.otus.hw09.models.Genre;

import java.util.List;
import java.util.Set;

public interface GenreService {
    List<Genre> findAll();

    List<Genre> findById(Set<Long> ids);
}
