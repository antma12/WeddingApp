package pl.antma.wedding.app.videographer;

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
import pl.antma.wedding.app.todo.Todo;
import pl.antma.wedding.app.viedographer.Videographer;
import pl.antma.wedding.app.viedographer.VideographerRepository;
import pl.antma.wedding.app.viedographer.VideographerService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VideographerServiceTest {

    @Mock
    VideographerRepository videographerRepository;

    @InjectMocks
    VideographerService videographerService;

    public static List<Videographer> videographerList = Stream.generate(Videographer::new).limit(10).collect(Collectors.toList());
    public static Page<Videographer> videographerPage = new PageImpl<>(videographerList);
    public static Videographer videographer1;

    @BeforeAll
    public static void beforeAll() {
        videographer1 = new Videographer();
        videographer1.setName("Spoko Studio");
    }

    @Test
    @DisplayName("findAll from service should return the same stream as repository")
    void testFindAll() {
        when(videographerRepository.findAll(any(PageRequest.class))).thenReturn(videographerPage);
        Stream<Videographer> result = videographerService.findAll();
        Assertions.assertEquals(videographerList, result.collect(Collectors.toList()));
    }

    @Test
    @DisplayName("findVideographerById with correct id should return optional of videographer")
    void testFindVideographerByIdExistentId() {
        when(videographerRepository.findById(eq(1L))).thenReturn(Optional.of(videographer1));
        Optional<Videographer> result = videographerService.findVideographerById(1L);
        Assertions.assertEquals(Optional.of(videographer1), result);
    }

    @Test
    @DisplayName("findVideographerById with non-existent id should return empty optional")
    void testFindVideographerByIdNonExistentId() {
        when(videographerRepository.findById(eq(2L))).thenReturn(Optional.empty());
        Optional<Videographer> result = videographerService.findVideographerById(2L);
        Assertions.assertEquals(Optional.empty(), result);
    }

    @Test
    @DisplayName("addVideographer should return created videographer")
    void testAddVideographerNew() {
        when(videographerRepository.save(videographer1)).then(i -> i.getArgument(0, Videographer.class));
        Videographer result = videographerService.addVideographer(videographer1);
        Assertions.assertEquals(videographer1, result);
    }

    @Test
    @DisplayName("updateVideographer should return optional of updated videographer")
    void testUpdateVideographerExisting() {
        when(videographerRepository.findById(1L)).thenReturn(Optional.of(videographer1));
        when(videographerRepository.save(videographer1)).then(i -> i.getArgument(0, Videographer.class));
        Optional<Videographer> result = videographerService.updateVideographer(videographer1, 1L);
        Assertions.assertEquals(Optional.of(videographer1), result);
    }

    @Test
    @DisplayName("updateVideographer should return empty optional if videographer with this id is not in db")
    void testUpdateVideographerNonExisting() {
        when(videographerRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<Videographer> result = videographerService.updateVideographer(null, 2L);
        Assertions.assertEquals(Optional.empty(), result);
    }

    @Test
    @DisplayName("deleteVideographer should return optional of deleted videographer")
    void testDeleteVideographerExisting() {
        when(videographerRepository.findById(1L)).thenReturn(Optional.of(videographer1));
        Optional<Videographer> result = videographerService.deleteVideographer(1L);
        Assertions.assertEquals(Optional.of(videographer1), result);
    }

    @Test
    @DisplayName("deleteVideographer should return empty optional if videographer with this id is not in db")
    void testDeleteVideographerNonExisting() {
        when(videographerRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<Videographer> result = videographerService.deleteVideographer(2L);
        Assertions.assertEquals(Optional.empty(), result);
    }

}
