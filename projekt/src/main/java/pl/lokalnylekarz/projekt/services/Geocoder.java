package pl.lokalnylekarz.projekt.services;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import pl.lokalnylekarz.projekt.dataTransferObjects.GeocoderLocation;
import pl.lokalnylekarz.projekt.dataTypes.Address;
import pl.lokalnylekarz.projekt.dataTypes.Location;
import pl.lokalnylekarz.projekt.user.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class Geocoder {

    public ArrayList<GeocoderLocation> geocodeAddress(String address, int limit) {
        try {
            return this.get(address, limit);
        } catch (ParseException | IOException $e) {
            return new ArrayList<>();
        }
    }

    protected ArrayList<GeocoderLocation> get(String address, int limit) throws IOException, ParseException {
        String nominatimUrl = "https://nominatim.openstreetmap.org/search?q=${address}&format=json&addressdetails=1&countrycodes=pl&limit=${limit}";

        URL url = new URL(nominatimUrl
                                  .replace("${address}", URLEncoder.encode(address, StandardCharsets.UTF_8))
                                  .replace("${limit}", URLEncoder.encode(String.valueOf(limit), StandardCharsets.UTF_8))
        );

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        if (connection.getResponseCode() != 200) {
            return null;
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));

        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        return this.formatResponse(response);
    }

    protected ArrayList<GeocoderLocation> formatResponse(StringBuilder responseBuilder) throws ParseException {
        JSONParser parser = new JSONParser();

        JSONArray json = (JSONArray) parser.parse(responseBuilder.toString());

        if (json.size() == 0) {
            return null;
        }

        return this.toGeocoderLocationDto(json);
    }

    protected ArrayList<GeocoderLocation> toGeocoderLocationDto(JSONArray json) {
        ArrayList<GeocoderLocation> locations = new ArrayList<>();

        for (Object obj : json) {
            JSONObject jsonObject = (JSONObject) obj;

            JSONObject address = (JSONObject) jsonObject.get("address");

            locations.add(new GeocoderLocation(
                    new Address(
                            (String) address.get("shop"),
                            (String) address.get("road"),
                            (String) address.get("neighbourhood"),
                            (String) address.get("suburb"),
                            (String) address.get("borough"),
                            (String) address.get("city"),
                            (String) address.get("ISO3166-2-lvl4"),
                            (String) address.get("postcode"),
                            (String) address.get("country"),
                            (String) address.get("country_code")
                    ),
                    new Location(
                            Double.valueOf((String) jsonObject.get("lat")),
                            Double.valueOf((String) jsonObject.get("lon"))
                    )
            ));
        }

        return locations;
    }
}
