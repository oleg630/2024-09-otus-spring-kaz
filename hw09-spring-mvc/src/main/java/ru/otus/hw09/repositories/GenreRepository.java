package ru.otus.hw09.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw09.models.Genre;

import java.util.List;
import java.util.Set;

public interface GenreRepository extends CrudRepository<Genre, Long> {
    List<Genre> findAll();

    List<Genre> findByIdIn(Set<Long> id);
}
