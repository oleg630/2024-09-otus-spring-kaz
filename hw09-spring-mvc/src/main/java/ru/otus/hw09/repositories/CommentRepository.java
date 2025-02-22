package ru.otus.hw09.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw09.models.Comment;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findByBookId(Long bookId);

}
