package ru.otus.hw08.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw08.model.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String>, CommentRepositoryCustom {
    List<Comment> findByBookId(String bookId);

    void removeCommentArrayElementsById(String id);
}
