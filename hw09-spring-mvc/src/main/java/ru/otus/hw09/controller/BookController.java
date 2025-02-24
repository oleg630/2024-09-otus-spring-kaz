package ru.otus.hw09.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw09.mapper.BookMapper;
import ru.otus.hw09.models.dto.BookDto;
import ru.otus.hw09.models.dto.BookUpdateDto;
import ru.otus.hw09.services.BookService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/")
    public String listBook(Model model) {
        List<BookDto> books = bookService.findAll();

        model.addAttribute("books", books);
        return "list";
    }

    @GetMapping("/edit")
    public String editBook(@RequestParam("id") long id, Model model) {
        BookUpdateDto book = BookMapper.toUpdateDto(bookService.findById(id));
        model.addAttribute("book", book);
        return "edit";
    }

    @PostMapping("/edit")
    public String saveBook(@Valid @ModelAttribute("book") BookUpdateDto book,
                           BindingResult bindingResult,
                           @RequestParam(value = "genresId", defaultValue = "") List<String> genresId
    ) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }

        bookService.update(book.getId(), book.getTitle(), book.getAuthorId(), book.getGenresId());
        return "redirect:/";
    }

    @GetMapping("/create")
    public String createBook(Model model) {
        model.addAttribute("book", new BookUpdateDto());
        return "create";
    }

    @PostMapping("/create")
    public String createBook(@Valid @ModelAttribute("book") BookUpdateDto book,
                           BindingResult bindingResult,
                           @RequestParam(value = "genresId", defaultValue = "") List<String> genresId
    ) {
        if (bindingResult.hasErrors()) {
            return "create";
        }

        bookService.insert(book.getTitle(), book.getAuthorId(), book.getGenresId());
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteBook(@RequestParam("id") long id) {
        bookService.deleteById(id);
        return "redirect:/";
    }
}
