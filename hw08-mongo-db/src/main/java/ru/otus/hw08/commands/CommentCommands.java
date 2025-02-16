package ru.otus.hw08.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw08.converters.BookConverter;
import ru.otus.hw08.model.Comment;
import ru.otus.hw08.service.CommentService;

import java.util.stream.Collectors;

@SuppressWarnings({"unused"})
@RequiredArgsConstructor
@ShellComponent
public class CommentCommands {

    private final CommentService commentService;

    private final BookConverter bookConverter;

    @ShellMethod(value = "Find comment by book id", key = "fc")
    public String findCommentById(String bookId) {
        return commentService.findByBookId(bookId).stream()
                .map(Comment::getText)
                .collect(Collectors.joining(", "));
    }

    @ShellMethod(value = "Insert comment", key = "ci")
    public String insertComment(String id, String text) {
        return "Comment id: " + commentService.insert(id, text).getId();
    }

    @ShellMethod(value = "Update comment", key = "cu")
    public void updateComment(String id, String text) {
        commentService.update(id, text);
    }

    @ShellMethod(value = "Delete comment by id", key = "cd")
    public void deleteComment(String id) {
        commentService.deleteById(id);
    }

}
