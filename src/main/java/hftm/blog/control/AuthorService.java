package hftm.blog.control;

import java.util.List;
import org.jboss.logging.Logger;

import hftm.blog.entity.Author;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Dependent
public class AuthorService {
    @Inject
    AuthorRepository authorRepository;

    @Inject
    Logger logger;

    public List<Author> getAuthors() {
        var authors = authorRepository.listAll();
        logger.info("Returning " + authors.size() + " authors");
        return authors;
    }

    @Transactional
    public void addAuthor(Author author) {
        logger.info("Adding author " + author.getFirstname() + " " + author.getLastname());
        authorRepository.persist(author);
    }

    @Transactional
    public void removeAuthor(Author author) {
        logger.info("Remove author " + author.toString());
        authorRepository.delete(author);
    }

    @Transactional
    public void removeAuthorById(Long id) {
        logger.info("Try to remove author by Id: " + id);
        Author author = authorRepository.findById(id);
        if (author != null) {
            authorRepository.delete(author);
            logger.info("Removed authro by Id: " + id);
        }
    }
}
