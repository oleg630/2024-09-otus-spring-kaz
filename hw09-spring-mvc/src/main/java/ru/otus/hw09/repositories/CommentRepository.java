package ru.otus.hw09.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw09.models.dto.CommentDto;

import java.util.List;

public interface CommentRepository extends CrudRepository<CommentDto, Long> {
    List<CommentDto> findByBookId(Long bookId);

}
