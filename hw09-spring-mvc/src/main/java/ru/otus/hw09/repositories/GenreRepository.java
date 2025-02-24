package ru.otus.hw09.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw09.models.Genre;

import java.util.List;

public interface GenreRepository extends CrudRepository<Genre, Long> {
    List<Genre> findAll();

    List<Genre> findByIdIn(List<Long> id);
}
