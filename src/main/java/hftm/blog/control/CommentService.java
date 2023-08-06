package hftm.blog.control;

import hftm.blog.control.dto.CommentDtos.AddCommentDto;
import hftm.blog.control.dto.CommentDtos.UpdateCommentDto;
import hftm.blog.entity.Blog;
import hftm.blog.entity.Comment;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

import org.jboss.logging.Logger;

@ApplicationScoped
public class CommentService {

    @Inject
    CommentRepository commentRepository;

    @Inject
    BlogRepository blogRepository;

    @Inject
    Logger logger;

    public List<Comment> getComments() {
        var comments = commentRepository.listAll();
        logger.info("Returning " + comments.size() + " comments");
        return comments;
    }

    public Comment getCommentById(Long id) {
        logger.info("Found Blog with id: " + id);
        return commentRepository.findById(id);
    }

    @Transactional
    public void addCommentDto(Long blogId, AddCommentDto commentDto) {
        logger.info("Adding comment: " + commentDto.toString());
        Blog blog = blogRepository.findById(blogId);
        if (blog != null) {
            Comment comment = new Comment();
            comment.setContent(commentDto.content());
            comment.setCreationDate(LocalDate.now());
            comment.setCreator(commentDto.creator());
            comment.setBlog(blog);
            commentRepository.persist(comment);
        } else {
            logger.error("Blog not found");
        }
    }

    @Transactional
    public void updateCommentDto(Long id, UpdateCommentDto updatedCommentDto) {
        Comment comment = commentRepository.findById(id);
        if (comment != null) {
            logger.info("Update Comment: " + id);
            logger.info("Before Update - Content: " + comment.getContent());
            logger.info("Before Update - Creator: " + comment.getCreator());
            comment.setContent(updatedCommentDto.content());
            comment.setCreator(updatedCommentDto.creator());
            commentRepository.persist(comment);
            logger.info("After Update - Content: " + comment.getContent());
            logger.info("After Update - Creator: " + comment.getCreator());
        } else {
            logger.error("Comment not found");
        }
    }

    @Transactional
    public void removeCommentById(Long id) {
        logger.info("Remove Comment by id: " + id );
        commentRepository.deleteById(id);
    }
}
