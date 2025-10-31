package team.cafehub.model.blogpost;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;
import team.cafehub.model.Image;
import team.cafehub.model.Tag;
import team.cafehub.model.user.User;

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
    private String titleEn;
    private String excerptEn;
    private String contentEn;
    @Column(length = 512)
    private String excerpt;
    @Column(nullable = false)
    private String content; // text*
    @ManyToMany
    @JoinTable(
            name = "blog_post_categories",
            joinColumns = @JoinColumn(name = "blog_post_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;
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
    private Set<Tag> tags;
}
