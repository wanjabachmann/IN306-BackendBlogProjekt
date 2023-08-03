package hftm.blog.entity;

import java.time.LocalDate;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(min = 10, max = 100, message = "Comment must be between 10 and 100 characters long")
    private String content;

    @NotBlank
    private String creator;
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "BLOG_COMMENT")
    @JsonbTransient // Avoid serializing the 'comments' property
    private Blog blog;

    public Comment(String content, LocalDate creationDate, String creator, Blog blog) {
        this.content = content;
        this.creationDate = creationDate;
        this.creator = creator;
        this.blog = blog;
    }
}
