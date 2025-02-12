package ru.otus.hw08.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.hw08.model.Author;
import ru.otus.hw08.model.Book;
import ru.otus.hw08.model.Comment;
import ru.otus.hw08.model.Genre;
import ru.otus.hw08.repository.AuthorRepository;
import ru.otus.hw08.repository.BookRepository;
import ru.otus.hw08.repository.CommentRepository;
import ru.otus.hw08.repository.GenreRepository;

import java.util.List;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private Author[] authors;

    private Genre[] genres;

    private Book[] books;

    @ChangeSet(order = "000", id = "dropDB", author = "stvort", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "stvort", runAlways = true)
    public void initAuthors(AuthorRepository repository) {
        authors = new Author[3];
        authors[0] = new Author("1", "Author_1");
        authors[1] = new Author("2", "Author_2");
        authors[2] = new Author("3", "Author_3");
        for (Author author : authors) {
            repository.save(author);
        }
    }

    @ChangeSet(order = "002", id = "initGenres", author = "stvort", runAlways = true)
    public void initGenres(GenreRepository repository) {
        genres = new Genre[6];
        genres[0] = new Genre("1", "Genre_1");
        genres[1] = new Genre("2", "Genre_2");
        genres[2] = new Genre("3", "Genre_3");
        genres[3] = new Genre("4", "Genre_4");
        genres[4] = new Genre("5", "Genre_5");
        genres[5] = new Genre("6", "Genre_6");
        for (Genre genre : genres) {
            repository.save(genre);
        }
    }

    @ChangeSet(order = "003", id = "initBooks", author = "stvort", runAlways = true)
    public void initBooks(BookRepository repository) {
        books = new Book[3];
        books[0] = new Book("1", "Book_1", authors[0], List.of(genres[0], genres[1]));
        books[1] = new Book("2", "Book_2", authors[1], List.of(genres[2], genres[3]));
        books[2] = new Book("3", "Book_3", authors[2], List.of(genres[4], genres[5]));
        for (Book book : books) {
            repository.save(book);
        }
    }

    @ChangeSet(order = "004", id = "initComments", author = "stvort", runAlways = true)
    public void initBooks(CommentRepository repository) {
        Comment[] comments = new Comment[1];
        comments[0] =  new Comment("1", books[0], "my comment 1");

        for (Comment comment : comments) {
            repository.save(comment);
        }
    }
}
