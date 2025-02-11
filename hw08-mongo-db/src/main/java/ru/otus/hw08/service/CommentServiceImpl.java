package ru.otus.hw08.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw08.exceptions.EntityNotFoundException;
import ru.otus.hw08.model.Book;
import ru.otus.hw08.model.Comment;
import ru.otus.hw08.repository.BookRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findByBookId(String bookId) {
        Optional<Book> book = bookRepository.findById(bookId);

        return book.get().getComments();
    }

    @Transactional
    @Override
    public Comment insert(String bookId, String text) {
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new EntityNotFoundException("Book with id " + bookId + " not found"));

        Comment comment = new Comment(UUID.randomUUID().toString(), text);
        book.getComments().add(comment);
        bookRepository.save(book);
        return comment;
    }

    @Transactional
    @Override
    public Comment update(String bookId, String id, String text) {
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new EntityNotFoundException("Book with id " + bookId + " not found"));

        Comment comment = book.getComments().stream().filter(c -> c.getId().equals(id)).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Comment with id " + id + " not found"));
        comment.setText(text);
        bookRepository.save(book);

        return comment;
    }

    @Transactional
    @Override
    public void deleteById(String bookId, String id) {
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new EntityNotFoundException("Book with id " + bookId + " not found"));

        if (book.getComments().removeIf(c -> c.getId().equals(id))) {
            bookRepository.save(book);
        }
    }
}
