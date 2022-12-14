package pl.lokalnylekarz.projekt.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "placowka")
public class Placowka {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nazwa;
    private String nazwaMaszynowa;
    private String rodzajPlacowki;
    private String telefon;
    private String stronaWWW;
    private String opis;
    private String statusNFZ;
    private String godzinyOtwarcia;
    private String dataDodania;
//    private String idSpecjalisty;
}
