package hftm.blog.boundary;

import java.util.List;

import hftm.blog.control.AuthorService;
import hftm.blog.control.BlogService;
import hftm.blog.entity.Author;
import hftm.blog.entity.Blog;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;

@Path("/")
@ApplicationScoped
public class BlogResource {

    @Inject
    AuthorService authorService;

    @Inject
    BlogService blogService;

    /*
     * Author Entries
     */
    @GET
    @Path("authors")
    public List<Author> getAuthors() {
        return this.authorService.getAuthors();
    }

    @POST
    @Path("authors")
    public void addAuthor(Author author) {
        this.authorService.addAuthor(author);
    }

    @DELETE
    @Path("authors/{id}")
    public void removeAuthor(Long id) {
        this.authorService.removeAuthorById(id);
    }

    /*
     * Blog Entries
     */
    @GET
    @Path("blogs")
    public List<Blog> getEntries(@QueryParam("name") String search) {
        return this.blogService.getBlogs();
    }

    @POST
    @Path("blogs")
    public void addBlog(Blog blog) {
        this.blogService.addBlog(blog);
    }

    @GET
    @Path("blogs/{id}")
    public Blog getBlog(Long id) {
        return this.blogService.getBlogById(id);
    }

    @PUT
    @Path("blogs/{id}")
    public void updateBlog(@PathParam("id") long id, Blog updatedBlog) {
        this.blogService.updateBlog(id, updatedBlog);
    }

    @DELETE
    @Path("blogs/{id}")
    public void removeBlog(Long id) {
        this.blogService.removeBlogById(id);
    }
}
