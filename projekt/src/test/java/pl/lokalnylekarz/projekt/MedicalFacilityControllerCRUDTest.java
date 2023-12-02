package pl.lokalnylekarz.projekt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.lokalnylekarz.projekt.medicalFacility.MedicalFacilityForRegisterDto;
import pl.lokalnylekarz.projekt.medicalFacility.MedicalFacilityListDto;
import pl.lokalnylekarz.projekt.medicalFacility.MedicalFacilityService;

import java.time.LocalTime;
import java.util.ArrayList;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class MedicalFacilityControllerCRUDTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MedicalFacilityService service;

    @Test
    public void testGetMedicalFacilities() throws Exception {

        Mockito.when(service.getAll()).thenReturn(new ArrayList<MedicalFacilityListDto>());
        mockMvc.perform(MockMvcRequestBuilders.get("/medical-facilities"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetMedicalFacilityDetails() throws Exception {
        Long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get("/medical-facilities/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetMedicalFacilityTypes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/medical-facilities/types")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCreateMedicalFacility() throws Exception {
        MedicalFacilityForRegisterDto facilityDto = new MedicalFacilityForRegisterDto(
                "Name", "SZPITAL", "Address", "Phone", "website", "Descriptioin", "PARTIAL",
                LocalTime.now(), LocalTime.now(), 1d, 2d, 1l, "iMG"
        );
        mockMvc.perform(MockMvcRequestBuilders.post("/medical-facilities")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(facilityDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testEditMedicalFacility() throws Exception {
        Long id = 1L;
        MedicalFacilityForRegisterDto facilityDto = new MedicalFacilityForRegisterDto(
                "Name", "SZPITAL", "Address", "Phone", "website", "Descriptioin", "PARTIAL",
                LocalTime.now(), LocalTime.now(), 1d, 2d, 1l, "iMG"
        );
        mockMvc.perform(MockMvcRequestBuilders.patch("/medical-facilities/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(facilityDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteMedicalFacility() throws Exception {
        Long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/medical-facilities/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
