package pl.lokalnylekarz.projekt.dataTransferObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.lokalnylekarz.projekt.dataTypes.Address;
import pl.lokalnylekarz.projekt.dataTypes.Location;

@Data
@AllArgsConstructor
public class GeocoderLocation {
    protected Address address;
    protected Location location;

}
