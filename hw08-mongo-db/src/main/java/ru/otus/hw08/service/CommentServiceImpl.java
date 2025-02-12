package ru.otus.hw08.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw08.exceptions.EntityNotFoundException;
import ru.otus.hw08.model.Book;
import ru.otus.hw08.model.Comment;
import ru.otus.hw08.repository.BookRepository;
import ru.otus.hw08.repository.CommentRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;

    @Override
    public List<Comment> findByBookId(String bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        List<Comment> comments = commentRepository.findByBookId(bookId);

        return comments.stream().map(c -> new Comment(c.getId(), book.get(), c.getText())).toList();
    }

    @Transactional
    @Override
    public Comment insert(String bookId, String text) {
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new EntityNotFoundException("Book with id " + bookId + " not found"));

        Comment comment = new Comment(UUID.randomUUID().toString(), book, text);
        commentRepository.insert(comment);
        return comment;
    }

    @Transactional
    @Override
    public Comment update(String id, String text) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id " + id + " not found"));
        comment.setText(text);
        commentRepository.save(comment);

        Book book = bookRepository.findById(comment.getBook().getId()).orElseThrow(() ->
                new EntityNotFoundException("Book with id " + comment.getBook().getId() + " not found"));

        return new Comment(comment.getId(), book, comment.getText());
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        commentRepository.deleteById(id);
    }
}
