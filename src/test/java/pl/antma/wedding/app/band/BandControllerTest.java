package pl.antma.wedding.app.band;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.antma.wedding.app.ballroom.Ballroom;

import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BandController.class)
class BandControllerTest {

    @MockBean
    BandService bandService;

    @Autowired
    private MockMvc mockMvc;

    Band band1;

    @BeforeEach
    public void before(){

        Stream<Band> bandStream =  Stream.generate(Band::new).limit(10);
        band1 = new Band();
        band1.setName("Quadrans");

        when(bandService.findAll()).thenReturn(bandStream);
        when(bandService.addBand(any(Band.class))).then(i -> i.getArgument(0, Band.class));
        when(bandService.findBandById(eq(1L))).thenReturn(Optional.of(band1));
        when(bandService.findBandById(eq(2L))).thenReturn(Optional.empty());
        when(bandService.deleteBand(eq(1L))).thenReturn(Optional.of(band1));
        when(bandService.deleteBand(eq(2L))).thenReturn(Optional.empty());
        when(bandService.updateBand(any(Band.class), eq(1L))).then(i -> Optional.of(i.getArgument(0, Ballroom.class)));
        when(bandService.updateBand(any(Band.class), eq(2L))).thenReturn(Optional.empty());
    }

    @Test
    @DisplayName("GET /bands should return the same number of results as service")
    void testGetBand() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bands"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(10)));
    }

    @Test
    @DisplayName("GET /bands/1 should return one band")
    void testGetSpecificBallroom() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bands/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Quadrans"));
    }

    @Test
    @DisplayName("POST /bands should return created band")
    void testPostBallroom() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/bands")
                .content(asJsonString(band1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Quadrans"));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
