package pl.lokalnylekarz.projekt.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.lokalnylekarz.projekt.dataTransferObjects.GeocoderLocation;
import pl.lokalnylekarz.projekt.dataTypes.Location;
import pl.lokalnylekarz.projekt.pojo.NominatimPlace;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class Geocoder {

    public ArrayList<GeocoderLocation> geocodeAddress(String address, int limit) {
        try {
            return this.get(address, limit);
        } catch (IOException $e) {
            return new ArrayList<>();
        }
    }

    public ArrayList<GeocoderLocation> get(
            String address,
            int limit
    ) throws UnsupportedEncodingException, MalformedURLException {

        HashMap<String, String> params = new HashMap<>();
        params.put("q", address);
        params.put("format", "json");
        params.put("limit", String.valueOf(limit));
        params.put("addressdetails", "1");
        params.put("countrycodes", "pl");

        String nominatimUrl = "https://nominatim.openstreetmap.org/search?q={q}&format={format}&limit={limit}&addressdetails={addressdetails}&countrycodes={countrycodes}";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("User-Agent", "ZnanyLekrza/1.0.0");
        headers.add("Referer", "http://www.znany-lekrza.pl");

        ResponseEntity<NominatimPlace[]> response = restTemplate.exchange(
                nominatimUrl,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                NominatimPlace[].class,
                params
        );

        return toGeocoderLocationDto(Arrays.asList(response.getBody()));
    }

    protected ArrayList<GeocoderLocation> toGeocoderLocationDto(List<NominatimPlace> response) {
        ArrayList<GeocoderLocation> locations = new ArrayList<>();

        for (NominatimPlace place : response) {
            locations.add(new GeocoderLocation(
                    place.getAddress(),
                    new Location(
                            Double.valueOf(place.getLat()),
                            Double.valueOf(place.getLon())
                    )
            ));
        }

        return locations;
    }
}
