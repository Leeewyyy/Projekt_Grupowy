package pl.lokalnylekarz.projekt.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import pl.lokalnylekarz.projekt.enumeration.MedicalFacilityTypes;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    private Double lat;

    @Column
    private Double lng;

    @Column
    @CreationTimestamp
    private Date addedAt;

//    @ManyToOne
//    @JoinColumn(name = "specialist_id")
//    private Specialist specialist;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User addedBy;

}
