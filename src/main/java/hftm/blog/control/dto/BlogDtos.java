package hftm.blog.control.dto;

import java.time.LocalDate;
import java.util.Set;

import hftm.blog.entity.Author;
import hftm.blog.entity.Blog;
import jakarta.validation.Valid;

public class BlogDtos {

    @Valid
    public record AddBlogDto(String title, String content, Set<Author> author) {
        public Blog toBlog() {
            return new Blog(title, content, LocalDate.now(), author);
        }

        public static AddBlogDto fromBlog(Blog blog) {
            return new AddBlogDto(blog.getTitle(), blog.getContent(), blog.getAuthors());
        }
    }

    @Valid
    public record UpdateBlogDto(Long id, String title, String content, Set<Author> author) {

    }
}
