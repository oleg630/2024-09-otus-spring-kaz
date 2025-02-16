package ru.otus.hw08;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw08.model.Author;
import ru.otus.hw08.model.Book;
import ru.otus.hw08.model.Genre;
import ru.otus.hw08.service.BookService;
import ru.otus.hw08.service.BookServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Сервис для работы с книгами")
@DataMongoTest
@EnableConfigurationProperties
@Import({BookServiceImpl.class})
class BookServiceTest {

    @Autowired
    private BookService bookService;

    private final List<Book> bookList;

    BookServiceTest() {
        bookList = List.of(
                new Book("1", "Book_1", new Author("1", "Author_1"),
                        List.of(new Genre("1", "Genre_1"), new Genre("2", "Genre_2"))),
                new Book("2", "Book_2", new Author("2", "Author_2"),
                        List.of(new Genre("3", "Genre_3"), new Genre("4", "Genre_4"))),
                new Book("3", "Book_3", new Author("3", "Author_3"),
                        List.of(new Genre("5", "Genre_5"), new Genre("6", "Genre_6")))
        );
    }

    @DisplayName("должен загружать информацию о книге по ее id")
    @Test
    void shouldFindExpectedBookById() {
        Book expectedBook = bookList.get(0);
        Optional<Book> optionalActualBook = bookService.findById(expectedBook.getId());
        compareTwoBooks(expectedBook, optionalActualBook.orElse(null));
    }

    @DisplayName("должен загружать список всех книг")
    @Test
    void shouldReturnCorrectBooksList() {
        List<Book> actualBooks = bookService.findAll();
        List<Book> expectedBooks = bookList;
        for (int i = 0; i < expectedBooks.size(); i++) {
            compareTwoBooks(expectedBooks.get(i), actualBooks.get(i));
        }
        assertThat(actualBooks.get(0).getAuthor().getFullName()).isNotNull();
        assertThat(actualBooks.get(0).getGenres().get(0).getName()).isNotNull();
    }

    @DisplayName("должен сохранять новую книгу, а потом удалять")
    @Test
    void shouldSaveNewBookAndDelete() {
        var expectedBook = bookList.get(1);
        expectedBook.setId(null);
        var returnedBook = bookService.insert(expectedBook.getTitle(), expectedBook.getAuthor().getId(),
                expectedBook.getGenres().stream().map(Genre::getId).collect(Collectors.toSet()));
        compareTwoBooks(expectedBook, returnedBook);

        compareTwoBooks(returnedBook, bookService.findById(returnedBook.getId()).orElse(null));

        bookService.deleteById(returnedBook.getId());
        assertThat(bookService.findById(returnedBook.getId())).isEmpty();
    }


    @DisplayName("должен сохранять измененную книгу")
    @Test
    void shouldSaveUpdatedBook() {
        var expected = new Book("2", "new title", new Author("3", "Author_3"),
                List.of(new Genre("1", "Genre_1"), new Genre("6", "Genre_6")));

        Optional<Book> actual = bookService.findById(expected.getId());
        assertTrue(actual.isPresent());
        assertNotEquals(expected.getTitle(), actual.get().getTitle());
        assertNotEquals(expected.getAuthor().getId(), actual.get().getAuthor().getId());
        assertThat(expected.getGenres().stream().map(Genre::getId).toArray())
                .isNotSameAs(actual.get().getGenres().stream().map(Genre::getId).toArray());

        var returnedBook = bookService.update(expected.getId(), expected.getTitle(),
                expected.getAuthor().getId(), expected.getGenres().stream().map(Genre::getId)
                        .collect(Collectors.toSet()));
        assertThat(returnedBook).isNotNull()
                .matches(book -> !book.getId().isBlank())
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expected);

        compareTwoBooks(returnedBook, bookService.findById(returnedBook.getId()).orElse(null));

        // откат
        expected = bookList.get(1);
        bookService.update(expected.getId(), expected.getTitle(),
                expected.getAuthor().getId(), expected.getGenres().stream().map(Genre::getId)
                        .collect(Collectors.toSet()));
    }

    private void compareTwoBooks(Book expected, Book actual) {
        assertThat(actual).isNotNull()
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expected);
    }
}