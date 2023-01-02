package pl.lokalnylekarz.projekt.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import pl.lokalnylekarz.projekt.enumeration.FacilityRatings;

import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "opinions")
public class Opinion {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User addedBy;

    @Enumerated(EnumType.ORDINAL)
    private FacilityRatings rating;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "medical_facilities_id")
    private MedicalFacility medicalFacility;

    @Column
    @CreationTimestamp
    private Timestamp addedAt;
}
