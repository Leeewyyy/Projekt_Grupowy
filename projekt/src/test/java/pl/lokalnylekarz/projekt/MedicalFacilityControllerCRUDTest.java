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
import org.springframework.web.multipart.MultipartFile;
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
    public void testDeleteMedicalFacility() throws Exception {
        Long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/medical-facilities/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
