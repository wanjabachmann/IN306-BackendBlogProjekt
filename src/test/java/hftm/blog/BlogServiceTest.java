package hftm.blog;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.List;

import org.jboss.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import hftm.blog.control.BlogService;
import hftm.blog.control.dto.BlogDtos.AddBlogDto;
import hftm.blog.entity.Blog;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class BlogServiceTest {
    @Inject
    BlogService blogService;

    @Inject
    Logger logger;

    private static final String NEW_BLOG_TITLE = "Testing Title";
    private static final String NEW_BLOG_CONTENT = "This is my test content!";

    // Update Test
    private static final String UPDATE_BLOG_TITLE = "Testing Title Update";
    private static final String UPDATE_BLOG_CONTENT = "This is my updated test content!";

    private AddBlogDto blogDto;
    private List<Blog> blogs;
    int blogsBefore;
    int blogsAfterInit;

    @BeforeEach
    public void init() {
        blogsBefore = blogService.getBlogs().size();
        blogDto = new AddBlogDto(NEW_BLOG_TITLE, NEW_BLOG_CONTENT, new HashSet<>());
        blogService.addBlogDto(blogDto);
        blogs = blogService.getBlogs();
        blogsAfterInit = blogs.size();
    }

    @Order(1)
    @Test
    void testListingAndAddingBlogs() {

        // Act
        Blog lastBlog = blogs.get(blogs.size() - 1);

        // Assert
        assertEquals(blogsBefore + 1, blogs.size());
        assertEquals(lastBlog.getId(), blogs.get(blogs.size() - 1).getId());
        assertEquals(NEW_BLOG_TITLE, lastBlog.getTitle());
        assertEquals(NEW_BLOG_CONTENT, lastBlog.getContent());
    }

    @Order(2)
    @Test
    void testUpdateBlog() {
/*         // Arrange
        blogs = blogService.getBlogs();
        UpdateBlogDto blogDto = new UpdateBlogDto(1L, UPDATE_BLOG_TITLE, UPDATE_BLOG_CONTENT, new HashSet<>());

    
        // Act
        blogService.updateBlogDto(1L, blogDto);
    
        // Fetch the updated list of blogs after the update
        blogs = blogService.getBlogs();
    
        // Assert
        Blog updatedBlog = blogService.getBlogById(1L);
        assertEquals(UPDATE_BLOG_TITLE, updatedBlog.getTitle());
        assertEquals(UPDATE_BLOG_CONTENT, updatedBlog.getContent()); */
    
    }

    @Order(3)
    @Test
    void testRemoveBlog() {
        // Arrange
        Blog lastBlog = blogs.get(blogs.size() - 1);

        // Act
        blogService.removeBlog(lastBlog);

        // Assert
        assertEquals(blogsAfterInit - 1, blogService.getBlogs().size());
    }

    @Order(4)
    @Test
    void testRemoveBlogById() {
        // Arrange

        // Act
        blogService.removeBlogById(1L);

        // Assert
        assertEquals(blogsAfterInit - 1, blogService.getBlogs().size());
    }

    @Order(5)
    @Test
    void testFindBlogs() {
        // Arrange
        List<Blog> foundBlog = blogService.findBlogs("test content");

        // Act

        // Assert
        assert (foundBlog.size() > 0);
    }

}