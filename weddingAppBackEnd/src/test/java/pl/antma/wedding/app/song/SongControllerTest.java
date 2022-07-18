package pl.antma.wedding.app.song;

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

import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SongController.class)
class SongControllerTest {

    @MockBean
    SongService songService;

    @Autowired
    private MockMvc mockMvc;

    Song song1;

    @BeforeEach
    public void before() {

        Stream<Song> songStream = Stream.generate(Song::new).limit(10);
        song1 = new Song();
        song1.setName("Book of love");

        when(songService.findAll()).thenReturn(songStream);
        when(songService.addSong(any(Song.class))).then(i -> i.getArgument(0, Song.class));
        when(songService.findSongById(eq(1L))).thenReturn(Optional.of(song1));
        when(songService.findSongById(eq(2L))).thenReturn(Optional.empty());
        when(songService.deleteSong(eq(1L))).thenReturn(Optional.of(song1));
        when(songService.deleteSong(eq(2L))).thenReturn(Optional.empty());
        when(songService.updateSong(any(Song.class), eq(1L))).then(i -> Optional.of(i.getArgument(0, Song.class)));
        when(songService.updateSong(any(Song.class), eq(2L))).thenReturn(Optional.empty());
    }

    @Test
    @DisplayName("GET /songs should return the same number of results as service")
    void testGetSongs() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/songs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(10)));
    }

    @Test
    @DisplayName("GET /songs/1 should return one song")
    void testGetSpecificSong() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/songs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Book of love"));
    }

    @Test
    @DisplayName("POST /songs should return created song")
    void testPostSong() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/songs")
                .content(asJsonString(song1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Book of love"));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
