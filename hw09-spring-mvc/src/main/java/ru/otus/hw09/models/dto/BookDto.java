package ru.otus.hw09.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hw09.models.Book;

import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private long id;

    @NotBlank(message = "{title-field-should-not-be-blank}")
    @Size(min = 2, max = 50, message = "{title-field-should-have-expected-size}")
    private String title;

    private Long authorId;

    private String authorName;

    private Set<Long> genresId;

    private String genresNames;

    public String genresIdAsString() {
        if (isEmpty(genresId)) {
            return "";
        }
        return genresId.stream().map(g -> String.valueOf(g)).collect(Collectors.joining(", "));
    }

    public static BookDto fromDomainObject(Book book) {
        return new BookDto(book.getId(), book.getTitle(), book.getAuthor().getId(), book.getAuthor().getFullName(),
                book.getGenres().stream().map(g -> g.getId()).collect(Collectors.toSet()),
                book.getGenres().stream().map(g -> g.getName()).collect(Collectors.joining(", ")));
    }
}
