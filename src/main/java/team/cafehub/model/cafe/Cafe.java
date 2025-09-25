package team.cafehub.model.cafe;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE cafes SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted=false")
@Table(name = "cafes")
public class Cafe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String excerpt;
    @Column(nullable = false)
    private String description; // text *
    @Column(nullable = false)
    private String name;
    @Column(unique = true, nullable = true)
    private String slug;
    @Column(nullable = false)
    private String address;
    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL)
    private List<Image> images; // url
    @Column(nullable = false)
    private BigDecimal latitude; // Широта

    @Column(nullable = false)
    private BigDecimal longitude; // Довгота

    @ManyToMany
    @JoinTable(name = "cafe_tags",
            joinColumns = @JoinColumn(name = "cafe_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    @Column(nullable = false)
    private boolean isDeleted = false;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date created;

    @UpdateTimestamp
    @Column(nullable = false)
    private Date updated;
    private String hours;
    private Integer views; // кількість кліків
    @OneToOne
    private User user;
}
