package team.cafehub.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import team.cafehub.model.blogpost.BlogPost;
import team.cafehub.model.cafe.Cafe;

@Entity
@Table(name = "images")
@Getter
@Setter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String imageUrl;
    @Column(nullable = false)
    private String altText;
    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;
    @ManyToOne
    @JoinColumn(name = "blog_post_id")
    private BlogPost blogPost;
}
