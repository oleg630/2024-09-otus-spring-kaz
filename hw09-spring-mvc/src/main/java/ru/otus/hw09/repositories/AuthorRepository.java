package ru.otus.hw09.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw09.models.Author;

import java.util.List;

public interface AuthorRepository extends CrudRepository<Author, Long> {
    @Override
    List<Author> findAll();

}
