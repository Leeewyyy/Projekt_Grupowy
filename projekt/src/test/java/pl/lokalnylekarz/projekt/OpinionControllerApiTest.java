package pl.lokalnylekarz.projekt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.lokalnylekarz.projekt.model.Opinion;
import pl.lokalnylekarz.projekt.opinion.OpinionController;
import pl.lokalnylekarz.projekt.opinion.OpinionDto;
import pl.lokalnylekarz.projekt.opinion.OpinionFromFrontDto;
import pl.lokalnylekarz.projekt.opinion.OpinionService;

import java.sql.Timestamp;
import java.util.Collections;

@WebMvcTest(OpinionController.class)
public class OpinionControllerApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OpinionService opinionService;

    @Test
    public void testAddOpinionEndpoint() throws Exception {
        Long userId = 1L;
        Long facilityId = 2L;
        OpinionFromFrontDto opinionFromFrontDto = new OpinionFromFrontDto(1, "Rating");
        opinionFromFrontDto.setRating(5);
        opinionFromFrontDto.setText("Great service");

        Mockito.when(opinionService.save(Mockito.anyLong(), Mockito.anyLong(), Mockito.any(OpinionFromFrontDto.class)))
                .thenReturn(new Opinion());

        mockMvc.perform(MockMvcRequestBuilders.post("/opinions/add/{userId}/{facilityId}", userId, facilityId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(opinionFromFrontDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testAddOpinionEndpoint_NotAcceptable() throws Exception {
        Long userId = 1L;
        Long facilityId = 2L;
        OpinionFromFrontDto opinionFromFrontDto = new OpinionFromFrontDto(1, null);

        mockMvc.perform(MockMvcRequestBuilders.post("/opinions/add/{userId}/{facilityId}", userId, facilityId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(opinionFromFrontDto)))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }

    @Test
    public void testGetAllOpinionsEndpoint() throws Exception {
        Mockito.when(opinionService.getAll()).thenReturn(Collections.singletonList(new OpinionDto(1L, 1, "Description", new Timestamp(1701534062))));

        mockMvc.perform(MockMvcRequestBuilders.get("/opinions"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1));
    }
}
