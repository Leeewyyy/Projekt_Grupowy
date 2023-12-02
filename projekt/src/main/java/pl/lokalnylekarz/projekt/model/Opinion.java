package pl.lokalnylekarz.projekt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private User addedBy;

    @Column
    private Integer rating;

    @Column
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private MedicalFacility medicalFacility;

    @Column
    @CreationTimestamp
    private Timestamp addedAt;
}
