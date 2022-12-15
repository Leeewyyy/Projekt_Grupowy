package pl.lokalnylekarz.projekt.model;

import jakarta.persistence.*;
import pl.lokalnylekarz.projekt.enumeration.Specialization;

@Entity
@Table(name = "specialists")
public class Specialist {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private Specialization specialization;

    @ManyToOne
    @JoinColumn(name = "medical_facilities_id")
    private MedicalFacility medicalFacility;

    public Specialist(String firstname, String lastname, Specialization specialization, MedicalFacility medicalFacility) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.specialization = specialization;
        this.medicalFacility = medicalFacility;
    }

    public Specialist() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public MedicalFacility getMedicalFacility() {
        return medicalFacility;
    }

    public void setMedicalFacility(MedicalFacility medicalFacility) {
        this.medicalFacility = medicalFacility;
    }
}
