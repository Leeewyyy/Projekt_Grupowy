package pl.lokalnylekarz.projekt.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NominatimAddress {
    private String house_number;
    private String road;
    private String quarter;
    private String suburb;
    private String city_district;
    private String city;
    private String state;
    private String ISO3166_2_lvl4;
    private String postcode;
    private String country;
    private String country_code;
}
