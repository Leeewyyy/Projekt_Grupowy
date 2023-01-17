package pl.lokalnylekarz.projekt.dataTypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class Address {
    protected String shop;
    protected String road;
    protected String neighbourhood;
    protected String suburb;
    protected String borough;
    protected String city;
    protected String ISO3166_2_lvl4;
    protected String postcode;
    protected String country;
    protected String country_code;
}
