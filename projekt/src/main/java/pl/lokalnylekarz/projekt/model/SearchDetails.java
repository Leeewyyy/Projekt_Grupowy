package pl.lokalnylekarz.projekt.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "search_details")
public class SearchDetails {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "search_type")
    private String searchType;

    @Column(name = "`value`")
    private String value;

    @Column
    private int hits;
}
