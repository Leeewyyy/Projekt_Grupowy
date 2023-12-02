package pl.lokalnylekarz.projekt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.lokalnylekarz.projekt.controllers.ajax.LocationAutocomplete;
import pl.lokalnylekarz.projekt.dataTransferObjects.GeocoderLocation;
import pl.lokalnylekarz.projekt.services.Geocoder;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class LocationAutocompleteTest {

    @Mock
    private Geocoder geocoder;

    @InjectMocks
    private LocationAutocomplete locationAutocomplete;

    @Test
    public void getLocation() throws Exception {
        // Arrange
        ArrayList<GeocoderLocation> geocoderLocations = new ArrayList<>();
        when(geocoder.geocodeAddress(anyString(), anyInt())).thenReturn(geocoderLocations);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(locationAutocomplete).build();

        mockMvc.perform(get("/ajax/locations?address=Grunwaldzka&limit=5")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void getLocationWithoutLimit() throws Exception {
        ArrayList<GeocoderLocation> geocoderLocations = new ArrayList<>(); // Add some sample data
        when(geocoder.geocodeAddress(anyString(), eq(10))).thenReturn(geocoderLocations);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(locationAutocomplete).build();

        mockMvc.perform(get("/ajax/locations")
                                .param("address", "Grunwaldzka 2")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isArray());
    }
}
