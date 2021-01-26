package pl.antma.wedding.app.mass.function;

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
class MassFunctionServiceTest {

    @Mock
    MassFunctionRepository massFunctionRepository;

    @InjectMocks
    MassFunctionService massFunctionService;

    public static List<MassFunction> massFunctionList =  Stream.generate(MassFunction::new).limit(10).collect(Collectors.toList());
    public static Page<MassFunction> massFunctionPage = new PageImpl<>(massFunctionList);
    public static MassFunction massFunction1;

    @BeforeAll
    public static void beforeAll(){
        massFunction1 = new MassFunction();
        massFunction1.setName("psalm");
    }

    @Test
    @DisplayName("findAll from service should return the same stream as repository")
    void testFindAll() {
        when(massFunctionRepository.findAll(any(PageRequest.class))).thenReturn(massFunctionPage);
        Stream<MassFunction> result = massFunctionService.findAll();
        Assertions.assertEquals(massFunctionList, result.collect(Collectors.toList()));
    }

    @Test
    @DisplayName("findMassFunctionById with correct id should return optional of massFunction")
    void testFindMassFunctionByIdExistentId() {
        when(massFunctionRepository.findById(eq(1L))).thenReturn(Optional.of(massFunction1));
        Optional<MassFunction> result = massFunctionService.findMassFunctionById(1L);
        Assertions.assertEquals(Optional.of(massFunction1), result);
    }

    @Test
    @DisplayName("findMassFunctionById with non-existent id should return empty optional")
    void testFindMassFunctionByIdNonExistentId() {
        when(massFunctionRepository.findById(eq(2L))).thenReturn(Optional.empty());
        Optional<MassFunction> result = massFunctionService.findMassFunctionById(2L);
        Assertions.assertEquals(Optional.empty(), result);
    }

    @Test
    @DisplayName("addMassFunction should return created massFunction")
    void testAddMassFunctionNew() {
        when(massFunctionRepository.save(massFunction1)).then(i -> i.getArgument(0, MassFunction.class));
        MassFunction result = massFunctionService.addMassFunction(massFunction1);
        Assertions.assertEquals(massFunction1, result);
    }

    @Test
    @DisplayName("updateMassFunction should return optional of updated massFunction")
    void testUpdateMassFunctionExisting() {
        when(massFunctionRepository.findById(1L)).thenReturn(Optional.of(massFunction1));
        when(massFunctionRepository.save(massFunction1)).then(i -> i.getArgument(0, MassFunction.class));
        Optional<MassFunction> result = massFunctionService.updateMassFunction(massFunction1, 1L);
        Assertions.assertEquals(Optional.of(massFunction1), result);
    }

    @Test
    @DisplayName("updateMassFunction should return empty optional if massFunction with this id is not in db")
    void testUpdateMassFunctionNonExisting() {
        when(massFunctionRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<MassFunction> result = massFunctionService.updateMassFunction(null, 2L);
        Assertions.assertEquals(Optional.empty(), result);
    }

    @Test
    @DisplayName("deleteMassFunction should return optional of deleted massFunction")
    void testDeleteMassFunctionExisting() {
        when(massFunctionRepository.findById(1L)).thenReturn(Optional.of(massFunction1));
        Optional<MassFunction> result = massFunctionService.deleteMassFunction(1L);
        Assertions.assertEquals(Optional.of(massFunction1), result);
    }

    @Test
    @DisplayName("deleteMassFunction should return empty optional if massFunction with this id is not in db")
    void testDeleteMassFunctionNonExisting() {
        when(massFunctionRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<MassFunction> result = massFunctionService.deleteMassFunction(2L);
        Assertions.assertEquals(Optional.empty(), result);
    }

}
