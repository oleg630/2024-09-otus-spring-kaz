package ru.otus.hw09.controller;

import org.hamcrest.text.MatchesPattern;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.hw09.models.Author;
import ru.otus.hw09.models.Book;
import ru.otus.hw09.models.Genre;
import ru.otus.hw09.models.dto.BookDto;
import ru.otus.hw09.services.BookService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;    // = mock(BookService.class);

    private final List<Book> bookList;

    private static final String MATCH_ANY = "[\\s\\S]*";

    @Captor
    ArgumentCaptor<Long> idCaptor;

    @Captor
    ArgumentCaptor<String> titleCaptor;

    @Captor
    ArgumentCaptor<Long> authorIdCaptor;

    @Captor
    ArgumentCaptor<Set<Long>> genresIdCaptor;


    BookControllerTest() {
        bookList = List.of(
                new Book(1L, "BookTitle_1", new Author(1L, "Author_1"),
                        List.of(new Genre(1L, "Genre_1"), new Genre(2L, "Genre_2"))),
                new Book(2L, "BookTitle_2", new Author(2L, "Author_2"),
                        List.of(new Genre(3L, "Genre_3"), new Genre(4L, "Genre_4"))),
                new Book(3L, "BookTitle_3", new Author(3L, "Author_3"),
                        List.of(new Genre(5L, "Genre_5"), new Genre(6L, "Genre_6")))
        );
    }

    @Test
    void listBookPage() throws Exception {
        given(bookService.findAll()).willReturn(bookList);
        this.mvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(MatchesPattern.matchesPattern(
                        MATCH_ANY + bookList.stream().map(b -> b.getTitle())
                                .collect(Collectors.joining(MATCH_ANY)) + MATCH_ANY)))
                .andExpect(content().string(MatchesPattern.matchesPattern(
                        MATCH_ANY + bookList.stream().map(b -> b.getAuthor().getFullName())
                                .collect(Collectors.joining(MATCH_ANY)) + MATCH_ANY)))
                .andExpect(content().string(MatchesPattern.matchesPattern(
                        MATCH_ANY + bookList.stream().map(b -> b.getGenres().get(0).getName())
                                .collect(Collectors.joining(MATCH_ANY)) + MATCH_ANY)));
    }

    @Test
    void editBookPage() throws Exception {
        given(bookService.findById(1)).willReturn(Optional.ofNullable(bookList.get(0)));
        this.mvc.perform(MockMvcRequestBuilders.get("/edit?id={id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string(MatchesPattern.matchesPattern(
                        MATCH_ANY + bookList.get(0).getTitle() + MATCH_ANY)))
                .andExpect(content().string(MatchesPattern.matchesPattern(
                        "((?!" + bookList.get(1).getTitle() + ")[\\s\\S])*")));
    }

    @Test
    void editBookSave() throws Exception {
        BookDto bookDto = BookDto.fromDomainObject(bookList.get(0));

        this.mvc.perform(MockMvcRequestBuilders.post("/edit?genresId={genresId}", "1,2")
                        .flashAttr("book", bookDto))
                .andExpect(status().isFound());

        verify(bookService).update(idCaptor.capture(), titleCaptor.capture(), authorIdCaptor.capture(), genresIdCaptor.capture());
        assertEquals(bookDto.getId(), idCaptor.getValue());
        assertEquals(bookDto.getTitle(), titleCaptor.getValue());
        assertEquals(bookDto.getAuthorId(), authorIdCaptor.getValue());
        assertEquals(bookDto.getGenresId(), genresIdCaptor.getValue());
    }

    @Test
    void createBookPage() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/create"))
                .andExpect(status().isOk())
                .andExpect(content().string(MatchesPattern.matchesPattern(
                        MATCH_ANY + "Edit book" + MATCH_ANY)))
                .andExpect(content().string(MatchesPattern.matchesPattern(
                        "((?!" + "BookTitle" + ")[\\s\\S])*")));
    }

    @Test
    void createBookSave() throws Exception {
        BookDto bookDto = BookDto.fromDomainObject(bookList.get(0));

        this.mvc.perform(MockMvcRequestBuilders.post("/create?genresId={genresId}", "1,2")
                        .flashAttr("book", bookDto))
                .andExpect(status().isFound());

        verify(bookService).insert(titleCaptor.capture(), authorIdCaptor.capture(), genresIdCaptor.capture());
        assertEquals(bookDto.getTitle(), titleCaptor.getValue());
        assertEquals(bookDto.getAuthorId(), authorIdCaptor.getValue());
        assertEquals(bookDto.getGenresId(), genresIdCaptor.getValue());
    }

    @Test
    void deleteBook() throws Exception {
        BookDto bookDto = BookDto.fromDomainObject(bookList.get(0));
        given(bookService.findById(1)).willReturn(Optional.ofNullable(bookList.get(0)));

        this.mvc.perform(MockMvcRequestBuilders.get("/delete?id={id}", bookDto.getId()))
                .andExpect(status().isFound());

        verify(bookService).deleteById(idCaptor.capture());
        assertEquals(bookDto.getId(), idCaptor.getValue());
    }
}