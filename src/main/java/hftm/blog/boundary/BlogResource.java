package hftm.blog.boundary;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

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
import jakarta.ws.rs.core.Response;

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
    @Tag(name = "Authors")
    @Path("authors")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Query successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Author.class))),
            @APIResponse(responseCode = "404", description = "No authors found")
    })
    public Response getAuthors(@QueryParam("search") String search) {
        List<Author> authors = null;

        if(search == null || search.isBlank()){
            authors = authorService.getAuthors();
        }else{
            authors = authorService.findAuthors(search);
        }

        if (authors != null && !authors.isEmpty()) {
            return Response.status(Response.Status.OK)
                    .entity(authors)
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No authors found")
                    .build();
        }
    }

    @POST
    @Tag(name = "Authors")
    @Path("authors")
    public void addAuthor(Author author) {
        this.authorService.addAuthor(author);
    }

    @GET
    @Tag(name = "Authors")
    @Path("authors/{id}")
    public Author getAuthorById(@PathParam("id") Long Id) {
        return this.authorService.getAuthorById(Id);
    }

    @PUT
    @Tag(name = "Authors")
    @Path("authors/{id}")
    public void updateAuthor(@PathParam("id") long id, Author updatedAuthor) {
        this.authorService.updateAuthor(id, updatedAuthor);
    }

    @DELETE
    @Tag(name = "Authors")
    @Path("authors/{id}")
    public void removeAuthor(@PathParam("id") Long id) {
        this.authorService.removeAuthorById(id);
    }

    /*
     * Blog Entries
     */
    @GET
    @Tag(name = "Blogs")
    @Path("blogs")
    public List<Blog> getEntries(@QueryParam("search") String search) {
        if(search == null || search.isBlank()){
            return this.blogService.getBlogs();
        }else{
            return this.blogService.findBlogs(search);
        }
        
    }

    @POST
    @Tag(name = "Blogs")
    @Path("blogs")
    public void addBlog(Blog blog) {
        this.blogService.addBlog(blog);
    }

    @GET
    @Tag(name = "Blogs")
    @Path("blogs/{id}")
    public Blog getBlog(@QueryParam("id") Long id) {
        return this.blogService.getBlogById(id);
    }

    @PUT
    @Tag(name = "Blogs")
    @Path("blogs/{id}")
    public void updateBlog(@PathParam("id") long id, Blog updatedBlog) {
        this.blogService.updateBlog(id, updatedBlog);
    }

    @DELETE
    @Tag(name = "Blogs")
    @Path("blogs/{id}")
    public void removeBlog(@PathParam("id") Long id) {
        this.blogService.removeBlogById(id);
    }
}
