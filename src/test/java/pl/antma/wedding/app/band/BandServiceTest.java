package pl.antma.wedding.app.band;

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
class BandServiceTest {

    @Mock
    BandRepository bandRepository;

    @InjectMocks
    BandService bandService;

    public static List<Band> bandList =  Stream.generate(Band::new).limit(10).collect(Collectors.toList());
    public static Page<Band> bandPage = new PageImpl<>(bandList);
    public static Band band1;

    @BeforeAll
    public static void beforeAll(){
        band1 = new Band();
        band1.setName("Quadrans");
    }

    @Test
    @DisplayName("findAll from service should return the same stream as repository")
    void testFindAll() {
        when(bandRepository.findAll(any(PageRequest.class))).thenReturn(bandPage);
        Stream<Band> result = bandService.findAll();
        Assertions.assertEquals(bandList, result.collect(Collectors.toList()));
    }

    @Test
    @DisplayName("findBandById with correct id should return optional of band")
    void testFindBandByIdExistentId() {
        when(bandRepository.findById(eq(1L))).thenReturn(Optional.of(band1));
        Optional<Band> result = bandService.findBandById(1L);
        Assertions.assertEquals(Optional.of(band1), result);
    }

    @Test
    @DisplayName("findBandById with non-existent id should return empty optional")
    void testFindBandByIdNonExistentId() {
        when(bandRepository.findById(eq(2L))).thenReturn(Optional.empty());
        Optional<Band> result = bandService.findBandById(2L);
        Assertions.assertEquals(Optional.empty(), result);
    }

    @Test
    @DisplayName("addBand should return created band")
    void testAddBandNew() {
        when(bandRepository.save(band1)).then(i -> i.getArgument(0, Band.class));
        Band result = bandService.addBand(band1);
        Assertions.assertEquals(band1, result);
    }

    @Test
    @DisplayName("updateBand should return optional of updated band")
    void testUpdateBandExisting() {
        when(bandRepository.findById(1L)).thenReturn(Optional.of(band1));
        when(bandRepository.save(band1)).then(i -> i.getArgument(0, Band.class));
        Optional<Band> result = bandService.updateBand(band1, 1L);
        Assertions.assertEquals(Optional.of(band1), result);
    }

    @Test
    @DisplayName("updateBand should return empty optional if band with this id is not in db")
    void testUpdateBandNonExisting() {
        when(bandRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<Band> result = bandService.updateBand(null, 2L);
        Assertions.assertEquals(Optional.empty(), result);
    }

    @Test
    @DisplayName("deleteBand should return optional of deleted band")
    void testDeleteBandExisting() {
        when(bandRepository.findById(1L)).thenReturn(Optional.of(band1));
        Optional<Band> result = bandService.deleteBand(1L);
        Assertions.assertEquals(Optional.of(band1), result);
    }

    @Test
    @DisplayName("deleteBand should return empty optional if band with this id is not in db")
    void testDeleteBandNonExisting() {
        when(bandRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<Band> result = bandService.deleteBand(2L);
        Assertions.assertEquals(Optional.empty(), result);
    }

}
