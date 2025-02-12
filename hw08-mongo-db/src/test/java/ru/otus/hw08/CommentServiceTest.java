package ru.otus.hw08;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ru.otus.hw08.model.Author;
import ru.otus.hw08.model.Book;
import ru.otus.hw08.model.Comment;
import ru.otus.hw08.model.Genre;
import ru.otus.hw08.service.BookService;
import ru.otus.hw08.service.BookServiceImpl;
import ru.otus.hw08.service.CommentService;
import ru.otus.hw08.service.CommentServiceImpl;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DisplayName("Сервис для работы с комментами")
@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.hw08.repository"})
@Import({CommentServiceImpl.class, BookServiceImpl.class})
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BookService bookService;

    private final Comment testComment;

    CommentServiceTest() {
        Book book = new Book("1", "Book_1", new Author("1", "Author_1"),
                List.of(new Genre("1", "Genre_1"), new Genre("2", "Genre_2")));
        testComment = new Comment("1", book, "my comment 1");
    }


    @DisplayName("должен загружать информацию о комменте по  id")
    @Test
    void shouldFindExpectedCommentByBookId() {
        List<Comment> actualComments = commentService.findByBookId("1");
        assertEquals(1, actualComments.size());
        compareTwoComments(testComment, actualComments.get(0));
    }

    @DisplayName("должен сохранять новый коммент и удалять")
    @Test
    void shouldSaveNewComment() {
        Book book = bookService.findById("2").get();
        var expectedComment = new Comment("2", book, "CommentTitle_10500");
        var returnedComment = commentService.insert("2", expectedComment.getText());
        assertThat(returnedComment).isNotNull().matches(Comment -> !Comment.getId().isBlank());
        assertEquals(expectedComment.getText(), returnedComment.getText());

        compareTwoComments(returnedComment, commentService.findByBookId("2").get(0));

        commentService.deleteById(returnedComment.getId());
        assertEquals(0, commentService.findByBookId("2").size());
    }

    @DisplayName("должен сохранять измененный коммент")
    @Test
    void shouldSaveUpdatedComment() {
        Book book = bookService.findById("1").get();
        var expectedComment = new Comment("1", book, "CommentTitle_10500");

        assertNotEquals(expectedComment.getText(),
                commentService.findByBookId(expectedComment.getId()).get(0).getText());

        var returnedComment = commentService.update(expectedComment.getId(), expectedComment.getText());
        compareTwoComments(expectedComment, returnedComment);

        compareTwoComments(returnedComment, commentService.findByBookId(returnedComment.getId()).get(0));

        // откат
        commentService.update(testComment.getId(), testComment.getText());
    }

    private void compareTwoComments(Comment expected, Comment actual) {
        assertThat(actual).isNotNull()
                .matches(book -> !book.getId().isBlank())
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expected);
    }
}