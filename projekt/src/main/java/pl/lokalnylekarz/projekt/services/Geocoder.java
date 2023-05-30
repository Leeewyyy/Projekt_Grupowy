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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.stream.Stream;

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
        String nominatimUrl = "https://nominatim.openstreetmap.org/search?q=${address}&format=json&addressdetails=1&countrycodes=pl&dedupe=0&limit=${limit}";

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

        json = this.filterLocations(json);

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

    protected JSONArray filterLocations(JSONArray json) {
        JSONArray newJson = new JSONArray();

        for (Object o : json) {
            JSONObject jsonObject = (JSONObject) o;

            if (newJson.isEmpty()) {
                newJson.add(jsonObject);

                continue;
            }

            if (!this.isGeolocationDuplicated(newJson, jsonObject)) {
                newJson.add(jsonObject);
            }
        }

        return newJson;
    }

    protected boolean isGeolocationDuplicated(JSONArray jsonArray, JSONObject location) {
        String lat = (String) location.get("lat");
        String lon = (String) location.get("lon");

        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;

            if (jsonObject.get("lat").equals(lat) && jsonObject.get("lon").equals(lon)) {
                return true;
            }

            if (this.checkJsonObjectAttributes(jsonObject, location)) {
                return true;
            }
        }

        return false;
    }

    protected boolean checkJsonObjectAttributes(JSONObject jsonObject, JSONObject jsonObject2) {
        JSONObject location = (JSONObject) jsonObject2.get("address");
        JSONObject location2 = (JSONObject) jsonObject2.get("address");


        String shop = (String) location.get("shop");
        String road = (String) location.get("road");
        String neighbourhood = (String) location.get("neighbourhood");
        String suburb = (String) location.get("suburb");
        String borough = (String) location.get("borough");
        String city = (String) location.get("city");
        String iso = (String) location.get("ISO3166-2-lvl4");
        String postcode = (String) location.get("postcode");
        String country = (String) location.get("country");
        String country_code = (String) location.get("country_code");

        return
                (location2.get("shop") == shop &&
                        location2.get("road") == road &&
                        location2.get("neighbourhood") == neighbourhood &&
                        location2.get("suburb") == suburb &&
                        location2.get("borough") == borough &&
                        location2.get("city") == city &&
                        location2.get("ISO3166-2-lvl4") == iso &&
                        location2.get("postcode") == postcode &&
                        location2.get("country") == country &&
                        location2.get("country_code") == country_code)
                ||
                (location2.get("shop").equals(shop) &&
                        location2.get("road").equals(road) &&
                        location2.get("neighbourhood").equals(neighbourhood) &&
                        location2.get("suburb").equals(suburb) &&
                        location2.get("borough").equals(borough) &&
                        location2.get("city").equals(city) &&
                        location2.get("ISO3166-2-lvl4").equals(iso) &&
                        location2.get("postcode").equals(postcode) &&
                        location2.get("country").equals(country) &&
                        location2.get("country_code").equals(country_code));
    }
}
