package hftm.blog.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @NotBlank
    @Size(min = 3, max = 30, message = "Title must be between 3 and 30 characters long")
    private String title;
    @NotBlank
    private String content;

    private LocalDate creationDate;

    /*
     * @OneToMany(cascade = CascadeType.ALL)
     * 
     * @JoinColumn(name = "commend_id")
     * private List<Comment> comments;
     */
    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Blog_Author", joinColumns = { @JoinColumn(name = "author_id") }, inverseJoinColumns = {
            @JoinColumn(name = "blog_id") })
    private Set<Author> authors = new HashSet<>();

    @OneToMany(mappedBy = "blog", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("blog") // Avoid serializing the 'comments' property
    private List<Comment> comments;

    // Custom Constructor
    public Blog(String title, String content, LocalDate creationDate, Set<Author> authors) {
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
        this.authors = authors;
    }

}
