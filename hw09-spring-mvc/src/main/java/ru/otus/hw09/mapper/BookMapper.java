package ru.otus.hw09.mapper;

import ru.otus.hw09.models.Book;
import ru.otus.hw09.models.dto.AuthorDto;
import ru.otus.hw09.models.dto.BookDto;
import ru.otus.hw09.models.dto.BookUpdateDto;

import java.util.stream.Collectors;


public class BookMapper {

    public static BookDto toDto(Book book) {
        AuthorDto authorDto = new AuthorDto(book.getAuthor().getId(), book.getAuthor().getFullName());

        return new BookDto(book.getId(), book.getTitle(), authorDto,
                book.getGenres().stream().map(g -> g.getId()).collect(Collectors.toList()),
                book.getGenres().stream().map(g -> g.getName()).collect(Collectors.joining(", ")));
    }

    public static BookUpdateDto toUpdateDto(Book book) {
        return new BookUpdateDto(book.getId(), book.getTitle(), book.getAuthor().getId(),
                book.getGenres().stream().map(g -> g.getId()).collect(Collectors.toList()));
    }

    public static BookUpdateDto toUpdateDto(BookDto book) {
        return new BookUpdateDto(book.getId(), book.getTitle(), book.getAuthor().getId(), book.getGenresId());
    }
}
