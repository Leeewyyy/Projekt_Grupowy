package pl.lokalnylekarz.projekt.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import pl.lokalnylekarz.projekt.enumeration.FacilityRatings;

import java.sql.Timestamp;

@Entity
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

    public Opinion(User addedBy, FacilityRatings rating, String description, MedicalFacility medicalFacility) {
        this.addedBy = addedBy;
        this.rating = rating;
        this.description = description;
        this.medicalFacility = medicalFacility;
    }

    public Opinion() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(User addedBy) {
        this.addedBy = addedBy;
    }

    public FacilityRatings getRating() {
        return rating;
    }

    public void setRating(FacilityRatings rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MedicalFacility getMedicalFacility() {
        return medicalFacility;
    }

    public void setMedicalFacility(MedicalFacility medicalFacility) {
        this.medicalFacility = medicalFacility;
    }

    public Timestamp getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Timestamp addedAt) {
        this.addedAt = addedAt;
    }
}
