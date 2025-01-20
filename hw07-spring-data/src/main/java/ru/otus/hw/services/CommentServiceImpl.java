package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.dto.CommentDto;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findByBookId(long bookId) {
        List<CommentDto> comments = commentRepository.findByBookId(bookId);
        Optional<Book> book = bookRepository.findById(bookId);

        return comments.stream().map(c -> new Comment(c.getId(), book.get(), c.getText())).toList();
    }

    @Transactional
    @Override
    public Comment insert(long bookId, String text) {
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new EntityNotFoundException("Book with id " + bookId + " not found"));

        CommentDto commentDto = commentRepository.save(new CommentDto(null, bookId, text));
        return new Comment(commentDto.getId(), book, commentDto.getText());
    }

    @Transactional
    @Override
    public Comment update(long id, String text) {
        CommentDto commentDto = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id " + id + " not found"));
        commentDto.setText(text);
        commentRepository.save(commentDto);

        Book book = bookRepository.findById(commentDto.getBookId()).orElseThrow(() ->
                new EntityNotFoundException("Book with id " + commentDto.getBookId() + " not found"));

        return new Comment(commentDto.getId(), book, commentDto.getText());
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }
}
