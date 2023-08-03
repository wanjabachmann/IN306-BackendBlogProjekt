package hftm.blog.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Author {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    private LocalDate creationDate;

    // authors --> Name of the Hashset on Blog.java
    @ManyToMany(mappedBy = "authors")
    private Set<Blog> blogs = new HashSet<>();

    // Custom Constructor
    public Author(String firstname, String lastname, LocalDate creationDate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.creationDate = creationDate;
    }
    
}
