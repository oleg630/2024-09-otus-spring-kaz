package ru.otus.hw09.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import ru.otus.hw09.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long> {
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "book_entity_graph")
    @Override
    Optional<Book> findById(Long id);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "book_entity_graph")
    @Override
    List<Book> findAll();
}
