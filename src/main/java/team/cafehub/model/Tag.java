package team.cafehub.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import team.cafehub.model.blogpost.BlogPost;

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
    private String nameEn;
    @Column(nullable = false, unique = true)
    private String slug; // скорочення
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TagCategory category;
    @ManyToMany(mappedBy = "tags")
    private List<BlogPost> blogPosts;
}
