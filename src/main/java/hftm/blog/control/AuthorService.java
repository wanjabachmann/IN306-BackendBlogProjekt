package hftm.blog.control;

import java.util.List;
import org.jboss.logging.Logger;

import hftm.blog.control.dto.AuthorDtos.AddAuthorDto;
import hftm.blog.control.dto.AuthorDtos.UpdateAuthorDto;
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

    public List<Author> findAuthors(String searchString){
        var authors = authorRepository.find("firstname like ?1 or lastname like ?1", "%" + searchString + "%").list();
        logger.info("Found " + authors.size() + " blogs");
        return authors;
    }

    public Author getAuthorById(Long id) {
        var author = authorRepository.findById(id);
        logger.info("Returning " + id);
        return author;
    }

    @Transactional
    public void addAuthor(AddAuthorDto authorDto) {
        logger.info("Adding author " + authorDto.firstname() + " " + authorDto.lastname());
        var author = authorDto.toAuthor();
        authorRepository.persist(author);
    }

    @Transactional
    public void removeAuthor(Author author) {
        logger.info("Remove author " + author.toString());
        authorRepository.delete(author);
    }

    @Transactional
    public void updateAuthor(long id, UpdateAuthorDto updatedAuthorDto) {
        logger.info("Update author " + id);
        
        Author author = authorRepository.findById(id);

        if(author != null){
            author.setFirstname(updatedAuthorDto.firstname());
            author.setLastname(updatedAuthorDto.lastname());
        } else {
            logger.error("Author not found");
        }
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
