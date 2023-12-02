package pl.lokalnylekarz.projekt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.lokalnylekarz.projekt.enumeration.Specialization;
import pl.lokalnylekarz.projekt.specialist.SpecialistControler;

import java.util.List;

@WebMvcTest(SpecialistControler.class)
public class SpecialistControllerApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetSpecialistsTypesEndpoint() throws Exception {
        List<Specialization> expectedSpecializations = List.of(Specialization.values());

        mockMvc.perform(MockMvcRequestBuilders.get("/specialists/types")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(expectedSpecializations.size()));
    }
}
