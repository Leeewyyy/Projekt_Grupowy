package pl.lokalnylekarz.projekt.controllers.ajax;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lokalnylekarz.projekt.api.Endpoint;
import pl.lokalnylekarz.projekt.dataTransferObjects.GeocoderLocation;
import pl.lokalnylekarz.projekt.services.Geocoder;

import java.util.ArrayList;

@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoint.LOCATION_AUTOCOMPLETE_BASE_ROUTE)
public class LocationAutocomplete {
    private final Geocoder geocoder;

    @GetMapping(produces = {"application/json; charset=utf-8"}, params = {"address", "limit"})
    @ResponseBody
    public ResponseEntity<ArrayList<GeocoderLocation>> getLocation(
            @RequestParam String address,
            @RequestParam int limit
    ) {
        if (limit > 10) limit = 10;
        return new ResponseEntity<>(geocoder.geocodeAddress(address, limit), HttpStatus.OK);

    }

    @GetMapping(produces = {"application/json; charset=utf-8"}, params = {"address"})
    @ResponseBody
    public ResponseEntity<ArrayList<GeocoderLocation>> getLocationWithoutLimit(@RequestParam String address) {
        return new ResponseEntity<>(geocoder.geocodeAddress(address, 10), HttpStatus.OK);

    }
}
