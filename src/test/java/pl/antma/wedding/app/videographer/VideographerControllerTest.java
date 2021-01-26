package pl.antma.wedding.app.videographer;

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
import pl.antma.wedding.app.todo.Todo;
import pl.antma.wedding.app.viedographer.Videographer;
import pl.antma.wedding.app.viedographer.VideographerController;
import pl.antma.wedding.app.viedographer.VideographerService;

import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(VideographerController.class)
class VideographerControllerTest {
    @MockBean
    VideographerService videographerService;

    @Autowired
    private MockMvc mockMvc;

    Videographer videographer1;

    @BeforeEach
    public void before() {

        Stream<Videographer> videographerStream = Stream.generate(Videographer::new).limit(10);
        videographer1 = new Videographer();
        videographer1.setName("Spoko studio");

        when(videographerService.findAll()).thenReturn(videographerStream);
        when(videographerService.addVideographer(any(Videographer.class))).then(i -> i.getArgument(0, Videographer.class));
        when(videographerService.findVideographerById(eq(1L))).thenReturn(Optional.of(videographer1));
        when(videographerService.findVideographerById(eq(2L))).thenReturn(Optional.empty());
        when(videographerService.deleteVideographer(eq(1L))).thenReturn(Optional.of(videographer1));
        when(videographerService.deleteVideographer(eq(2L))).thenReturn(Optional.empty());
        when(videographerService.updateVideographer(any(Videographer.class), eq(1L))).then(i -> Optional.of(i.getArgument(0, Videographer.class)));
        when(videographerService.updateVideographer(any(Videographer.class), eq(2L))).thenReturn(Optional.empty());
    }

    @Test
    @DisplayName("GET /videographers should return the same number of results as service")
    void testGetVideographers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/videographers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(10)));
    }

    @Test
    @DisplayName("GET /videographers/1 should return one videographer")
    void testGetSpecificVideographer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/videographers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Spoko studio"));
    }

    @Test
    @DisplayName("POST /videographers should return created videographer")
    void testPostVideographer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/videographers")
                .content(asJsonString(videographer1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Spoko studio"));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
