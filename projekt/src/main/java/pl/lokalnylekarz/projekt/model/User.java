package pl.lokalnylekarz.projekt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import pl.lokalnylekarz.projekt.user.UserRoleEnum;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static pl.lokalnylekarz.projekt.user.UserRoleEnum.USER;

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
    private String fullName;

    @Column
    private String email;

    @Column
    private String image;

    @Column
    @JsonIgnore
    private String password;

    @Column
    @CreationTimestamp
    private Timestamp registrationDate;

    @OneToMany(mappedBy = "addedBy")
    @JsonIgnore
    private List<Opinion> opinions;

    @OneToMany(mappedBy = "addedBy")
    @JsonIgnore
    private List<MedicalFacility> addedMedicalFacilities;

    @ManyToMany(mappedBy = "favoriteFor")
    @JsonIgnore
    private List<MedicalFacility> favoriteFacilities;

    @Column
    private UserRoleEnum role;

    @Column
    private LocalDate verificationDate;

    public User(String fullName, String email, String password ) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.registrationDate = Timestamp.valueOf(LocalDateTime.now());
    }

    public User(String fullName, String email, String password, UserRoleEnum role) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.registrationDate = Timestamp.valueOf(LocalDateTime.now());
    }
}
