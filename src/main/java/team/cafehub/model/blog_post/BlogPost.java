package team.cafehub.model.blog_post;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;
import team.cafehub.model.Image;
import team.cafehub.model.Tag;
import team.cafehub.model.user.User;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE blog_posts SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted=false")
@Table(name = "blog_posts")
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(length = 512)
    private String excerpt;
    @Column(nullable = false)
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToMany(mappedBy = "blogPost", cascade = CascadeType.ALL)
    private List<Image> images;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date created;
    @UpdateTimestamp
    @Column(nullable = false)
    private Date updated;
    @Column(nullable = false, unique = true)
    private String slug;
    @Column(nullable = false)
    private boolean isDeleted = false;
    @OneToOne
    private User user;
    private Integer views;
    @ManyToMany
    @JoinTable(
            name = "blog_post_tags",
            joinColumns = @JoinColumn(name = "blog_post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;
}
