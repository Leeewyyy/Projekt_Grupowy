package pl.lokalnylekarz.projekt.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import pl.lokalnylekarz.projekt.enumeration.MedicalFacilityTypes;
import pl.lokalnylekarz.projekt.enumeration.NfzStatuses;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
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
    private String number;

    @Column
    private String website;

    @Column
    private String description;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private NfzStatuses nfzStatus;

    @Column
    private Timestamp openFrom;

    @Column
    private Timestamp openTo;

    @Column
    @CreationTimestamp
    private Date addedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User addedBy;

    public MedicalFacility(String name, MedicalFacilityTypes type, String number, String website, String description, NfzStatuses nfzStatus, Timestamp openFrom, Timestamp openTo, User addedBy) {
        this.name = name;
        this.type = type;
        this.number = number;
        this.website = website;
        this.description = description;
        this.nfzStatus = nfzStatus;
        this.openFrom = openFrom;
        this.openTo = openTo;
        this.addedBy = addedBy;
    }

    public MedicalFacility() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MedicalFacilityTypes getType() {
        return type;
    }

    public void setType(MedicalFacilityTypes type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public NfzStatuses getNfzStatus() {
        return nfzStatus;
    }

    public void setNfzStatus(NfzStatuses nfzStatus) {
        this.nfzStatus = nfzStatus;
    }

    public Timestamp getOpenFrom() {
        return openFrom;
    }

    public void setOpenFrom(Timestamp openFrom) {
        this.openFrom = openFrom;
    }

    public Timestamp getOpenTo() {
        return openTo;
    }

    public void setOpenTo(Timestamp openTo) {
        this.openTo = openTo;
    }

    public Date getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Date addedAt) {
        this.addedAt = addedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
