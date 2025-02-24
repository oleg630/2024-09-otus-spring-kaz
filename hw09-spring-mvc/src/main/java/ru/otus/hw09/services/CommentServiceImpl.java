package ru.otus.hw09.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw09.exceptions.EntityNotFoundException;
import ru.otus.hw09.models.Comment;
import ru.otus.hw09.models.dto.CommentDto;
import ru.otus.hw09.repositories.CommentRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> findByBookId(long bookId) {
        List<Comment> comments = commentRepository.findByBookId(bookId);

        return comments.stream().map(c -> new CommentDto(c.getId(), bookId, c.getText())).toList();
    }

    @Transactional
    @Override
    public CommentDto insert(long bookId, String text) {
        Comment comment = commentRepository.save(new Comment(null, bookId, text));
        return new CommentDto(comment.getId(), bookId, comment.getText());
    }

    @Transactional
    @Override
    public CommentDto update(long id, String text) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id " + id + " not found"));
        comment.setText(text);
        commentRepository.save(comment);

        return new CommentDto(comment.getId(), comment.getBookId(), comment.getText());
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }
}
