package pl.lokalnylekarz.projekt.dataTransferObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.lokalnylekarz.projekt.dataTypes.Location;
import pl.lokalnylekarz.projekt.pojo.NominatimAddress;

@Data
@AllArgsConstructor
public class GeocoderLocation {
    protected NominatimAddress address;
    protected Location location;

}
