package pl.antma.wedding.app.guest;

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
@WebMvcTest(GuestController.class)
class GuestControllerTest {

    private static final String usernameInDB = "jkowalski";
    private static final String usernameNotInDB = "mnowak";

    @MockBean
    GuestService guestService;

    @Autowired
    private MockMvc mockMvc;

    Guest guest1;

    @BeforeEach
    public void before(){

        Stream<Guest> guestStream =  Stream.generate(Guest::new).limit(10);
        guest1 = new Guest();
        guest1.setUsername(usernameInDB);

        when(guestService.findAll()).thenReturn(guestStream);
        when(guestService.addGuest(any(Guest.class))).then(i -> i.getArgument(0, Guest.class));
        when(guestService.findGuestByUsername(eq(usernameInDB))).thenReturn(Optional.of(guest1));
        when(guestService.findGuestByUsername(eq(usernameNotInDB))).thenReturn(Optional.empty());
        when(guestService.deleteGuest(eq(usernameInDB))).thenReturn(Optional.of(guest1));
        when(guestService.deleteGuest(eq(usernameNotInDB))).thenReturn(Optional.empty());
        when(guestService.updateGuest(any(Guest.class), eq(usernameInDB))).then(i -> Optional.of(i.getArgument(0, Ballroom.class)));
        when(guestService.updateGuest(any(Guest.class), eq(usernameNotInDB))).thenReturn(Optional.empty());
    }

    @Test
    @DisplayName("GET /guests should return the same number of results as service")
    void testGetGuest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/guests"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(10)));
    }

    @Test
    @DisplayName("GET /guests/jkowalski should return one guest")
    void testGetSpecificGuest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/guests/" + usernameInDB))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(usernameInDB));
    }

    @Test
    @DisplayName("POST /guests should return created guest")
    void testPostGuest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/guests")
                .content(asJsonString(guest1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(usernameInDB));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
