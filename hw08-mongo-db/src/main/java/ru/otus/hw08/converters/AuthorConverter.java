package ru.otus.hw08.converters;

import org.springframework.stereotype.Component;
import ru.otus.hw08.model.Author;

@Component
public class AuthorConverter {
    public String authorToString(Author author) {
        return "Id: %s, FullName: %s".formatted(author.getId(), author.getFullName());
    }
}
