package hftm.blog;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hftm.blog.control.AuthorService;
import hftm.blog.control.BlogService;
import hftm.blog.entity.Author;
import hftm.blog.entity.Blog;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;


@QuarkusTest
public class AuthorBlogTest {
    @Inject
    BlogService blogService;

    @Inject
    AuthorService authorService;

    @Test
    void addAuthorToBlog(){
        // Arrange
        Blog blog = new Blog("addAuthorToBlog", "Blog from addAuthroToBlog Junit test.");
        Author author = new Author("Christoph", "Meier");
    
        // Act
        blog.getAuthors().add(author);

        // Assert
        // blog.getAuthors().forEach(x -> System.out.println(x.getFirstname() + " " + x.getLastname()));
        assertTrue(blog.getAuthors().contains(author));
    }
}
