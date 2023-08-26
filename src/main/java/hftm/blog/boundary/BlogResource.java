package hftm.blog.boundary;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import hftm.blog.control.AuthorService;
import hftm.blog.control.BlogService;
import hftm.blog.control.CommentService;
import hftm.blog.control.dto.BlogDtos;
import hftm.blog.control.dto.CommentDtos;
import hftm.blog.control.dto.AuthorDtos.AddAuthorDto;
import hftm.blog.control.dto.AuthorDtos.UpdateAuthorDto;
import hftm.blog.control.dto.BlogDtos.BlogOverviewDto;
import hftm.blog.control.dto.CommentDtos.AddCommentDto;
import hftm.blog.entity.Author;
import hftm.blog.entity.Blog;
import hftm.blog.entity.Comment;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.core.Response.Status;

@Path("/")
@ApplicationScoped
public class BlogResource {

    @Inject
    AuthorService authorService;

    @Inject
    BlogService blogService;

    @Inject
    CommentService commentService;

    @Inject
    SecurityIdentity securityIdentity;

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

        if (search == null || search.isBlank()) {
            authors = authorService.getAuthors();
        } else {
            authors = authorService.findAuthors(search);
        }

        if (authors != null && !authors.isEmpty()) {
            return Response.status(Response.Status.OK)
                    .entity(authors)
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No Authors found")
                    .build();
        }
    }

    @POST
    @Tag(name = "Authors")
    @Path("authors")
    @RolesAllowed("user")
    public Response addAuthor(@Valid AddAuthorDto authorDto, @Context UriInfo uriInfo) {
        var id = this.authorService.addAuthorDto(authorDto);

        // Status 201 created + Path to created resource
        var uri = uriInfo.getAbsolutePathBuilder().path(Long.toString((id))).build();
        return Response.created(uri).build();
    }

    @GET
    @Tag(name = "Authors")
    @Path("authors/{id}")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Author found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Author.class))),
            @APIResponse(responseCode = "404", description = "Author not found")
    })
    public Response getAuthorById(@PathParam("id") Long id) {
        Author author = this.authorService.getAuthorById(id);
        if (author == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Author not found")
                    .build();
        } else {
            return Response.status(Response.Status.OK)
                    .entity(author)
                    .build();
        }
    }

    @PUT
    @Tag(name = "Authors")
    @Path("authors/{id}")
    @RolesAllowed("user")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Author updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Author.class))),
            @APIResponse(responseCode = "404", description = "Author not found")
    })
    public Response updateAuthor(@PathParam("id") long id, @Valid UpdateAuthorDto updatedAuthorDto) {
        Author author = this.authorService.getAuthorById(id);

        // Check if Author exist before modify
        if (author == null) {
            return Response.status(Status.NOT_FOUND)
                    .entity("Author not found")
                    .build();
        } else {
            this.authorService.updateAuthorDto(id, updatedAuthorDto);
            return Response.status(Response.Status.OK)
                    .entity(author)
                    .build();
        }
    }

    @DELETE
    @Tag(name = "Authors")
    @Path("authors/{id}")
    @RolesAllowed("administrator")
    public Response removeAuthor(@PathParam("id") Long id) {
        Author author = this.authorService.getAuthorById(id);

        if (author == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Author not found")
                    .build();
        } else {
            this.authorService.removeAuthorById(id);

            return Response.status(Response.Status.NO_CONTENT)
                    .build();
        }
    }

    /*
     * Blog Entries
     */
    @GET
    @Tag(name = "Blogs")
    @Path("blogs")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Query successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Blog.class))),
            @APIResponse(responseCode = "404", description = "No Blogs found")
    })
    public Response getEntries(@QueryParam("search") String search) {
        List<BlogOverviewDto> blogs = null;

        if (search == null || search.isBlank()) {
            blogs = this.blogService.getBlogs();
        } else {
            blogs = this.blogService.findBlogs(search);
        }

        if (blogs != null && !blogs.isEmpty()) {
            return Response.status(Response.Status.OK)
                    .entity(blogs)
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No blogs found")
                    .build();
        }
    }

    @POST
    @Tag(name = "Blogs")
    @Path("blogs")
    @RolesAllowed("user")
    public Response addBlog(BlogDtos.AddBlogDto newBlogDto, @Context UriInfo uriInfo) {
        var id = this.blogService.addBlogDto(newBlogDto);

        // Status 201 created + Path to created resource
        var uri = uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build();
        return Response.created(uri).build();
    }

    @GET
    @Tag(name = "Blogs")
    @Path("blogs/{id}")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Blog found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Blog.class))),
            @APIResponse(responseCode = "404", description = "Blog not found")
    })
    public Response getBlog(@PathParam("id") Long id) {
        BlogOverviewDto blog = this.blogService.getBlogById(id);
        if (blog == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Blog not found")
                    .build();
        } else {
            return Response.status(Response.Status.OK)
                    .entity(blog)
                    .build();
        }
    }

    @PUT
    @Tag(name = "Blogs")
    @Path("blogs/{id}")
    @RolesAllowed("user")
    public Response updateBlog(@PathParam("id") long id, BlogDtos.UpdateBlogDto updatedBlogDto) {
        BlogOverviewDto blog = this.blogService.getBlogById(id);

        // Check if Blog exist before modify
        if (blog == null) {
            return Response.status(Status.NOT_FOUND)
                    .entity("Blog not found")
                    .build();
        } else {
            this.blogService.updateBlogDto(id, updatedBlogDto);
            return Response.status(Response.Status.OK)
                    .entity(blog)
                    .build();
        }
    }

    @DELETE
    @Tag(name = "Blogs")
    @Path("blogs/{id}")
    @RolesAllowed("user")
    @APIResponses(value = {
            @APIResponse(responseCode = "204", description = "Blog removed successfully"),
            @APIResponse(responseCode = "404", description = "Blog not found")
    })
    public Response removeBlog(@PathParam("id") Long id) {
        BlogOverviewDto blog = this.blogService.getBlogById(id);

        if (blog == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Blog not found")
                    .build();
        } else {
            this.blogService.removeBlogById(id);
            return Response.status(Response.Status.NO_CONTENT)
                    .build();
        }
    }

    /*
     * Comment Entries
     */
    @GET
    @Tag(name = "Comments")
    @Path("blogs/{id}/comments")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Query successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))),
            @APIResponse(responseCode = "404", description = "No comments found")
    })
    public Response getComments() {
        List<Comment> comments = commentService.getComments();

        if (comments != null && !comments.isEmpty()) {
            return Response.status(Response.Status.OK)
                    .entity(comments)
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No comments found")
                    .build();
        }
    }

    @POST
    @Tag(name = "Comments")
    @Path("blogs/{id}/comments")
    @Transactional
    public Response addComment(@PathParam("id") Long blogId, AddCommentDto commentDto, @Context UriInfo uriInfo) {
        BlogOverviewDto blog = blogService.getBlogById(blogId);
        if (blog != null) {
            var id = this.commentService.addCommentDto(blogId, commentDto);

            // Status 201 created + Path to created resource
            var uri = uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build();
            return Response.created(uri).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Tag(name = "Comments")
    @Path("blogs/comments/{id}")
    @RolesAllowed("administrator")
    public Response updateCommentDto(@PathParam("id") Long id, CommentDtos.UpdateCommentDto updatedCommentDto) {
        if (updatedCommentDto == null) {
            return Response.status(Status.BAD_REQUEST)
                    .entity("Updated Comment object cannot be null")
                    .build();
        } else {
            commentService.updateCommentDto(id, updatedCommentDto);

            return Response.status(Response.Status.OK)
                    .build();
        }
    }

    @DELETE
    @Tag(name = "Comments")
    @Path("blogs/comments/{id}")
    @RolesAllowed("administrator")
    @APIResponses(value = {
            @APIResponse(responseCode = "204", description = "Comment removed successfully"),
            @APIResponse(responseCode = "404", description = "Comment not found")
    })
    public Response removeComment(@PathParam("id") Long id) {
        if (id == null) {
            return Response.status(Status.BAD_REQUEST)
                    .entity("Comment ID cannot be null")
                    .build();
        } else {
            commentService.removeCommentById(id);

            return Response.status(Response.Status.NO_CONTENT)
                    .build();
        }
    }
}
