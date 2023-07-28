package hftm.blog.control;

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
    public void addComment(Comment comment) {
        logger.info("Adding comment: " + comment.toString());
        comment.setCreationDate(LocalDate.now());
        commentRepository.persist(comment);
    }

    @Transactional
    public void updateComment(Long id, Comment updatedComment) {
        Comment comment = commentRepository.findById(id);
        if (comment != null) {
            logger.info("Update Comment: " + id);
            comment.setContent(updatedComment.getContent());
            commentRepository.persist(comment);
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
