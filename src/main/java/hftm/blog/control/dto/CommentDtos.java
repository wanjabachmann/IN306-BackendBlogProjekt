package hftm.blog.control.dto;

import jakarta.validation.Valid;

public class CommentDtos {
    
    @Valid
    public record AddCommentDto(String content, String creator){
    }

    @Valid
    public record UpdateCommentDto(Long id, String content, String creator){
        
    }
}
