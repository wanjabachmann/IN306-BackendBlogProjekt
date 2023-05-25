package hftm.blog.entity;

import java.util.HashSet;
import java.util.Set;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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

    private String firstname;
    private String lastname;

    // authors --> Name of the Hashset on Blog.java
    @ManyToMany(mappedBy = "authors")
    private Set<Blog> blogs = new HashSet<>();

    // Custom Constructor
    public Author(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }
    
}
