package pl.lokalnylekarz.projekt.model;

import jakarta.persistence.*;
import lombok.*;
import pl.lokalnylekarz.projekt.enumeration.Specialization;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
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
}
