package pl.lokalnylekarz.projekt.dataTypes;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class Location implements Serializable {
    private Double latitude;
    private Double longitude;

    public double distanceTo(Location that) {
        final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
        final double MILES_TO_KILOMETERS = 1.609344;

        double lat1 = Math.toRadians(this.latitude);
        double lon1 = Math.toRadians(this.longitude);
        double lat2 = Math.toRadians(that.latitude);
        double lon2 = Math.toRadians(that.longitude);

        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        double nauticalMiles = 60 * Math.toDegrees(angle);
        double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;

        return statuteMiles * MILES_TO_KILOMETERS;
    }
}
