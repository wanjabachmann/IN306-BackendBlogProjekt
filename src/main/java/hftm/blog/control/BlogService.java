package hftm.blog.control;

import java.util.List;
import org.jboss.logging.Logger;

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

    // Remove by blog id
    @Transactional
    public void removeBlogById(Long id) {
        logger.info("Try to remove Blog by Id: " + id);
        Blog blog = blogRepository.findById(id);
        if (blog != null) {
            blogRepository.delete(blog);
            logger.info("Removed Blog by Id: " + id);
        }
    }
}