package ru.otus.hw.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw.models.Author;

import java.util.List;

public interface AuthorRepository extends CrudRepository<Author, Long> {
    @Override
    List<Author> findAll();

}
