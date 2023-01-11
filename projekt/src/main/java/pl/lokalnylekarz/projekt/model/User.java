package pl.lokalnylekarz.projekt.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String login;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    @CreationTimestamp
    private Timestamp registrationDate;

    @ManyToMany(mappedBy = "addedBy")
    private List<Opinion> opinions;

    @OneToMany(mappedBy = "addedBy")
    private List<MedicalFacility> addedMedicalFacilities;

    @ManyToMany(mappedBy = "favoriteFor")
    private List<MedicalFacility> favoriteFacilities;
}
