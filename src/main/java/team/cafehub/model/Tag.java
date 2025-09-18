package team.cafehub.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import team.cafehub.model.blog_post.BlogPost;

import java.util.List;

@Entity
@Table(name = "tags")
@Getter
@Setter
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String slug; // скорочення
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TagCategory category;
    @ManyToMany(mappedBy = "tags")
    private List<BlogPost> blogPosts;
}
