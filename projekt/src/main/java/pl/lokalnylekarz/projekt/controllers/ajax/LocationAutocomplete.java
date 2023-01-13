package pl.lokalnylekarz.projekt.controllers.ajax;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.lokalnylekarz.projekt.api.Endpoint;
import pl.lokalnylekarz.projekt.services.Geocoder;

import java.util.ArrayList;

@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoint.LOCATION_AUTOCOMPLETE_BASE_ROUTE)
public class LocationAutocomplete {
    private final Geocoder geocoder;

    @GetMapping()
    @ResponseBody
    public ArrayList getLocation(@RequestParam String address, @RequestParam int limit) {
        return geocoder.geocodeAddress(address, limit);
    }
}
