package ru.otus.hw08;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw08.model.Author;
import ru.otus.hw08.model.Genre;

import java.util.List;
import java.util.Optional;
import ru.otus.hw08.model.Book;
import ru.otus.hw08.repository.BookRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий MongoDB для работы с книгами")
@DataMongoTest
@EnableConfigurationProperties
class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @DisplayName("должен загружать информацию о книге студенте по ее id")
    @Test
    void shouldFindExpectedStudentById() {
        Optional<Book> optionalActualBook = repository.findById("1");
        Book expectedBook = mongoTemplate.findById("1", Book.class);
        assertThat(optionalActualBook).isPresent().get().isEqualToComparingFieldByField(expectedBook);
    }

    @DisplayName("должен загружать список всех книг")
    @Test
    void shouldReturnCorrectBooksList() {
        List<Book> actualBooks = repository.findAll();
        List<Book> expectedBooks = mongoTemplate.findAll(Book.class);
        assertThat(actualBooks).containsExactlyElementsOf(expectedBooks);
    }

    @DisplayName("должен сохранять новую книгу")
    @Test
    void shouldSaveNewBook() {
        var expectedBook = new Book(null, "BookTitle_10500", mongoTemplate.findById("1", Author.class),
                List.of(mongoTemplate.findById("1", Genre.class), mongoTemplate.findById("3", Genre.class)));
        var returnedBook = repository.save(expectedBook);
        assertThat(returnedBook).isNotNull()
                .matches(book -> !book.getId().isBlank())
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        assertThat(repository.findById(returnedBook.getId()))
                .isPresent()
                .get()
                .isEqualTo(returnedBook);
    }

    @DisplayName("должен сохранять измененную книгу")
    @Test
    void shouldSaveUpdatedBook() {
        var expectedBook = new Book("1", "BookTitle_10500", mongoTemplate.findById("1", Author.class),
                List.of(mongoTemplate.findById("1", Genre.class), mongoTemplate.findById("3", Genre.class)));

        assertThat(repository.findById(expectedBook.getId()))
                .isPresent()
                .get()
                .isNotEqualTo(expectedBook);

        var returnedBook = repository.save(expectedBook);
        assertThat(returnedBook).isNotNull()
                .matches(book -> !book.getId().isBlank())
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        assertThat(repository.findById(returnedBook.getId()))
                .isPresent()
                .get()
                .isEqualTo(returnedBook);
    }

    @DisplayName("должен удалять книгу по id ")
    @Test
    void shouldDeleteBook() {
        assertThat(repository.findById("1")).isPresent();
        repository.deleteById("1");
        assertThat(repository.findById("1")).isEmpty();
    }
}