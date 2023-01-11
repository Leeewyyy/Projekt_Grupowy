package pl.lokalnylekarz.projekt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    @JsonIgnore
    private String password;

    @Column
    @CreationTimestamp
    private Timestamp registrationDate;

    @ManyToMany(mappedBy = "addedBy")
    @JsonIgnore
    private List<Opinion> opinions;

    @OneToMany(mappedBy = "addedBy")
    @JsonIgnore
    private List<MedicalFacility> addedMedicalFacilities;

    @ManyToMany(mappedBy = "favoriteFor")
    @JsonIgnore
    private List<MedicalFacility> favoriteFacilities;


    public User(String login, String firstName, String lastName, String email, String password) {
        this.login = login;
        this.firstname = firstName;
        this.lastname = lastName;
        this.email = email;
        this.password = password;
        this.registrationDate = Timestamp.valueOf(LocalDateTime.now());
    }
}
