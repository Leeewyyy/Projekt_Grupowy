package pl.lokalnylekarz.projekt.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NominatimPlace {
    private long place_id;
    private String licence;
    private String osm_type;
    private long osm_id;
    private String lat;
    private String lon;
    private String classType;
    private String type;
    private int place_rank;
    private double importance;
    private String addresstype;
    private String name;
    private String display_name;
    private NominatimAddress address;
    private List<String> boundingbox;
}
