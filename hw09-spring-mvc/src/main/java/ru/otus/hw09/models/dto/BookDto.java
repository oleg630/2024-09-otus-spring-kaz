package ru.otus.hw09.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private long id;

    @NotBlank(message = "{title-field-should-not-be-blank}")
    @Size(min = 2, max = 50, message = "{title-field-should-have-expected-size}")
    private String title;

    private AuthorDto author;

    private List<Long> genresId;

    private String genresNames;

}
