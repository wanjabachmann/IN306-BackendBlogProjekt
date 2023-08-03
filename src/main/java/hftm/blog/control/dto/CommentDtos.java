package hftm.blog.control.dto;

import java.time.LocalDate;

import hftm.blog.entity.Blog;
import hftm.blog.entity.Comment;
import jakarta.validation.Valid;

public class CommentDtos {
    
    @Valid
    public record AddCommentDto(String content, String creator, Blog blog){
        public Comment toComment(){
            return new Comment(content, LocalDate.now(), creator, blog);
        }
    }

    @Valid
    public record UpdateCommentDto(Long id, String content, String creator){
        
    }
}
