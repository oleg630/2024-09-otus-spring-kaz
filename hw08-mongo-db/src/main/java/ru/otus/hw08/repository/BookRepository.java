package ru.otus.hw08.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw08.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {

    @Override
    Optional<Book> findById(String id);

    @Override
    List<Book> findAll();
}
