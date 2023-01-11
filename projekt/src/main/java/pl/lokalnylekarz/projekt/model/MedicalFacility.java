package pl.lokalnylekarz.projekt.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import pl.lokalnylekarz.projekt.dataTypes.Location;
import pl.lokalnylekarz.projekt.enumeration.MedicalFacilityTypes;
import pl.lokalnylekarz.projekt.persistence.LocationConverter;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "medical_facilities")
public class MedicalFacility {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private MedicalFacilityTypes type;

    @Column
    private String address;

    @Column
    private String imageUrl;

    @Column
    private String phone;

    @Column
    private String websiteUrl;

    @Column
    private String description;

    @Column
    private Boolean isNFZ;

    @Column
    private Integer rating;

    @Column
    private Timestamp openFrom;

    @Column
    private Timestamp openTo;

    @Column
    @Convert(converter = LocationConverter.class)
    private Location location;

    @Column
    @CreationTimestamp
    private Date addedAt;

    @ManyToMany
    @JoinColumn(name = "id")
    private List<Specialist> specialist;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User addedBy;

    @ManyToMany(mappedBy = "medicalFacility")
    private List<Opinion> opinions;

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinTable(name = "favorite_facilities_for_users", joinColumns = @JoinColumn(name = "facility_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> favoriteFor;
}
