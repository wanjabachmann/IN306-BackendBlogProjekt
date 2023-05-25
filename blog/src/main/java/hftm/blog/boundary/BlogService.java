package hftm.blog.boundary;

import java.util.List;
import org.jboss.logging.Logger;

import hftm.blog.control.BlogRepository;
import hftm.blog.entity.Blog;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Dependent
public class BlogService {
    @Inject
    BlogRepository blogRepository;

    @Inject
    Logger logger;

    public List<Blog> getBlogs() {
        var blogs = blogRepository.listAll();
        logger.info("Returning " + blogs.size() + " blogs");
        return blogs;
    }

    @Transactional
    public void addBlog(Blog blog) {
        logger.info("Adding blog " + blog.toString());
        blogRepository.persist(blog);
    }

    @Transactional
    public void removeBlog(Blog blog) {
        logger.info("Removing blog " + blog.toString());
        blogRepository.delete(blog);
    }
}