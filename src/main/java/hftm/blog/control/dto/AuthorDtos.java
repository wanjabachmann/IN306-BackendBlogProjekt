package hftm.blog.control.dto;

import java.time.LocalDate;

import hftm.blog.entity.Author;
import jakarta.validation.Valid;

public class AuthorDtos {

    @Valid
    public record AddAuthorDto(Long id, String firstname, String lastname) {
        public Author toAuthor() {
            return new Author(firstname, lastname, LocalDate.now());
        }
    }

    @Valid
    public record UpdateAuthorDto(Long id, String firstname, String lastname) {

    }
}
