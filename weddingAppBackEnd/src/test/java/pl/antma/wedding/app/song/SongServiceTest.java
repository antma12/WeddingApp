package pl.antma.wedding.app.song;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SongServiceTest {

    @Mock
    SongRepository songRepository;

    @InjectMocks
    SongService songService;

    public static List<Song> songList = Stream.generate(Song::new).limit(10).collect(Collectors.toList());
    public static Page<Song> songPage = new PageImpl<>(songList);
    public static Song song1;

    @BeforeAll
    public static void beforeAll() {
        song1 = new Song();
        song1.setName("Book of love");
    }

    @Test
    @DisplayName("findAll from service should return the same stream as repository")
    void testFindAll() {
        when(songRepository.findAll(any(PageRequest.class))).thenReturn(songPage);
        Stream<Song> result = songService.findAll();
        Assertions.assertEquals(songList, result.collect(Collectors.toList()));
    }

    @Test
    @DisplayName("findSongById with correct id should return optional of song")
    void testFindSongByIdExistentId() {
        when(songRepository.findById(eq(1L))).thenReturn(Optional.of(song1));
        Optional<Song> result = songService.findSongById(1L);
        Assertions.assertEquals(Optional.of(song1), result);
    }

    @Test
    @DisplayName("findSongById with non-existent id should return empty optional")
    void testFindSongByIdNonExistentId() {
        when(songRepository.findById(eq(2L))).thenReturn(Optional.empty());
        Optional<Song> result = songService.findSongById(2L);
        Assertions.assertEquals(Optional.empty(), result);
    }

    @Test
    @DisplayName("addSong should return created song")
    void testAddSongNew() {
        when(songRepository.save(song1)).then(i -> i.getArgument(0, Song.class));
        Song result = songService.addSong(song1);
        Assertions.assertEquals(song1, result);
    }

    @Test
    @DisplayName("updateSong should return optional of updated song")
    void testUpdateSongExisting() {
        when(songRepository.findById(1L)).thenReturn(Optional.of(song1));
        when(songRepository.save(song1)).then(i -> i.getArgument(0, Song.class));
        Optional<Song> result = songService.updateSong(song1, 1L);
        Assertions.assertEquals(Optional.of(song1), result);
    }

    @Test
    @DisplayName("updateSong should return empty optional if song with this id is not in db")
    void testUpdateSongNonExisting() {
        when(songRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<Song> result = songService.updateSong(null, 2L);
        Assertions.assertEquals(Optional.empty(), result);
    }

    @Test
    @DisplayName("deleteSong should return optional of deleted song")
    void testDeleteSongExisting() {
        when(songRepository.findById(1L)).thenReturn(Optional.of(song1));
        Optional<Song> result = songService.deleteSong(1L);
        Assertions.assertEquals(Optional.of(song1), result);
    }

    @Test
    @DisplayName("deleteSong should return empty optional if song with this id is not in db")
    void testDeleteSongNonExisting() {
        when(songRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<Song> result = songService.deleteSong(2L);
        Assertions.assertEquals(Optional.empty(), result);
    }
}
