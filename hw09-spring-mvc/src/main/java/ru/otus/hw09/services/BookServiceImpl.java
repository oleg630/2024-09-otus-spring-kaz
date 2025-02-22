package ru.otus.hw09.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw09.exceptions.EntityNotFoundException;
import ru.otus.hw09.mapper.BookMapper;
import ru.otus.hw09.models.Book;
import ru.otus.hw09.models.dto.BookDto;
import ru.otus.hw09.models.dto.BookUpdateDto;
import ru.otus.hw09.repositories.AuthorRepository;
import ru.otus.hw09.repositories.BookRepository;
import ru.otus.hw09.repositories.GenreRepository;

import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    @Override
    public BookDto findById(long id) {
        return bookRepository.findById(id).map(BookMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " not found"));
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream().map(BookMapper::toDto).toList();
    }

    @Transactional
    @Override
    public BookUpdateDto insert(String title, long authorId, List<Long> genresIds) {
        return save(new Book(), title, authorId, genresIds);
    }

    @Transactional
    @Override
    public BookUpdateDto update(long id, String title, long authorId, List<Long> genresIds) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("book with id " + id + " not found"));
        save(book, title, authorId, genresIds);
        return BookMapper.toUpdateDto(book);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    private BookUpdateDto save(Book book, String title, long authorId, List<Long> genresIds) {
        if (isEmpty(genresIds)) {
            throw new IllegalArgumentException("Genres ids must not be null");
        }

        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %d not found".formatted(authorId)));
        var genres = genreRepository.findByIdIn(genresIds);
        if (isEmpty(genres) || genresIds.size() != genres.size()) {
            throw new EntityNotFoundException("One or all genres with ids %s not found".formatted(genresIds));
        }

        book.setTitle(title);
        book.setAuthor(author);
        book.setGenres(genres);
        return BookMapper.toUpdateDto(bookRepository.save(book));
    }
}
