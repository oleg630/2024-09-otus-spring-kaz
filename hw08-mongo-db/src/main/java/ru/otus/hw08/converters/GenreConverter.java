package ru.otus.hw08.converters;

import org.springframework.stereotype.Component;
import ru.otus.hw08.model.Genre;

@Component
public class GenreConverter {
    public String genreToString(Genre genre) {
        return "Id: %s, Name: %s".formatted(genre.getId(), genre.getName());
    }
}
