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
import jakarta.ws.rs.Path;

@Path("/blogs")
@ApplicationScoped
public class BlogResource {

    @Inject
    BlogService blogService;

    @Inject
    AuthorService authorService;

    // Diese Methode ist über eine http-GET-Anfrage erreichbar.
    @GET
    public List<Blog> getEntries() {
        return this.blogService.getBlogs();
    }

    // Diese Methode ist über eine http-POST-Anfrage erreichbar.
    @POST
    public void addBlog(Blog blog) {
        this.blogService.addBlog(blog);
    }

    @DELETE
    @Path("{id}")
    public void removeBlog(Long id){
        this.blogService.removeBlogById(id);
    }


    // Author Section
/*     @GET
    public List<Author> getAuthors() {
        return this.authorService.getAuthors();
    } */

/*     @POST
    public void addAuthor (Author author){
        this.authorService.addAuthor(author);
    } */
}
