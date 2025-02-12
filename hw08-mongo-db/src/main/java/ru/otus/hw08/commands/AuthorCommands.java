package ru.otus.hw08.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw08.converters.AuthorConverter;
import ru.otus.hw08.model.Author;
import ru.otus.hw08.service.AuthorService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class AuthorCommands {

    private final AuthorService authorService;

    private final AuthorConverter authorConverter;

    @ShellMethod(value = "Find all authors", key = "aa")
    public String findAllAuthors() {
        return authorService.findAll().stream()
                .map(authorConverter::authorToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Find author by id", key = "fa")
    public String findAuthorById(String id) {
        Author author = authorService.findById(id);
        return author == null ? "not found" : authorConverter.authorToString(author);
    }
}
