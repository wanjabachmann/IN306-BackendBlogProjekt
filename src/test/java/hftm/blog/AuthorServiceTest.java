package hftm.blog;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hftm.blog.control.AuthorService;
import hftm.blog.control.dto.AuthorDtos;
import hftm.blog.entity.Author;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class AuthorServiceTest {
    @Inject
    AuthorService authorService;

    private static final String NEW_AUTHOR_FIRSTNAME = "Urs";
    private static final String NEW_AUTHOR_LASTNAME = "Meister";

    private static final String UPDATE_AUTHOR_FIRSTNAME = "Hans";
    private static final String UPDATE_AUTHOR_LASTNAME = "Muster";
    private List<Author> authors;
    private Author lastAuthor;

    @BeforeEach
    public void init() {
        AuthorDtos.AddAuthorDto addAuthorDto = new AuthorDtos.AddAuthorDto(NEW_AUTHOR_FIRSTNAME, NEW_AUTHOR_LASTNAME);
        authorService.addAuthorDto(addAuthorDto);
        authors = authorService.getAuthors();
    }

    @Test
    void testListingAndAddingAuthors() {
        // Arrange

        // Act

        Author lastAuthor = authors.get(authors.size() - 1);
        System.out.println(lastAuthor.getFirstname());

        // Assert
        assertEquals(NEW_AUTHOR_FIRSTNAME, lastAuthor.getFirstname());
        assertEquals(NEW_AUTHOR_LASTNAME, lastAuthor.getLastname());
        assertEquals(LocalDate.now(), lastAuthor.getCreationDate());
    }

    @Test
    void testUpdateAuthor() {
/*         // Arrange
        Author lastAuthor = authors.get(authors.size() - 1);
        UpdateAuthorDto authorDto = new UpdateAuthorDto(lastAuthor.getId(), UPDATE_AUTHOR_FIRSTNAME,
                UPDATE_AUTHOR_LASTNAME);

        // Act
        authorService.updateAuthorDto(lastAuthor.getId(), authorDto);

        // Assert
        assertEquals(UPDATE_AUTHOR_FIRSTNAME, lastAuthor.getFirstname());
        assertEquals(UPDATE_AUTHOR_FIRSTNAME, lastAuthor.getLastname()); */
    }

    @Test
    void deleteAuthor(){
        // Arrange
        Author lastAuthor = authors.get(authors.size() -1);
        int authorsBefore;

        // Act
        authorsBefore = authorService.getAuthors().size();
        authorService.removeAuthor(lastAuthor);

        // Assert
        assertEquals(authorsBefore -1, authorService.getAuthors().size());
    }
}
