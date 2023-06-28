package hftm.blog;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import hftm.blog.control.BlogService;
import hftm.blog.entity.Blog;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class BlogServiceTest {
    @Inject
    BlogService blogService;

    @Test
    void listingAndAddingBlogs() {
        // Arrange
        Blog blog = new Blog("Testing Blog", "This is my testing blog", LocalDate.of(2025, 05, 03));
        int blogsBefore;
        List<Blog> blogs;

        // Act
        blogsBefore = blogService.getBlogs().size();
        blogService.addBlog(blog);
        blogs = blogService.getBlogs();

        // Assert
        assertEquals(blogsBefore + 1, blogs.size());
        assertEquals(blog.getId(), blogs.get(blogs.size() - 1).getId());
    }

    @Test
    void deleteBlog(){
        // Arrange
        Blog blog = new Blog("Blog", "Content from the Blog", LocalDate.of(2025, 06, 03));
        int blogsBefore;
        blogService.addBlog(blog);

        // Act
        blogsBefore = blogService.getBlogs().size();
        blogService.removeBlog(blog);

        // Assert
        assertEquals(blogsBefore -1 , blogService.getBlogs().size());
    }
}