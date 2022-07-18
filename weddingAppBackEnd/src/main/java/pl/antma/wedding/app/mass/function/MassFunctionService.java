package pl.antma.wedding.app.mass.function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@Component
public class MassFunctionService {

    @Autowired
    MassFunctionRepository massFunctionRepository;

    public Stream<MassFunction> findAll() {
        return massFunctionRepository.findAll(PageRequest.of(0, 20)).get();
    }

    public MassFunction addMassFunction(MassFunction massFunction) {
        return massFunctionRepository.save(massFunction);
    }

    public Optional<MassFunction> findMassFunctionById(Long id) {
        return massFunctionRepository.findById(id);
    }

    public Optional<MassFunction> updateMassFunction(MassFunction massFunction, Long id) {
        Optional<MassFunction> updatedMassFunction = massFunctionRepository.findById(id);
        updatedMassFunction.ifPresent(
                updMassFunction -> {
                    updMassFunction.setName(massFunction.getName());
                    updMassFunction.setGuests(massFunction.getGuests());
                    massFunctionRepository.save(updMassFunction);
                });
        return updatedMassFunction;
    }

    public Optional<MassFunction> deleteMassFunction(Long id) {
        Optional<MassFunction> deletedMassFunction = massFunctionRepository.findById(id);
        deletedMassFunction.ifPresent(massFunctionRepository::delete);
        return deletedMassFunction;
    }
}
