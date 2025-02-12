package ru.otus.hw08;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw08.events.MongoBookCascadeDeleteEventsListener;
import ru.otus.hw08.model.Comment;
import ru.otus.hw08.repository.CommentRepositoryCustomImpl;
import ru.otus.hw08.service.BookService;
import ru.otus.hw08.service.BookServiceImpl;
import ru.otus.hw08.service.CommentService;
import ru.otus.hw08.service.CommentServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Событие удаления книги")
@DataMongoTest
@EnableConfigurationProperties
@Import({CommentServiceImpl.class, BookServiceImpl.class, MongoBookCascadeDeleteEventsListener.class,
        CommentRepositoryCustomImpl.class})
class BookCommentDeleteTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BookService bookService;

    @DisplayName("должен удалять комментарии при удалении книги")
    @Test
    void shouldFindExpectedCommentByBookId() {
        List<Comment> actualComments = commentService.findByBookId("1");
        assertEquals(1, actualComments.size());

        bookService.deleteById("1");

        actualComments = commentService.findByBookId("1");
        assertEquals(0, actualComments.size());
    }
}