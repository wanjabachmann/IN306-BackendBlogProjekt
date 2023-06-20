package hftm.blog.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
    @GeneratedValue
    private Long id;

    private String title;
    private String content;

/*     @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "commend_id")
    private List<Comment> comments;
 */
    @ManyToMany
    @JoinTable(
        name = "Blog_Author",
        joinColumns = {@JoinColumn(name = "author_id")},
        inverseJoinColumns = {@JoinColumn(name = "blog_id")}
    )
    private Set<Author> authors = new HashSet<>();


    // Custom Constructor
    public Blog(String title, String content){
        this.title = title;
        this.content = content;
    }

}   
