package ru.otus.hw08.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw08.model.Genre;

import java.util.List;
import java.util.Set;

public interface GenreRepository extends MongoRepository<Genre, String> {

    List<Genre> findByIdIn(Set<String> id);
}
