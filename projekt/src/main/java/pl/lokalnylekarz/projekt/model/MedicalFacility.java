package pl.lokalnylekarz.projekt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import pl.lokalnylekarz.projekt.dataTypes.Image;
import pl.lokalnylekarz.projekt.dataTypes.Location;
import pl.lokalnylekarz.projekt.enumeration.MedicalFacilityTypes;
import pl.lokalnylekarz.projekt.enumeration.NfzStatuses;
import pl.lokalnylekarz.projekt.persistence.ImagesConverter;
import pl.lokalnylekarz.projekt.persistence.LocationConverter;

import java.time.LocalDate;
import java.time.LocalTime;
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
    @Enumerated(EnumType.STRING)
    private NfzStatuses nfzStatus;

    @Column
    private LocalTime openFrom;

    @Column
    private LocalTime openTo;

    @Column
    @Convert(converter = LocationConverter.class)
    private Location location;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDate addedAt;

    @ManyToMany
    @JoinColumn(name = "id")
    private List<Specialist> specialists;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User addedBy;

    @OneToMany(mappedBy = "medicalFacility", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Opinion> opinions;

    @ManyToMany
    @JsonIgnore
    private List<User> favoriteFor;
}
