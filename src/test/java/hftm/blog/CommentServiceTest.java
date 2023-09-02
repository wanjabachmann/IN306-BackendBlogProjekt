package hftm.blog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import hftm.blog.control.BlogService;
import hftm.blog.control.CommentService;
import hftm.blog.control.dto.BlogDtos.AddBlogDto;
import hftm.blog.control.dto.CommentDtos;
import hftm.blog.control.dto.CommentDtos.AddCommentDto;
import hftm.blog.entity.Comment;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
        var blogid = blogService.addBlogDto(blogDto);


        commentsBefore = commentService.getComments().size();
        commentDto = new CommentDtos.AddCommentDto(NEW_COMMENT_CONTENT, NEW_COMMENT_CREATOR);
        commentService.addCommentDto(blogid, commentDto);
        comments = commentService.getComments();
    }

    @Order(1)
    @Test
    public void testAddCommentToBlog() {
        // Assert
        Comment lastComment = comments.get(comments.size() -1);
        assertEquals(NEW_COMMENT_CONTENT, lastComment.getContent());
        assertEquals(NEW_COMMENT_CREATOR, lastComment.getCreator());
    }

/*     @Order(2)
    @Test
    public void testUpdateComment(){
        // Arange
        comments = commentService.getComments();
        UpdateCommentDto updateCommentDto = new UpdateCommentDto((long)commentService.getComments().size() -1, UPDATE_COMMENT_CONTENT, UPDATE_COMMENT_CREATOR);
        
        // Act
        commentService.updateCommentDto((long)commentService.getComments().size() -1, updateCommentDto);
        Comment lastComment = commentService.getComments().get(commentService.getComments().size() -1);

        // Assert
        assertEquals(UPDATE_COMMENT_CONTENT, lastComment.getContent());
    } */

    @Order(3)
    @Test
    public void testRemoveCommentById() {
        // Arrange
        int commentSizeBeforeRemoval = commentService.getComments().size();
        Comment lastComment = comments.get(comments.size() - 1);
        
        // Act
        commentService.removeCommentById(lastComment.getId());

        // Assert
        assertEquals(commentSizeBeforeRemoval, commentService.getComments().size());
    }

}
