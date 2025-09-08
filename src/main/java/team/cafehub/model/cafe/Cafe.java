package team.cafehub.model.cafe;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

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
    private String name;
    @Column(unique = true)
    private String slug;
    @Column(nullable = false)
    private String address;
    private BigDecimal rating;
    private String image; // url
    @Column(nullable = false)
    private BigDecimal latitude; // Широта

    @Column(nullable = false)
    private BigDecimal longitude; // Довгота

    @ManyToMany
    @JoinTable(name = "cafe_tags",
            joinColumns = @JoinColumn(name = "cafe_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "cafe_metro_stations",
            joinColumns = @JoinColumn(name = "cafe_id"),
            inverseJoinColumns = @JoinColumn(name = "metro_station_id"))
    private Set<MetroStation> metroStations = new HashSet<>();
    @Column(nullable = false)
    private boolean isDeleted = false;
}
