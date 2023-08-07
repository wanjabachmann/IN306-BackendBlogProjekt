package hftm.blog.control;

import java.util.List;
import org.jboss.logging.Logger;

import hftm.blog.control.dto.BlogDtos.AddBlogDto;
import hftm.blog.control.dto.BlogDtos.UpdateBlogDto;
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

    public List<Blog> findBlogs(String searchString) {
        var blogs = blogRepository.find("title like ?1 or content like ?1", "%" + searchString + "%").list();
        logger.info("Found " + blogs.size() + " blogs");
        return blogs;
    }

    public Blog getBlogById(Long id) {
        var blog = blogRepository.findById(id);
        return blog;
    }

    @Transactional
    public long addBlogDto(AddBlogDto blogDto) {
        logger.info("Adding blog " + blogDto.title());
        var blog = blogDto.toBlog();
        blogRepository.persist(blog);
        return blog.getId();
    }

    @Transactional
    public void removeBlog(Blog blog) {
        logger.info("Removing blog " + blog.toString());
        blogRepository.delete(blog);
    }

    @Transactional
    public void updateBlogDto(Long Id, UpdateBlogDto updatedBlogDto) {
        logger.info("Update blog " + Id);

        Blog blog = blogRepository.findById(Id);

        if (blog != null) {
            if (updatedBlogDto.title() != null) {
                blog.setTitle(updatedBlogDto.title());
            }
            if (updatedBlogDto.content() != null) {
                blog.setContent(updatedBlogDto.content());
            }
            if (updatedBlogDto.author() != null) {
                blog.setAuthors(updatedBlogDto.author());
            }
            blogRepository.persist(blog);
        } else {
            logger.error("Blog not found");
        }
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