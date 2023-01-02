package pl.lokalnylekarz.projekt.persistence;

import jakarta.persistence.AttributeConverter;
import pl.lokalnylekarz.projekt.dataTypes.Location;

public class LocationConverter implements AttributeConverter<Location, String> {

    private static final String SEPARATOR = ", ";

    @Override
    public String convertToDatabaseColumn(Location location) {
        if (location == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        if (location.getLatitude() == null || location.getLongitude() == null) {
            return null;
        }

        return sb.append(location.getLatitude())
                .append(SEPARATOR)
                .append(location.getLongitude())
                .toString();
    }

    @Override
    public Location convertToEntityAttribute(String dbLocation) {
        if (dbLocation.isEmpty()) {
            return null;
        }

        String[] locationParts = dbLocation.split(SEPARATOR);

        if (locationParts.length != 2) {
            return null;
        }

        return Location.builder()
                .latitude(Double.valueOf(locationParts[0]))
                .longitude(Double.valueOf(locationParts[1]))
                .build();
    }
}
