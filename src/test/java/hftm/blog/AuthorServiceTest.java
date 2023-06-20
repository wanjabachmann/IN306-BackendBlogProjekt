package hftm.blog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

import org.junit.jupiter.api.Test;

import hftm.blog.control.AuthorService;
import hftm.blog.entity.Author;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class AuthorServiceTest {
    @Inject
    AuthorService authorService;
    

    @Test
    void listingAndAddingAuthors() {
        // Arrange
        Author author = new Author("Hans", "Müller");
        int authorsBefore;
        List<Author> authors;

        // Act
        authorsBefore = authorService.getAuthors().size();
        authorService.addAuthor(author);
        authors = authorService.getAuthors();

        // Assert
        assertEquals(authorsBefore + 1, authors.size());
        assertEquals(author.getId(), authors.get(authors.size() - 1).getId());
    }

    @Test
    void deleteAuthor(){
        // Arrange
        Author author = new Author("Ueli", "Wagner");
        int authorsBefore;
        authorService.addAuthor(author);

        // Act
        authorsBefore = authorService.getAuthors().size();
        authorService.removeAuthor(author);

        // Assert
        assertEquals(authorsBefore -1, authorService.getAuthors().size());
    }
}
