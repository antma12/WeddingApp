package pl.antma.wedding.app.ballroom;

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
class BallroomServiceTest {

    @Mock
    BallroomRepository ballroomRepository;

    @InjectMocks
    BallroomService ballroomService;

    public static List<Ballroom> ballroomList = Stream.generate(Ballroom::new).limit(10).collect(Collectors.toList());
    public static Page<Ballroom> ballroomPage = new PageImpl<Ballroom>(ballroomList);
    public static Ballroom ballroom1;

    @BeforeAll
    public static void beforeAll() {
        ballroom1 = new Ballroom();
        ballroom1.setName("Riverside");
    }

    @Test
    @DisplayName("findAll from service should return the same stream as repository")
    public void testFindAll() {
        when(ballroomRepository.findAll(any(PageRequest.class))).thenReturn(ballroomPage);
        //TODO: hardcoded page and size
        Stream<Ballroom> result = ballroomService.findAll(0, 20);
        Assertions.assertEquals(ballroomList, result.collect(Collectors.toList()));
    }

    @Test
    @DisplayName("findBallroomById with correct id should return optional of ballroom")
    public void testFindBallroomByIdExistentId() {
        when(ballroomRepository.findById(eq(1L))).thenReturn(Optional.of(ballroom1));
        Optional<Ballroom> result = ballroomService.findBallroomById(1L);
        Assertions.assertEquals(Optional.of(ballroom1), result);
    }

    @Test
    @DisplayName("findBallroomById with non-existent id should return empty optional")
    public void testFindBallroomByIdNonExistentId() {
        when(ballroomRepository.findById(eq(2L))).thenReturn(Optional.empty());
        Optional<Ballroom> result = ballroomService.findBallroomById(2L);
        Assertions.assertEquals(Optional.empty(), result);
    }

    @Test
    @DisplayName("addBallroom should return created ballroom")
    public void testAddBallroomNew() {
        when(ballroomRepository.save(ballroom1)).then(i -> i.getArgument(0, Ballroom.class));
        Ballroom result = ballroomService.addBallroom(ballroom1);
        Assertions.assertEquals(ballroom1, result);
    }

    @Test
    @DisplayName("updateBallroom should return optional of updated ballroom")
    public void testUpdateBallroomExisting() {
        when(ballroomRepository.findById(1L)).thenReturn(Optional.of(ballroom1));
        when(ballroomRepository.save(ballroom1)).then(i -> i.getArgument(0, Ballroom.class));
        Optional<Ballroom> result = ballroomService.updateBallroom(ballroom1, 1L);
        Assertions.assertEquals(Optional.of(ballroom1), result);
    }

    @Test
    @DisplayName("updateBallroom should return empty optional if ballroom with this id is not in db")
    public void testUpdateBallroomNonExisting() {
        when(ballroomRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<Ballroom> result = ballroomService.updateBallroom(null, 2L);
        Assertions.assertEquals(Optional.empty(), result);
    }

    @Test
    @DisplayName("deleteBallroom should return optional of deleted ballroom")
    public void testDeleteBallroomExisting() {
        when(ballroomRepository.findById(1L)).thenReturn(Optional.of(ballroom1));
        Optional<Ballroom> result = ballroomService.deleteBallroom(1L);
        Assertions.assertEquals(Optional.of(ballroom1), result);
    }

    @Test
    @DisplayName("deleteBallroom should return empty optional if ballroom with this id is not in db")
    public void testDeleteBallroomNonExisting() {
        when(ballroomRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<Ballroom> result = ballroomService.deleteBallroom(2L);
        Assertions.assertEquals(Optional.empty(), result);
    }
}
