package hftm.blog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import hftm.blog.control.BlogService;
import hftm.blog.control.CommentService;
import hftm.blog.control.dto.BlogDtos.AddBlogDto;
import hftm.blog.control.dto.CommentDtos;
import hftm.blog.control.dto.CommentDtos.AddCommentDto;
import hftm.blog.entity.Blog;
import hftm.blog.entity.Comment;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class CommentServiceTest {
    @Inject
    CommentService commentService;

    @Inject
    BlogService blogService;

    private static final String NEW_COMMENT_CONTENT = "Comment Content";
    private static final String NEW_COMMENT_CREATOR = "Wanja Bachmann";

    // Update Test
    private static final String UPDATE_COMMENT_CONTENT = "Updated Comment Content";
    private static final String UPDATE_COMMENT_CREATOR = "Thomas Mustermann";

    private static final String NEW_BLOG_TITLE = "Testing Title";
    private static final String NEW_BLOG_CONTENT = "This is my test content!";

    private AddBlogDto blogDto;
    int blogsBefore;
    int blogsAfterInit;


    private AddCommentDto commentDto;
    private List<Comment> comments;
    private int commentsBefore;

    @BeforeEach
    public void init() {
        blogsBefore = blogService.getBlogs().size();
        blogDto = new AddBlogDto(NEW_BLOG_TITLE, NEW_BLOG_CONTENT, new HashSet<>());
        blogService.addBlogDto(blogDto);


        commentsBefore = commentService.getComments().size();
        commentDto = new CommentDtos.AddCommentDto(NEW_COMMENT_CONTENT, NEW_COMMENT_CREATOR);
        commentService.addCommentDto(1L, commentDto);
        comments = commentService.getComments();
    }

    @Order(1)
    @Test
    public void testAddCommentToBlog() {
        // Assert
        Comment lastComment = comments.get(comments.size() - 1);
        assertEquals(NEW_COMMENT_CONTENT, lastComment.getContent());
        assertEquals(NEW_COMMENT_CREATOR, lastComment.getCreator());
    }

    @Order(2)
    @Test
    public void testUpdateComment(){
       /*  comments = commentService.getComments();
        UpdateCommentDto commentDto = new UpdateCommentDto(1L, NEW_COMMENT_CONTENT, NEW_COMMENT_CREATOR);
        commentService.updateCommentDto(1L, commentDto);
        Comment lastComment = commentService.getComments().get(1);
        assertEquals(UPDATE_COMMENT_CONTENT, lastComment.getContent()); */
    }

    @Order(3)
    @Test
    public void testRemoveCommentById() {
        // Arrange
        Comment lastComment = comments.get(comments.size() - 1);

        // Act
        commentService.removeCommentById(lastComment.getId());

        // Assert
        assertFalse(commentService.getComments().contains(lastComment));
    }

    /*
     * @Test
     * void addAuthorToBlog(){
     * // Arrange
     * Author author = new Author("Christoph", "Meier", LocalDate.now());
     * 
     * // Act
     * blog.getAuthors().add(author);
     * 
     * // Assert
     * // blog.getAuthors().forEach(x -> System.out.println(x.getFirstname() + " " +
     * x.getLastname()));
     * assertTrue(blog.getAuthors().contains(author));
     * }
     */
}
