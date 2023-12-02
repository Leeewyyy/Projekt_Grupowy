package pl.lokalnylekarz.projekt;

import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import pl.lokalnylekarz.projekt.dataTransferObjects.GeocoderLocation;
import pl.lokalnylekarz.projekt.services.Geocoder;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GeocoderTests {

    @InjectMocks
    private Geocoder geocoder;

    @Test
    public void testGeocodeAddress_Success() throws IOException {
        String address = "Sample Address";
        int limit = 5;

        Geocoder geocoderSpy = Mockito.spy(geocoder);
        when(geocoderSpy.geocodeAddress(anyString(), anyInt())).thenReturn(new ArrayList<>());

        ArrayList<GeocoderLocation> result = geocoderSpy.geocodeAddress(address, limit);

        assertEquals(0, result.size());
    }
}
