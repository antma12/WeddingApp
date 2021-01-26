package pl.antma.wedding.app.mass.function;

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
@WebMvcTest(MassFunctionController.class)
class MassFunctionControllerTest {

    @MockBean
    MassFunctionService massFunctionService;

    @Autowired
    private MockMvc mockMvc;

    MassFunction massFunction1;

    @BeforeEach
    public void before(){

        Stream<MassFunction> massFunctionStream =  Stream.generate(MassFunction::new).limit(10);
        massFunction1 = new MassFunction();
        massFunction1.setName("psalm");

        when(massFunctionService.findAll()).thenReturn(massFunctionStream);
        when(massFunctionService.addMassFunction(any(MassFunction.class))).then(i -> i.getArgument(0, MassFunction.class));
        when(massFunctionService.findMassFunctionById(eq(1L))).thenReturn(Optional.of(massFunction1));
        when(massFunctionService.findMassFunctionById(eq(2L))).thenReturn(Optional.empty());
        when(massFunctionService.deleteMassFunction(eq(1L))).thenReturn(Optional.of(massFunction1));
        when(massFunctionService.deleteMassFunction(eq(2L))).thenReturn(Optional.empty());
        when(massFunctionService.updateMassFunction(any(MassFunction.class), eq(1L))).then(i -> Optional.of(i.getArgument(0, MassFunction.class)));
        when(massFunctionService.updateMassFunction(any(MassFunction.class), eq(2L))).thenReturn(Optional.empty());
    }

    @Test
    @DisplayName("GET /massfunctions should return the same number of results as service")
    void testGetMassFunction() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/massfunctions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(10)));
    }

    @Test
    @DisplayName("GET /massFunctions/1 should return one mass function")
    void testGetSpecificBallroom() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/massfunctions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("psalm"));
    }

    @Test
    @DisplayName("POST /massFunctions should return created mass function")
    void testPostBallroom() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/massfunctions")
                .content(asJsonString(massFunction1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("psalm"));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
