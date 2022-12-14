package pl.lokalnylekarz.projekt.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "opinie")
public class Opinia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long idUzytkownika;
    private String ocena;
    private String opis;
    private Long idPlacowki;
}
