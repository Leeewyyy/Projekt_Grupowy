package pl.lokalnylekarz.projekt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.web.SortDefault;
import pl.lokalnylekarz.projekt.dataTypes.Image;
import pl.lokalnylekarz.projekt.dataTypes.Location;
import pl.lokalnylekarz.projekt.enumeration.MedicalFacilityTypes;
import pl.lokalnylekarz.projekt.persistence.ImagesConverter;
import pl.lokalnylekarz.projekt.persistence.LocationConverter;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

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

    @Column(length = 1024)
    private String imageUrl;

    @Column(length = 1024)
    @Convert(converter = ImagesConverter.class)
    private List<Image> images;

    @Column
    private String phone;

    @Column
    private String websiteUrl;

    @Column(length = 1024)
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
    @JsonIgnore
    private User addedBy;

    @OneToMany(mappedBy = "medicalFacility")
    @JsonIgnore
    private List<Opinion> opinions;

    @ManyToMany
    @JsonIgnore
    private List<User> favoriteFor;
}
