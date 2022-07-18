package pl.antma.wedding.app.ballroom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BallroomController.class)
class BallroomControllerTest {

    @MockBean
    BallroomService ballroomService;

    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Autowired
    private MockMvc mockMvc;

    Ballroom ballroom1;

    @BeforeEach
    void before() {

        Stream<Ballroom> ballroomStream = Stream.generate(Ballroom::new).limit(10);
        Set<LocalDate> dates = new HashSet(Set.of(
                LocalDate.of(2021, 5, 22),
                LocalDate.of(2021, 6, 26)));
        ballroom1 = new Ballroom();
        ballroom1.setName("Riverside");
        ballroom1.setCity("Włocławek");
        ballroom1.setDistance(0d);
        ballroom1.setAvailableDates(dates);

        when(ballroomService.findAll(0, 20)).thenReturn(ballroomStream);
        when(ballroomService.addBallroom(any(Ballroom.class))).then(i -> i.getArgument(0, Ballroom.class));
        when(ballroomService.findBallroomById(eq(1L))).thenReturn(Optional.of(ballroom1));
        when(ballroomService.findBallroomById(eq(2L))).thenReturn(Optional.empty());
        when(ballroomService.deleteBallroom(eq(1L))).thenReturn(Optional.of(ballroom1));
        when(ballroomService.deleteBallroom(eq(2L))).thenReturn(Optional.empty());
        when(ballroomService.updateBallroom(any(Ballroom.class), eq(1L))).then(i -> Optional.of(i.getArgument(0, Ballroom.class)));
        when(ballroomService.updateBallroom(any(Ballroom.class), eq(2L))).thenReturn(Optional.empty());
    }

    @Test
    @DisplayName("GET /ballrooms should return the same number of results as service")
    void testGetBallrooms() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ballrooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$", hasSize(10)));
    }

    @Test
    @DisplayName("GET /ballrooms/1 should return one ballroom")
    void testGetSpecificBallroom() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ballrooms/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Riverside"));
    }

    @Test
    @DisplayName("POST /ballrooms should return created ballroom")
        //TODO: repair this test
    void testPostBallroom() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/ballrooms")
                        .content(asJsonString(ballroom1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Riverside"))
                .andExpect(jsonPath("$.city").value("Włocławek"))
                .andExpect(jsonPath("$.availableDates[*]").value(containsInAnyOrder("2021-05-22", "2021-06-26")))
                .andExpect(jsonPath("$.distance").value("0.0"));
    }

    //TODO: put and delete 

    private String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
